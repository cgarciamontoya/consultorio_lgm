/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.datos.entidades.AntecedentesGenerales;
import consultorio.lgm.datos.entidades.AntecedentesGinecologicos;
import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.Consulta;
import consultorio.lgm.datos.entidades.Paciente;
import consultorio.lgm.datos.entidades.RelAntecedentesEnfermedades;
import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.excepciones.ExcepcionReporte;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.servicios.CatalogosServicio;
import consultorio.lgm.servicios.PacienteServicio;
import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "historiaClinicaMB")
@ViewScoped
public class HistoriaClinicaMB implements Serializable {
    private static final long serialVersionUID = -5395744625159881294L;
    
    private static final String ERROR_GUARDAR_PACIENTE = "historiaClinica.error.guardar";
    private static final String ERROR_NOMBRE_REQUERIDO = "historiaClinica.requerido.nombre";
    private static final String ERROR_PATERNO_REQUERIDO = "historiaClinica.requerido.paterno";
    private static final String ERROR_MATERNO_REQUERIDO = "historiaClinica.requerido.materno";
    private static final String ERROR_FECHA_NACIMIENTO_REQUERIDO = "historiaClinica.requerido.fechaNacimiento";
    private static final String ERROR_DIRECCION_REQUERIDO = "historiaClinica.requerido.direccion";
    private static final String ERROR_ESCOLARIDAD_REQUERIDO = "historiaClinica.requerido.escolaridad";
    private static final String ERROR_EDO_CIVIL_REQUERIDO = "historiaClinica.requerido.estadoCivil";
    private static final String ERROR_GPO_SANGUINEO_REQUERIDO = "historiaClinica.requerido.grupoSanguineo";
    private static final String ERROR_REPORTE_HISTORIA_CLINICA = "historiaClinica.error.reporteHC";
    private static final String EXITO_GUARDAR = "historiaClinica.exito.guardar";
    private static final String EXITO_ACTUALIZAR = "historiaClinica.exito.actualizar";
    
    private Paciente paciente;
    private Consulta notaMedica;
    private StreamedContent historiaClinicaExportada;
    private List<CatEscolaridad> listaEscolaridad;
    private List<CatEstadoCivil> listaEstadoCivil;
    private List<CatEnfermedadesCronicas> listaEnfermedadesCronicas;
    private List<CatGrupoSanguineo> listaGrupoSanguineo;
    private List<CatMpf> listaMpf;
    private List<String> idsEnfCronicas;
    
    private boolean soloLectura;
    
    @ManagedProperty(value = "#{pacienteServicio}")
    private PacienteServicio pacienteServicio;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    @ManagedProperty(value = "#{catalogosServicio}")
    private CatalogosServicio catalogosServicio;

    @PostConstruct
    public void init() {
        soloLectura = false;
        if (vistaHelper.recuperaSesion().getAttribute(Constantes.ID_PACIENTE) != null) {
            Long idPaciente = (Long) vistaHelper.recuperaSesion().getAttribute(Constantes.ID_PACIENTE);
            Integer idUsuario = (Integer) vistaHelper.recuperaSesion().getAttribute(Constantes.ID_USUARIO);
            paciente = pacienteServicio.consultarPacienteId(idPaciente);
            if (paciente.getIdAntecedentes().getRelAntecedentesEnfermedadesList() != null
                    && !paciente.getIdAntecedentes().getRelAntecedentesEnfermedadesList().isEmpty()) {
                idsEnfCronicas = new ArrayList();
                for (RelAntecedentesEnfermedades ae : paciente.getIdAntecedentes().getRelAntecedentesEnfermedadesList()) {
                    idsEnfCronicas.add(String.valueOf(ae.getIdEnfermedad().getId()));
                }
            }
            notaMedica = pacienteServicio.obtenerNotaMedica(idPaciente);
            if (!paciente.getUsuario().getIdUsuario().equals(idUsuario)) {
                soloLectura = true;
            }
        } else {
            inicializarPaciente(true);
        }
        listaEscolaridad = catalogosServicio.consultarEscolaridad();
        listaEstadoCivil = catalogosServicio.consultarEstadoCivil();
        listaEnfermedadesCronicas = catalogosServicio.consultarEnfermedadesCronicas(null);
        listaGrupoSanguineo = catalogosServicio.consultarGrupoSanguineo();
        listaMpf = catalogosServicio.consultarMpf(null);
    }
    
    public void limpiar() {
        limpiarInterno(false);
    }
    
    private void limpiarInterno(boolean nuevo) {
        inicializarPaciente(nuevo);
        idsEnfCronicas = null;
    }
    
    public void guardar() {
        if (!pacienteValido()) {
            return;
        }
        try {
            boolean pacienteNuevo = paciente.getId() == null || paciente.getId() == 0;
            paciente = pacienteServicio.guardarPaciente(paciente, idsEnfCronicas);
            vistaHelper.recuperaSesion().setAttribute(Constantes.ID_PACIENTE, paciente.getId());
            vistaHelper.agregarMensajeExito(pacienteNuevo ? EXITO_GUARDAR : EXITO_ACTUALIZAR);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(ERROR_GUARDAR_PACIENTE);
        }
    }
    
    public void calcularEdad(SelectEvent e) {
        if (e.getObject() != null) {
            paciente.setEdad(calcularEdad((Date) e.getObject()));
        }
    }
    
    private int calcularEdad(Date fechaN) {
        Calendar fechaNac = Calendar.getInstance();
            fechaNac.setTime(fechaN);
            Calendar fechaActual = Calendar.getInstance();
            int edad = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
            if (fechaActual.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH)
                    || (fechaActual.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH)
                    && fechaActual.get(Calendar.DAY_OF_MONTH) < fechaNac.get(Calendar.DAY_OF_MONTH))) {
                edad--;
            }
            return edad;
    }
    
    public void calcularEdad(AjaxBehaviorEvent e) {
        paciente.setEdad(calcularEdad(paciente.getFechaNacimiento()));
    }
    
    private boolean pacienteValido() {
        if (paciente.getNombre() == null || paciente.getNombre().isEmpty()) {
            vistaHelper.agregarMensajeError(ERROR_NOMBRE_REQUERIDO);
            return false;
        }
        if (paciente.getApellidoPaterno() == null || paciente.getApellidoPaterno().isEmpty()) {
            vistaHelper.agregarMensajeError(ERROR_PATERNO_REQUERIDO);
            return false;
        }
        if (paciente.getApellidoMaterno() == null || paciente.getApellidoMaterno().isEmpty()) {
            vistaHelper.agregarMensajeError(ERROR_MATERNO_REQUERIDO);
            return false;
        }
        if (paciente.getFechaNacimiento() == null) {
            vistaHelper.agregarMensajeError(ERROR_FECHA_NACIMIENTO_REQUERIDO);
            return false;
        }
        if (paciente.getDireccion() == null || paciente.getDireccion().isEmpty()) {
            vistaHelper.agregarMensajeError(ERROR_DIRECCION_REQUERIDO);
            return false;
        }
        if (paciente.getIdEscolaridad() == null || paciente.getIdEscolaridad().getId() == null
                || paciente.getIdEscolaridad().getId() < 0) {
            vistaHelper.agregarMensajeError(ERROR_ESCOLARIDAD_REQUERIDO);
            return false;
        }
        if (paciente.getIdEstadoCivil() == null || paciente.getIdEstadoCivil().getId() == null
                || paciente.getIdEstadoCivil().getId() == 0) {
            vistaHelper.agregarMensajeError(ERROR_EDO_CIVIL_REQUERIDO);
            return false;
        }
        if (paciente.getIdAntecedentes().getIdGrupoSanguineo() == null
                || paciente.getIdAntecedentes().getIdGrupoSanguineo().getId() == null
                || paciente.getIdAntecedentes().getIdGrupoSanguineo().getId() == 0) {
            vistaHelper.agregarMensajeError(ERROR_GPO_SANGUINEO_REQUERIDO);
            return false;
        }
        return true;
    }
    
    public void crearNuevo() {
        vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_PACIENTE);
        limpiarInterno(true);
        soloLectura = false;
    }
    
    private void inicializarPaciente(boolean nuevo) {
        Long idPaciente = null;
        if (!nuevo) {
            idPaciente = paciente.getId();
        }
        paciente = new Paciente();
        paciente.setIdEscolaridad(new CatEscolaridad());
        paciente.setIdEstadoCivil(new CatEstadoCivil());
        AntecedentesGenerales ag = new AntecedentesGenerales();
        ag.setIdGrupoSanguineo(new CatGrupoSanguineo());
        paciente.setIdAntecedentes(ag);
        AntecedentesGinecologicos ago = new AntecedentesGinecologicos();
        ago.setIdCatMpf(new CatMpf());
        paciente.setIdAntecedentesGinecologicos(ago);
        if (!nuevo && idPaciente != null) {
            paciente.setId(idPaciente);
        }
        Usuario usr = new Usuario();
        usr.setIdUsuario((Integer) vistaHelper.recuperaSesion().getAttribute("idUsuario"));
        paciente.setUsuario(usr);
    }
    
    public void generarHistoriaClinica() {
        try {
            historiaClinicaExportada = null;
            ByteArrayInputStream expediente = pacienteServicio.exportarHistoriaClinica(paciente.getId());
            if (expediente != null) {
                historiaClinicaExportada = new DefaultStreamedContent(expediente,
                    Constantes.CONTENT_TYPE_PDF,
                    generaNombreArchivoHC());
            }
        } catch (ExcepcionReporte e) {
            vistaHelper.agregarMensajeError(ERROR_REPORTE_HISTORIA_CLINICA);
        }
    }
    
    private String generaNombreArchivoHC() {
        StringBuilder sb = new StringBuilder();
        sb.append("HC_")
                .append(paciente.getNombre())
                .append("_")
                .append(paciente.getApellidoPaterno())
                .append("_")
                .append(paciente.getApellidoMaterno())
                .append(".pdf");
        return sb.toString();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<CatEscolaridad> getListaEscolaridad() {
        return listaEscolaridad;
    }

    public void setListaEscolaridad(List<CatEscolaridad> listaEscolaridad) {
        this.listaEscolaridad = listaEscolaridad;
    }

    public List<CatEstadoCivil> getListaEstadoCivil() {
        return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<CatEstadoCivil> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public List<CatEnfermedadesCronicas> getListaEnfermedadesCronicas() {
        return listaEnfermedadesCronicas;
    }

    public void setListaEnfermedadesCronicas(List<CatEnfermedadesCronicas> listaEnfermedadesCronicas) {
        this.listaEnfermedadesCronicas = listaEnfermedadesCronicas;
    }

    public List<CatGrupoSanguineo> getListaGrupoSanguineo() {
        return listaGrupoSanguineo;
    }

    public void setListaGrupoSanguineo(List<CatGrupoSanguineo> listaGrupoSanguineo) {
        this.listaGrupoSanguineo = listaGrupoSanguineo;
    }

    public List<CatMpf> getListaMpf() {
        return listaMpf;
    }

    public void setListaMpf(List<CatMpf> listaMpf) {
        this.listaMpf = listaMpf;
    }

    public List<String> getIdsEnfCronicas() {
        return idsEnfCronicas;
    }

    public void setIdsEnfCronicas(List<String> idsEnfCronicas) {
        this.idsEnfCronicas = idsEnfCronicas;
    }

    public void setPacienteServicio(PacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }

    public void setCatalogosServicio(CatalogosServicio catalogosServicio) {
        this.catalogosServicio = catalogosServicio;
    }

    public Consulta getNotaMedica() {
        return notaMedica;
    }

    public void setNotaMedica(Consulta notaMedica) {
        this.notaMedica = notaMedica;
    }

    public StreamedContent getHistoriaClinicaExportada() {
        return historiaClinicaExportada;
    }

    public void setHistoriaClinicaExportada(StreamedContent historiaClinicaExportada) {
        this.historiaClinicaExportada = historiaClinicaExportada;
    }

    public boolean isSoloLectura() {
        return soloLectura;
    }

    public void setSoloLectura(boolean soloLectura) {
        this.soloLectura = soloLectura;
    }
    
    
}
