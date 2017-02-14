/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.constantes.LGMConstantes;
import consultorio.lgm.datos.dto.NotaMedicaDTO;
import consultorio.lgm.datos.dto.PacienteDTO;
import consultorio.lgm.datos.dto.RecetaDTO;
import consultorio.lgm.datos.entidades.Consulta;
import consultorio.lgm.datos.entidades.Medicamento;
import consultorio.lgm.datos.entidades.Paciente;
import consultorio.lgm.datos.entidades.Tratamiento;
import consultorio.lgm.excepciones.ExcepcionReporte;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.servicios.CatalogosServicio;
import consultorio.lgm.servicios.ConsultasServicio;
import consultorio.lgm.servicios.PacienteServicio;
import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "consultasMB")
@ViewScoped
public class ConsultasMB implements Serializable {
    private static final long serialVersionUID = -8732624057888627554L;
    
    private static final String ERROR_GUARDAR_TRATAMIENTO = "consultas.error.guardar";
    private static final String ERROR_TRATAMIENTO_REQUERIDO = "consultas.requerido.tratamiento";
    private static final String ERROR_DOSIS_REQUERIDO = "consultas.requerido.dosis";
    private static final String ERROR_DURACION_REQUERIDO = "consultas.requerido.duracion";
    private static final String ERROR_HORARIO_REQUERIDO = "consultas.requerido.horario";
    private static final String ERROR_RECOMENDACIONES_REQUERIDO = "consultas.requerido.recomendaciones";
    private static final String ERROR_MEDICAMENTO_DUPLICADO = "consultas.error.medicamento.duplicado";
    private static final String EXITO_GUARDAR_CONSULTA = "consultas.exito.guardar";
    private static final String ERROR_GUARDAR_PDF = "consultas.error.guardarPdf";
    private static final String ERROR_RECETA_EXISTE = "consultas.error.recetaExiste";
    private static final String ERROR_CREAR_RUTA_PDF = "consultas.error.crearRuta";
    private static final String ERROR_GENERAR_PDF = "consultas.error.generarPdf";
    private static final String ERROR_PDF_VACIO = "consultas.error.pdfVacio";
    private static final String ERROR_CONSULTA_NO_GUARDADA = "consultas.error.consultaNoGuardada";
    
    private StreamedContent recetaExportada;
    private StreamedContent notaMedicaExportada;
    private Consulta consulta;
    private PacienteDTO paciente;
    private Medicamento medicamento;
    private Tratamiento tratamiento;
    private List<Tratamiento> receta;
    private List<Medicamento> listaMedicamentos;
    private Tratamiento[] medicamentosSeleccionados;
    
    @ManagedProperty(value = "#{pacienteServicio}")
    private PacienteServicio pacienteServicio;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    @ManagedProperty(value = "#{catalogosServicio}")
    private CatalogosServicio catalogosServicio;
    @ManagedProperty(value = "#{consultasServicio}")
    private ConsultasServicio consultasServicio;
    
    @PostConstruct
    public void init() {
        limpiar();
        if (vistaHelper.recuperaSesion().getAttribute(Constantes.ID_PACIENTE) != null) {
            paciente = pacienteServicio.consultarPacienteDTOId(
                    (Long) vistaHelper.recuperaSesion().getAttribute(Constantes.ID_PACIENTE));
        }
        if (vistaHelper.recuperaSesion().getAttribute(Constantes.ID_CONSULTA) != null) {
            consulta = consultasServicio.recuperaPorId(
                    (Long) vistaHelper.recuperaSesion().getAttribute(Constantes.ID_CONSULTA));
            if (consulta != null){
                if (consulta.getTratamientoList() != null
                        && !consulta.getTratamientoList().isEmpty()) {
                    receta = consulta.getTratamientoList();
                }
            }
        }
        try {
            listaMedicamentos = catalogosServicio.consultarMedicamento(null);
        } catch (ExcepcionServicio e) {
            listaMedicamentos = new ArrayList<Medicamento>();
        }
    }
    
    public void limpiar() {
        recetaExportada = null;
        medicamentosSeleccionados = null;
        receta = new ArrayList<>();
        tratamiento = new Tratamiento();
        medicamento = new Medicamento();
        consulta = new Consulta();
        consulta.setFecha(new Date());
    }
    
    public void nuevaConsulta() {
        limpiar();
        if (vistaHelper.recuperaSesion().getAttribute(Constantes.ID_CONSULTA) != null) {
            vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_CONSULTA);
        }
    }
    
    public void guardar() {
        try {
            /*if (receta == null || receta.isEmpty()) {
                vistaHelper.agregarMensajeError(ERROR_TRATAMIENTO_REQUERIDO);
            }*/
            consulta.setTratamientoList(receta);
            consulta.setIdPaciente(new Paciente(paciente.getId()));
            consulta = consultasServicio.guardarConsulta(consulta);
            vistaHelper.recuperaSesion().setAttribute(Constantes.ID_CONSULTA, consulta.getId());
            vistaHelper.agregarMensajeExito(EXITO_GUARDAR_CONSULTA);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(ERROR_GUARDAR_TRATAMIENTO);
        }
    }
    
    public void agregarMedicamentoTratamiento(SelectEvent e) {
        if (tratamiento != null) {
            tratamiento.setIdMedicamento(medicamento);
        }
    }
    
    public void agregarMedicamento() {
        if (medicamentoValido()) {
            tratamiento.setIdConsulta(consulta);
            receta.add(tratamiento);
            tratamiento = new Tratamiento();
            medicamento = null;
        }
    }
    
    public void limpiarTratamiento() {
        tratamiento = new Tratamiento();
        medicamento = null;
    }
    
    private boolean medicamentoValido() {
        boolean valido = true;
        if (tratamiento == null) {
            valido = false;
        } else {
            if (tratamiento.getIdMedicamento() == null
                    || tratamiento.getIdMedicamento().getId() == null
                    || tratamiento.getIdMedicamento().getId() == 0) {
                vistaHelper.agregarMensajeError(ERROR_TRATAMIENTO_REQUERIDO);
                valido = false;
            } else if (!receta.isEmpty()) {
                for (Tratamiento trat : receta) {
                    if (trat.getIdMedicamento().getId().equals(tratamiento.getIdMedicamento().getId())) {
                        vistaHelper.agregarMensajeError(ERROR_MEDICAMENTO_DUPLICADO);
                        valido = false;
                    }
                }
            }
            if (tratamiento.getDosis() == null || tratamiento.getDosis().isEmpty()) {
                vistaHelper.agregarMensajeError(ERROR_DOSIS_REQUERIDO);
                valido = false;
            }
            if (tratamiento.getDuracion() == null || tratamiento.getDuracion().isEmpty()) {
                vistaHelper.agregarMensajeError(ERROR_DURACION_REQUERIDO);
                valido = false;
            }
            if (tratamiento.getHorario() == null || tratamiento.getHorario().isEmpty()) {
                vistaHelper.agregarMensajeError(ERROR_HORARIO_REQUERIDO);
                valido = false;
            }
            if (tratamiento.getRecomendaciones() == null || tratamiento.getRecomendaciones().isEmpty()) {
                vistaHelper.agregarMensajeError(ERROR_RECOMENDACIONES_REQUERIDO);
                valido = false;
            }
        }
        return valido;
    }
    
    public void quitarMedicamento() {
        if (medicamentosSeleccionados != null
                && medicamentosSeleccionados.length > 0) {
            for (Tratamiento trat : medicamentosSeleccionados) {
                int i = 0;
                boolean encontrado = false;
                for (i = 0; i < receta.size(); i++) {
                    if (receta.get(i).getIdMedicamento().getId().equals(trat.getIdMedicamento().getId())) {
                        encontrado = true;
                        break;
                    }
                }
                if (encontrado) {
                    receta.remove(i);
                }
            }
            medicamentosSeleccionados = null;
        }
    }
    
    public List<Medicamento> medicamentoComplete(String nombre) {
        List<Medicamento> listaFiltrada = new ArrayList<>();
        for (Medicamento med : listaMedicamentos) {
            if (med.getNombre().toLowerCase().startsWith(nombre.toLowerCase())) {
                listaFiltrada.add(med);
            }
        }
        return listaFiltrada;
    }
    
    public void generarReceta() {
            try {
                recetaExportada = null;
                boolean descargar = false;
                RecetaDTO receta = consultasServicio.generarReceta(consulta);
                if (receta != null) {
                    if (receta.getUrlReceta() != null && !receta.getUrlReceta().isEmpty()) {
                        try {
                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + receta.getUrlReceta());
                        } catch (IOException e) {
                            descargar = true;
                        }
                    } else {
                        descargar = true;
                    }
                    if (descargar) {
                        recetaExportada = new DefaultStreamedContent(receta.getArchivo(),
                            Constantes.CONTENT_TYPE_PDF,
                            receta.getNombreArchivo());
                    }
                }
            } catch (ExcepcionServicio e) {
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_VALORES_NULL) {
                    vistaHelper.agregarMensajeError(ERROR_CONSULTA_NO_GUARDADA);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_CONSULTAS_GUARDAR_RUTA_PDF) {
                    vistaHelper.agregarMensajeError(ERROR_GUARDAR_PDF);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_CONSULTAS_RECETA_EXISTE) {
                    vistaHelper.agregarMensajeError(ERROR_RECETA_EXISTE);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_CONSULTAS_CREAR_PDF) {
                    vistaHelper.agregarMensajeError(ERROR_CREAR_RUTA_PDF);
                }
            } catch (ExcepcionReporte e) {
                if (e.getNumeroError() == LGMConstantesExcepciones.REPORTE_ERROR_LLENAR_REPORTE) {
                    vistaHelper.agregarMensajeError(ERROR_GENERAR_PDF);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.REPORTE_ERROR_REPORTE_VACIO) {
                    vistaHelper.agregarMensajeError(ERROR_PDF_VACIO);
                }
            }
    }
    
    public void generarNotaMedica() {
        try {
                notaMedicaExportada = null;
                boolean descargar = false;
                NotaMedicaDTO notaMedica = consultasServicio.generarNotaMedica(buildParamsNotaMedica());
                if (notaMedica != null) {
                    if (notaMedica.getUrlNotaMedica()!= null && !notaMedica.getUrlNotaMedica().isEmpty()) {
                        try {
                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + notaMedica.getUrlNotaMedica());
                        } catch (IOException e) {
                            descargar = true;
                        }
                    } else {
                        descargar = true;
                    }
                    if (descargar) {
                        notaMedicaExportada = new DefaultStreamedContent(notaMedica.getArchivo(),
                            Constantes.CONTENT_TYPE_PDF,
                            notaMedica.getNombreArchivo());
                    }
                }
            } catch (ExcepcionServicio e) {
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_VALORES_NULL) {
                    vistaHelper.agregarMensajeError(ERROR_CONSULTA_NO_GUARDADA);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_CONSULTAS_GUARDAR_RUTA_PDF) {
                    vistaHelper.agregarMensajeError(ERROR_GUARDAR_PDF);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_CONSULTAS_RECETA_EXISTE) {
                    vistaHelper.agregarMensajeError(ERROR_RECETA_EXISTE);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_CONSULTAS_CREAR_PDF) {
                    vistaHelper.agregarMensajeError(ERROR_CREAR_RUTA_PDF);
                }
            } catch (ExcepcionReporte e) {
                if (e.getNumeroError() == LGMConstantesExcepciones.REPORTE_ERROR_LLENAR_REPORTE) {
                    vistaHelper.agregarMensajeError(ERROR_GENERAR_PDF);
                }
                if (e.getNumeroError() == LGMConstantesExcepciones.REPORTE_ERROR_REPORTE_VACIO) {
                    vistaHelper.agregarMensajeError(ERROR_PDF_VACIO);
                }
            }
    }
    
    public Map<String, Object> buildParamsNotaMedica() {
        Map<String, Object> params = new HashMap<>();
        StringBuilder nombre = new StringBuilder();
        nombre.append(paciente.getNombre().toUpperCase()).append(" ").append(paciente.getApellidoPaterno().toUpperCase()).append(" ")
                .append(paciente.getApellidoMaterno().toUpperCase()).append(" DE ").append(paciente.getEdad()).append(" AÑOS");
        params.put(LGMConstantes.PARAM_NM_NOMBRE, nombre.toString());
        params.put(LGMConstantes.PARAM_NM_MOTIVO_CONSULTA, consulta.getMotivoConsulta());
        params.put(LGMConstantes.PARAM_NM_EXPLORACION_FISICA, consulta.getExploracionFisica());
        params.put(LGMConstantes.PARAM_NM_DIAGNOSTICO, "Dx: " + consulta.getDiagnostico());
        params.put(LGMConstantes.PARAM_NM_PLAN, "Plan: " + consulta.getPlan());
        DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
        StringBuilder sv = new StringBuilder();
        sv.append("SV: P: ")
                .append(consulta.getPeso()).append(", T: ")
                .append(consulta.getTalla()).append(", FC: ")
                .append(consulta.getFrecuenciaCardiaca()).append(" ppm, FR: ")
                .append(consulta.getFrecuenciaRespiratoria()).append(" rpm, Temp: ")
                .append(consulta.getTemperatura()).append(", TA: ")
                .append(consulta.getTaSistolica()).append("/").append(consulta.getTaDiastolica()).append(", G:")
                .append(consulta.getGlucosa()).append(", CC: ")
                .append(consulta.getCircunferenciaAbdominal()).append(" cms, IMC: ")
                .append(df2.format(consulta.getPeso() / (consulta.getTalla() * consulta.getTalla()))).append(".");
        params.put(LGMConstantes.PARAM_NM_SIGNOS_VITALES, sv.toString());
        params.put(LGMConstantes.PARAM_NM_NOMBRE_REP, paciente.getNombre() + "_" + paciente.getApellidoPaterno() + "_" + paciente.getApellidoMaterno());
        return params;
    }
    
    public void recargarMedicamento() {
        listaMedicamentos = catalogosServicio.consultarMedicamento(null);
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

    public void setConsultasServicio(ConsultasServicio consultasServicio) {
        this.consultasServicio = consultasServicio;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public List<Tratamiento> getReceta() {
        return receta;
    }

    public void setReceta(List<Tratamiento> receta) {
        this.receta = receta;
    }

    public List<Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void setListaMedicamentos(List<Medicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    public Tratamiento[] getMedicamentosSeleccionados() {
        return medicamentosSeleccionados;
    }

    public void setMedicamentosSeleccionados(Tratamiento[] medicamentosSeleccionados) {
        this.medicamentosSeleccionados = medicamentosSeleccionados;
    }

    public StreamedContent getRecetaExportada() {
        return recetaExportada;
    }

    public void setRecetaExportada(StreamedContent recetaExportada) {
        this.recetaExportada = recetaExportada;
    }

    public StreamedContent getNotaMedicaExportada() {
        return notaMedicaExportada;
    }

    public void setNotaMedicaExportada(StreamedContent notaMedicaExportada) {
        this.notaMedicaExportada = notaMedicaExportada;
    }
}
