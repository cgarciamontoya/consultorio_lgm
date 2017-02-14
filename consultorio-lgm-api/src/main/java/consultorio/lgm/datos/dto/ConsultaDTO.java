/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.datos.dto;

import java.util.Date;
import org.jdto.annotation.Source;

/**
 *
 * @author Usuario
 */
public class ConsultaDTO {

    private Long id;
    private String motivoConsulta;
    private Date fecha;
    private String diagnostico;
    @Source(value = "idPaciente.nombre")
    private String nombrePaciente;
    @Source(value = "idPaciente.apellidoPaterno")
    private String paternoPaciente;
    @Source(value = "idPaciente.apellidoMaterno")
    private String maternoPaciente;
    @Source(value = "idPaciente.id")
    private Long idPaciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getPaternoPaciente() {
        return paternoPaciente;
    }

    public void setPaternoPaciente(String paternoPaciente) {
        this.paternoPaciente = paternoPaciente;
    }

    public String getMaternoPaciente() {
        return maternoPaciente;
    }

    public void setMaternoPaciente(String maternoPaciente) {
        this.maternoPaciente = maternoPaciente;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    
}
