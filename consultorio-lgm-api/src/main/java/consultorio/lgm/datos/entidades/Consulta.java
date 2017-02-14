/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.datos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {
    private static final long serialVersionUID = 7940065671207454665L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "motivo_consulta")
    private String motivoConsulta;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "peso")
    private Double peso;
    @Column(name = "ta_sistolica")
    private Integer taSistolica;
    @Column(name = "ta_diastolica")
    private Integer taDiastolica;
    @Basic(optional = false)
    @Column(name = "diagnostico")
    private String diagnostico;
    @Column(name = "talla")
    private Double talla;
    @Column(name = "frecuencia_cardiaca")
    private Integer frecuenciaCardiaca;
    @Column(name = "frecuencia_respiratoria")
    private Integer frecuenciaRespiratoria;
    @Column(name = "glucosa")
    private Double glucosa;
    @Column(name = "exploracion_fisica")
    private String exploracionFisica;
    @Column(name = "laboratorio_gabinete")
    private String laboratorioGabinete;
    @Column(name = "plan")
    private String plan;
    @Column(name = "proxima_cita")
    @Temporal(TemporalType.DATE)
    private Date proximaCita;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConsulta", fetch = FetchType.EAGER)
    private List<Tratamiento> tratamientoList;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Paciente idPaciente;
    @Column(name = "temperatura")
    private Double temperatura;
    @Column(name = "circunferencia_abdominal")
    private Integer circunferenciaAbdominal;

    public Consulta() {
    }

    public Consulta(Long id) {
        this.id = id;
    }

    public Consulta(Long id, String motivoConsulta, Date fecha, String diagnostico) {
        this.id = id;
        this.motivoConsulta = motivoConsulta;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
    }

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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getTaSistolica() {
        return taSistolica;
    }

    public void setTaSistolica(Integer taSistolica) {
        this.taSistolica = taSistolica;
    }

    public Integer getTaDiastolica() {
        return taDiastolica;
    }

    public void setTaDiastolica(Integer taDiastolica) {
        this.taDiastolica = taDiastolica;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Double getTalla() {
        return talla;
    }

    public void setTalla(Double talla) {
        this.talla = talla;
    }

    public Integer getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(Integer frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public Integer getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(Integer frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public Double getGlucosa() {
        return glucosa;
    }

    public void setGlucosa(Double glucosa) {
        this.glucosa = glucosa;
    }

    public String getExploracionFisica() {
        return exploracionFisica;
    }

    public void setExploracionFisica(String exploracionFisica) {
        this.exploracionFisica = exploracionFisica;
    }

    public String getLaboratorioGabinete() {
        return laboratorioGabinete;
    }

    public void setLaboratorioGabinete(String laboratorioGabinete) {
        this.laboratorioGabinete = laboratorioGabinete;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getProximaCita() {
        return proximaCita;
    }

    public void setProximaCita(Date proximaCita) {
        this.proximaCita = proximaCita;
    }

    public List<Tratamiento> getTratamientoList() {
        return tratamientoList;
    }

    public void setTratamientoList(List<Tratamiento> tratamientoList) {
        this.tratamientoList = tratamientoList;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getCircunferenciaAbdominal() {
        return circunferenciaAbdominal;
    }

    public void setCircunferenciaAbdominal(Integer circunferenciaAbdominal) {
        this.circunferenciaAbdominal = circunferenciaAbdominal;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "consultorio.lgm.datos.entidades.Consulta[ id=" + id + " ]";
    }
    
}
