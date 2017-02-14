/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.datos.dto.ConsultaDTO;
import consultorio.lgm.datos.dto.PacienteDTO;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.servicios.ConsultasServicio;
import consultorio.lgm.servicios.PacienteServicio;
import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "busquedasConsultasMB")
@ViewScoped
public class BusquedasConsultasMB implements Serializable {
    private static final long serialVersionUID = -8732624057888627554L;
    
    private static final String ERROR_CONSULTA = "consultasPrevias.error.consulta";

    private Long idConsulta;
    private Date fecha;
    private List<ConsultaDTO> consultas;
    private PacienteDTO paciente;
    
    @ManagedProperty(value = "#{consultasServicio}")
    private ConsultasServicio consultasServicio;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    @ManagedProperty(value = "#{pacienteServicio}")
    private PacienteServicio pacienteServicio;
    
    @PostConstruct
    public void init() {
        paciente = new PacienteDTO();
        if (vistaHelper.recuperaSesion().getAttribute(Constantes.ID_PACIENTE) != null) {
            try {
                paciente = pacienteServicio.consultarPacienteDTOId(
                        (Long) vistaHelper.recuperaSesion().getAttribute(Constantes.ID_PACIENTE));
                consultas = consultasServicio.recuperaPorFiltro(paciente.getId(), null);
            } catch (ExcepcionServicio e) {
                paciente = new PacienteDTO();
            }
        }
    }
    
    public void buscar() {
        try {
            consultas = null;
            consultas = consultasServicio.recuperaPorFiltro(paciente.getId(), fecha);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(ERROR_CONSULTA);
        }
    }
    
    public void limpiar() {
        consultas = null;
        fecha = null;
        idConsulta = null;
        vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_CONSULTA);
    }
    
    public void nuevaBusqueda() {
        vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_PACIENTE);
        paciente = new PacienteDTO();
        limpiar();
    }
    
    public void seleccionarConsulta(SelectEvent e) {
        paciente = new PacienteDTO();
        ConsultaDTO cdto = (ConsultaDTO) e.getObject();
        paciente.setNombre(cdto.getNombrePaciente());
        paciente.setApellidoPaterno(cdto.getPaternoPaciente());
        paciente.setApellidoMaterno(cdto.getMaternoPaciente());
        paciente.setId(cdto.getIdPaciente());
        fecha = cdto.getFecha();
        vistaHelper.recuperaSesion().setAttribute(Constantes.ID_PACIENTE, cdto.getIdPaciente());
        vistaHelper.recuperaSesion().setAttribute(Constantes.ID_CONSULTA, cdto.getId());
        idConsulta = cdto.getId();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ConsultaDTO> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<ConsultaDTO> consultas) {
        this.consultas = consultas;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public void setConsultasServicio(ConsultasServicio consultasServicio) {
        this.consultasServicio = consultasServicio;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }

    public void setPacienteServicio(PacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }
    
}