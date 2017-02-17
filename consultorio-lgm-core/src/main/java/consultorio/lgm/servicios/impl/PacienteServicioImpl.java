/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios.impl;

import consultorio.lgm.constantes.LGMConstantes;
import consultorio.lgm.datos.dto.PacienteDTO;
import consultorio.lgm.datos.entidades.AntecedentesGenerales;
import consultorio.lgm.datos.entidades.AntecedentesGinecologicos;
import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.Consulta;
import consultorio.lgm.datos.entidades.Paciente;
import consultorio.lgm.datos.entidades.RelAntecedentesEnfermedades;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.ExcepcionReporte;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.ConsultasDAO;
import consultorio.lgm.integracion.dao.basedatos.PacienteDAO;
import consultorio.lgm.integracion.dao.basedatos.RelAntecedentesEnfermedadesDAO;
import consultorio.lgm.reportes.TipoReporte;
import consultorio.lgm.reportes.util.HistoriaClinicaUtil;
import consultorio.lgm.reportes.vo.HistoriaClinicaVO;
import consultorio.lgm.servicios.PacienteServicio;
import consultorio.lgm.servicios.ReportesManagerServicio;
import consultorio.lgm.servicios.ServicioBase;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdto.DTOBinder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Usuario
 */
public class PacienteServicioImpl extends ServicioBase implements PacienteServicio, Serializable {
    private static final long serialVersionUID = 1252563511898174638L;

    @Autowired
    private PacienteDAO pacienteDAO;
    @Autowired
    private DTOBinder dtoBinder;
    @Autowired
    private RelAntecedentesEnfermedadesDAO relAntecedentesEnfermedadesDAO;
    @Autowired
    private ConsultasDAO consultasDAO;
    @Autowired
    private ReportesManagerServicio reportesManagerServicio;
    
    @Override
    public List<PacienteDTO> consultarPacientes(String nombre, String paterno, String materno) {
        if ((nombre == null || nombre.isEmpty())
                && (paterno == null || paterno.isEmpty())
                && (materno == null || materno.isEmpty())) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        try {
            List<Paciente> pacientes = pacienteDAO.recuperaPorFiltro(nombre, paterno, materno);
            if (pacientes != null) {
                return dtoBinder.bindFromBusinessObjectList(PacienteDTO.class, pacientes);
            }
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
        return null;
    }

    @Override
    public boolean existePaciente(String nombre, String paterno, String materno, Date fechaNacimiento) {
        if (nombre == null
                || nombre.isEmpty()
                || paterno == null
                || paterno.isEmpty()
                || materno == null
                || materno.isEmpty()
                || fechaNacimiento == null) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        return pacienteDAO.existePaciente(nombre, paterno, materno, fechaNacimiento);
    }

    @Override
    public Paciente consultarPacienteId(Long id) {
        if (id == null || id == 0) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        Paciente paciente = pacienteDAO.recuperaPorID(id);
        try {
            paciente.getIdAntecedentes().setRelAntecedentesEnfermedadesList(
                    relAntecedentesEnfermedadesDAO.recuperaRelacionesAntecedentes(
                    paciente.getIdAntecedentes().getId()));
        } catch (ExcepcionDAO e) {
            paciente.getIdAntecedentes().setRelAntecedentesEnfermedadesList(new ArrayList());
        }
        return pacienteDAO.recuperaPorID(id);
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        if (!pacienteValido(paciente)) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        try {
            if (!paciente.getGeneroFemenino()
                    && (paciente.getIdAntecedentesGinecologicos().getId() == null
                    || paciente.getIdAntecedentesGinecologicos().getId() == 0)) {
                AntecedentesGinecologicos ago = new AntecedentesGinecologicos();
                ago.setIdCatMpf(new CatMpf(0));
                paciente.setIdAntecedentesGinecologicos(ago);
            }
            return pacienteDAO.guardarYVacia(paciente);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_PACIENTE_GUARDAR);
        }
        
    }
    
    @Override
    public Paciente guardarPaciente(Paciente paciente, List<String> idsEnfCronicas) {
        paciente = guardarPaciente(paciente);
        try {
            List<RelAntecedentesEnfermedades> relAE = relAntecedentesEnfermedadesDAO.
                    recuperaRelacionesAntecedentes(paciente.getIdAntecedentes().getId());
            if (relAE != null && !relAE.isEmpty()) {
                for (RelAntecedentesEnfermedades relacion : relAE) {
                    relAntecedentesEnfermedadesDAO.eliminar(relacion);
                }
            }
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_PACIENTE_GUARDAR);
        }
        if (idsEnfCronicas != null && !idsEnfCronicas.isEmpty()) {
            List<RelAntecedentesEnfermedades> relaciones = new ArrayList<>();
            for (String enf : idsEnfCronicas) {
                RelAntecedentesEnfermedades ae = new RelAntecedentesEnfermedades();
                ae.setIdAntecedentes(new AntecedentesGenerales(paciente.getIdAntecedentes().getId()));
                ae.setIdEnfermedad(new CatEnfermedadesCronicas(new Integer(enf)));
                relaciones.add(ae);
            }
            relAntecedentesEnfermedadesDAO.guardar(relaciones);
        }
        return consultarPacienteId(paciente.getId());
    }
    
    private boolean pacienteValido(Paciente paciente) {
        int contador = 0;
        if (paciente == null) {
            return false;
        }
        if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
            contador++;
        }
        if (paciente.getApellidoPaterno() == null || paciente.getApellidoPaterno().isEmpty()) {
            contador++;
        }
        if (paciente.getApellidoMaterno() == null || paciente.getApellidoMaterno().isEmpty()) {
            contador++;
        }
        if (paciente.getFechaNacimiento() == null) {
            contador++;
        }
        return contador == 0;
    }

    @Override
    public PacienteDTO consultarPacienteDTOId(Long id) {
        Paciente paciente = consultarPacienteId(id);
        return dtoBinder.bindFromBusinessObject(PacienteDTO.class, paciente);
    }
    
    @Override
    public Consulta obtenerNotaMedica(Long id) {
        return consultasDAO.recuperaNotaMedica(id);
    }

    @Override
    public ByteArrayInputStream exportarHistoriaClinica(Long id) {
        try {
            Paciente paciente = this.consultarPacienteId(id);
            Consulta notaMedica = this.obtenerNotaMedica(id);
            List<HistoriaClinicaVO> detalle = new ArrayList<>();
            detalle.add(HistoriaClinicaUtil.crearHC(paciente, notaMedica));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("medico", "Dr(a). " + paciente.getUsuario().getNombre() + " " + paciente.getUsuario().getApellidoPaterno() 
                    + " " + paciente.getUsuario().getApellidoMaterno());
            parametros.put("cedula", "Ced. Prof. " + paciente.getUsuario().getCedula());
            return reportesManagerServicio.generarReporte(detalle, parametros,
                    LGMConstantes.NOMBRE_REPORTE_HISTORIA_CLINICA_SPRING, TipoReporte.PDF);
        } catch (ExcepcionServicio | ExcepcionReporte e) {
            throw e;
        }
    }
}
