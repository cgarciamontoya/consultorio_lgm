/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.datos.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "antecedentes_ginecologicos")
public class AntecedentesGinecologicos implements Serializable {
    private static final long serialVersionUID = -5662296758128790271L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "menarca")
    private Integer menarca;
    @Column(name = "ciclo_menstrual_regular")
    private Boolean cicloMenstrualRegular;
    @Column(name = "duracion_ciclo_menstrual")
    private String duracionCicloMenstrual;
    @Column(name = "ivsa")
    private Integer ivsa;
    @Column(name = "numero_parejas")
    private Integer numeroParejas;
    @Column(name = "exploracion_mama")
    private Boolean exploracionMama;
    @Column(name = "papanicolaou")
    private Boolean papanicolaou;
    @Column(name = "resultado_papanicolaou")
    private String resultadoPapanicolaou;
    @Column(name = "gesta")
    private Short gesta;
    @Column(name = "parto")
    private Short parto;
    @Column(name = "abortos")
    private Short abortos;
    @Column(name = "cesarea")
    private Short cesarea;
    @Column(name = "fecha_ultima_menstruacion")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimaMenstruacion;
    @Column(name = "fecha_probable_parto")
    @Temporal(TemporalType.DATE)
    private Date fechaProbableParto;
    @Column(name = "complicaciones_embarazo")
    private String complicacionesEmbarazo;
    @JoinColumn(name = "id_cat_mpf", referencedColumnName = "id")
    @ManyToOne
    private CatMpf idCatMpf;

    public AntecedentesGinecologicos() {
    }

    public AntecedentesGinecologicos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMenarca() {
        return menarca;
    }

    public void setMenarca(Integer menarca) {
        this.menarca = menarca;
    }

    public Boolean getCicloMenstrualRegular() {
        return cicloMenstrualRegular;
    }

    public void setCicloMenstrualRegular(Boolean cicloMenstrualRegular) {
        this.cicloMenstrualRegular = cicloMenstrualRegular;
    }

    public String getDuracionCicloMenstrual() {
        return duracionCicloMenstrual;
    }

    public void setDuracionCicloMenstrual(String duracionCicloMenstrual) {
        this.duracionCicloMenstrual = duracionCicloMenstrual;
    }

    public Integer getIvsa() {
        return ivsa;
    }

    public void setIvsa(Integer ivsa) {
        this.ivsa = ivsa;
    }

    public Integer getNumeroParejas() {
        return numeroParejas;
    }

    public void setNumeroParejas(Integer numeroParejas) {
        this.numeroParejas = numeroParejas;
    }

    public Boolean getExploracionMama() {
        return exploracionMama;
    }

    public void setExploracionMama(Boolean exploracionMama) {
        this.exploracionMama = exploracionMama;
    }

    public Boolean getPapanicolaou() {
        return papanicolaou;
    }

    public void setPapanicolaou(Boolean papanicolaou) {
        this.papanicolaou = papanicolaou;
    }

    public String getResultadoPapanicolaou() {
        return resultadoPapanicolaou;
    }

    public void setResultadoPapanicolaou(String resultadoPapanicolaou) {
        this.resultadoPapanicolaou = resultadoPapanicolaou;
    }

    public Short getGesta() {
        return gesta;
    }

    public void setGesta(Short gesta) {
        this.gesta = gesta;
    }

    public Short getParto() {
        return parto;
    }

    public void setParto(Short parto) {
        this.parto = parto;
    }

    public Short getAbortos() {
        return abortos;
    }

    public void setAbortos(Short abortos) {
        this.abortos = abortos;
    }

    public Short getCesarea() {
        return cesarea;
    }

    public void setCesarea(Short cesarea) {
        this.cesarea = cesarea;
    }

    public Date getFechaUltimaMenstruacion() {
        return fechaUltimaMenstruacion;
    }

    public void setFechaUltimaMenstruacion(Date fechaUltimaMenstruacion) {
        this.fechaUltimaMenstruacion = fechaUltimaMenstruacion;
    }

    public Date getFechaProbableParto() {
        return fechaProbableParto;
    }

    public void setFechaProbableParto(Date fechaProbableParto) {
        this.fechaProbableParto = fechaProbableParto;
    }

    public String getComplicacionesEmbarazo() {
        return complicacionesEmbarazo;
    }

    public void setComplicacionesEmbarazo(String complicacionesEmbarazo) {
        this.complicacionesEmbarazo = complicacionesEmbarazo;
    }

    public CatMpf getIdCatMpf() {
        return idCatMpf;
    }

    public void setIdCatMpf(CatMpf idCatMpf) {
        this.idCatMpf = idCatMpf;
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
        if (!(object instanceof AntecedentesGinecologicos)) {
            return false;
        }
        AntecedentesGinecologicos other = (AntecedentesGinecologicos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "consultorio.lgm.datos.entidades.AntecedentesGinecologicos[ id=" + id + " ]";
    }
    
}
