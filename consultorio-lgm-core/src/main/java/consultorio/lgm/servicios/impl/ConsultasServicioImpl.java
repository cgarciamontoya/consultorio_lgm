/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios.impl;

import consultorio.lgm.constantes.ConfigEnum;
import consultorio.lgm.constantes.LGMConstantes;
import consultorio.lgm.datos.dto.ConsultaDTO;
import consultorio.lgm.datos.dto.NotaMedicaDTO;
import consultorio.lgm.datos.dto.RecetaDTO;
import consultorio.lgm.datos.entidades.Configuracion;
import consultorio.lgm.datos.entidades.Consulta;
import consultorio.lgm.datos.entidades.Paciente;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.ConfiguracionDAO;
import consultorio.lgm.integracion.dao.basedatos.ConsultasDAO;
import consultorio.lgm.reportes.TipoReporte;
import consultorio.lgm.servicios.ConsultasServicio;
import consultorio.lgm.servicios.ReportesManagerServicio;
import consultorio.lgm.servicios.ServicioBase;
import consultorio.lgm.util.UtileriaFechas;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.jdto.DTOBinder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Usuario
 */
public class ConsultasServicioImpl extends ServicioBase
        implements ConsultasServicio, Serializable {
    private static final long serialVersionUID = 4841082133677535915L;
    
    private static final String SEPARADOR_RUTA = "\\";
    
    @Autowired
    private ConsultasDAO consultasDAO;
    @Autowired
    private DTOBinder dtoBinder;
    @Autowired
    private ReportesManagerServicio reportesManagerServicio;
    @Autowired
    private ConfiguracionDAO configuracionDAO;

    @Override
    public List<ConsultaDTO> recuperaPorFiltro(Long idPaciente, Date fecha) {
        try {
            List<Consulta> consultas = consultasDAO.recuperaPorFiltro(idPaciente, fecha);
            return dtoBinder.bindFromBusinessObjectList(ConsultaDTO.class, consultas);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public Consulta recuperaUltimaConsulta(Long idPaciente) {
        try {
            return consultasDAO.recuperaUltimaConsulta(idPaciente);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public Consulta guardarConsulta(Consulta filtro) {
        try {
            if (!consultaValida(filtro)) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
            }
            return consultasDAO.guardarYVacia(filtro);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }
    
    private boolean consultaValida(Consulta filtro) {
        boolean valido = true;
        if (filtro == null) {
            valido = false;
        } else {
            if (filtro.getMotivoConsulta() == null || filtro.getMotivoConsulta().isEmpty()) {
                valido = false;
            }
            if (filtro.getIdPaciente() == null || filtro.getIdPaciente().getId() == null
                    || filtro.getIdPaciente().getId() == 0) {
                valido = false;
            }
            if (filtro.getDiagnostico() == null || filtro.getDiagnostico().isEmpty()) {
                valido = false;
            }
            /*if (filtro.getTratamientoList() == null || filtro.getTratamientoList().isEmpty()) {
                valido = false;
            }*/
        }
        return valido;
    }

    @Override
    public Consulta recuperaPorId(Long idConsulta) {
        return consultasDAO.recuperaPorID(idConsulta);
    }
    
    @Override
    public RecetaDTO generarReceta(Consulta consulta) {
        if (consulta == null
                || consulta.getId() == null
                || consulta.getId() == 0) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        ByteArrayInputStream resultado = reportesManagerServicio.generarReporte(
                null,
                generaParametros(consulta),
                LGMConstantes.NOMBRE_REPORTE_CONSULTA_SPRING,
                TipoReporte.PDF);
        String nombreReceta = generaNombreReceta(consulta.getFecha(), consulta.getIdPaciente(), consulta.getId());
        String urlReceta = guardarRecetaLocal(resultado, nombreReceta, consulta.getFecha());
        RecetaDTO receta = new RecetaDTO();
        receta.setArchivo(resultado);
        receta.setNombreArchivo(nombreReceta);
        receta.setUrlReceta(urlReceta);
        return receta;
    }
    
    @Override
    public NotaMedicaDTO generarNotaMedica(Map<String, Object> parametros) {
        if (parametros == null || parametros.isEmpty()) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        ByteArrayInputStream resultado = reportesManagerServicio.generarReporte(
                null,
                parametros,
                LGMConstantes.NOMBRE_REPORTE_NOTA_MEDICA_SPRING,
                TipoReporte.PDF);
        Date fechaNM = new Date();
        String nombreNM = "NM_" + parametros.get(LGMConstantes.PARAM_NM_NOMBRE_REP) + "_" + new SimpleDateFormat("dd-MM-yyyy").format(fechaNM) + ".pdf";
        String urlNM = guardarRecetaLocal(resultado, nombreNM, fechaNM);
        NotaMedicaDTO nm = new NotaMedicaDTO();
        nm.setArchivo(resultado);
        nm.setNombreArchivo(nombreNM);
        nm.setUrlNotaMedica(urlNM);
        return nm;
    }
    
    private String guardarRecetaLocal(ByteArrayInputStream archivo, String nombreReceta, Date fechaConsulta) {
        StringBuilder ruta = new StringBuilder();
        try {
            Configuracion conf = configuracionDAO.consultarConfiguracion(ConfigEnum.PARAM_RUTA_RECETAS.name());
            ruta.append(conf != null ? conf.getValor() : LGMConstantes.RUTA_RECETAS_DEFAULT);
        } catch (ExcepcionDAO e) {
            ruta.append(LGMConstantes.NOMBRE_REPORTE_CONSULTA);
        }
        
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaConsulta);
        
        ruta.append(fecha.get(Calendar.YEAR))
                .append(SEPARADOR_RUTA)
                .append(UtileriaFechas.obtenerNombreMes(fecha.get(Calendar.MONTH) + 1))
                .append(SEPARADOR_RUTA)
                .append(nombreReceta);
        
        FileOutputStream out = null;
        File archivoTemporal;

        try {
            //Creamos el archivo temporal en la ruta temporal del sistema y el nombre
            archivoTemporal = new File(ruta.toString());
            if (!archivoTemporal.getParentFile().exists()) {
                archivoTemporal.getParentFile().mkdirs();
            }
            out = new FileOutputStream(archivoTemporal);

            //Se copia el contenido del reporte al nuevo archivo
            IOUtils.copy(archivo, out);
        } catch (FileNotFoundException e) {
            ruta = new StringBuilder();
        } catch (IOException e) {
            ruta = new StringBuilder();
        } finally {
            //Cerramos el FileOutputStream
            IOUtils.closeQuietly(out);
            //Reseteamos el jasperByteArray
            archivo.reset();
        }
        return ruta.toString();
    }
    
    private String generaNombreReceta(Date fecha, Paciente paciente, Long idConsulta) {
        StringBuilder nombre = new StringBuilder();
        String separador = "_";
        nombre.append(paciente.getNombre())
                .append(separador)
                .append(paciente.getApellidoPaterno())
                .append(separador)
                .append(paciente.getApellidoMaterno())
                .append(separador)
                .append(new SimpleDateFormat("dd_MM_yyyy").format(fecha))
                .append(separador)
                .append(idConsulta)
                .append(".pdf");
        return nombre.toString();
    }
    
    private Map<String, Object> generaParametros(Consulta consulta) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put(LGMConstantes.PARAM_RECETA_NOMBRE, consulta.getIdPaciente().getNombre());
        parametros.put(LGMConstantes.PARAM_RECETA_PATERNO, consulta.getIdPaciente().getApellidoPaterno());
        parametros.put(LGMConstantes.PARAM_RECETA_MATERNO, consulta.getIdPaciente().getApellidoMaterno());
        parametros.put(LGMConstantes.PARAM_RECETA_FECHA, consulta.getFecha());
        parametros.put(LGMConstantes.PARAM_RECETA_DIAGNOSTICO, consulta.getDiagnostico());
        parametros.put(LGMConstantes.PARAM_RECETA_PROXIMA_FECHA, consulta.getProximaCita());
        parametros.put(LGMConstantes.PARAM_RECETA_ID_CONSULTA, consulta.getId());
        parametros.put("medico", "Dr(a). " + consulta.getUsuario().getNombre() + " " + consulta.getUsuario().getApellidoPaterno() + " " + consulta.getUsuario().getApellidoMaterno());
        parametros.put("cedula", "Ced. Prof. " + consulta.getUsuario().getCedula());
        return parametros;
    }
}
