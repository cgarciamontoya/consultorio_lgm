/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.datos.dto.PacienteDTO;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.servicios.PacienteServicio;
import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;
import java.io.Serializable;
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
@ManagedBean(name = "pacienteMB")
@ViewScoped
public class PacienteMB implements Serializable {
    private static final long serialVersionUID = -6902165092422717409L;
    
    private static final String ERROR_CONSULTA_BD = "paciente.error.consulta";
    private static final String ERROR_FILTROS_INVALIDOS = "paciente.error.filtros";

    private String nombre;
    private String paterno;
    private String materno;
    private Long idPaciente;
    List<PacienteDTO> pacientes;
    @ManagedProperty(value = "#{pacienteServicio}")
    private PacienteServicio pacienteServicio;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;

    @PostConstruct
    public void init() {
    }
    
    public void buscar() {
        try {
            pacientes = null;
            if ((nombre == null || nombre.isEmpty())
                    && (paterno == null || paterno.isEmpty())
                    && (materno == null || materno.isEmpty())) {
                vistaHelper.agregarMensajeError(ERROR_FILTROS_INVALIDOS);
                return;
            }
            pacientes = pacienteServicio.consultarPacientes(nombre, paterno, materno);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(ERROR_CONSULTA_BD);
        }
    }
    
    public void seleccionarPaciente(SelectEvent e) {
        idPaciente = ((PacienteDTO) e.getObject()).getId();
        vistaHelper.recuperaSesion().setAttribute(Constantes.ID_PACIENTE, idPaciente);
        vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_CONSULTA);
    }
    
    public void limpiar() {
        vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_PACIENTE);
        idPaciente = null;
        nombre = null;
        paterno = null;
        materno = null;
        pacientes = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public List<PacienteDTO> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<PacienteDTO> pacientes) {
        this.pacientes = pacientes;
    }

    public void setPacienteServicio(PacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }
    
}
