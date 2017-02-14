/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.datos.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "antecedentes_generales")
public class AntecedentesGenerales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "carga_genetica_paternal")
    private String cargaGeneticaPaternal;
    @Column(name = "carga_genetica_maternal")
    private String cargaGeneticaMaternal;
    @Column(name = "vivienda")
    private String vivienda;
    @Column(name = "alimentacion")
    private String alimentacion;
    @Column(name = "higiene")
    private String higiene;
    @Column(name = "toxicomanias")
    private String toxicomanias;
    @Column(name = "quirurgicas")
    private String quirurgicas;
    @Column(name = "hospitalizaciones")
    private Boolean hospitalizaciones;
    @Column(name = "motivo_hospitalizacion")
    private String motivoHospitalizacion;
    @Column(name = "transfuciones")
    private Boolean transfuciones;
    @Column(name = "motivo_transfuciones")
    private String motivoTransfuciones;
    @Column(name = "traumaticas")
    private Boolean traumaticas;
    @Column(name = "motivo_traumaticas")
    private String motivoTraumaticas;
    @Column(name = "alergias")
    private Boolean alergias;
    @Column(name = "descripcion_alergias")
    private String descripcionAlergias;
    @Column(name = "rh_positivo")
    private Boolean rhPositivo;
    @Transient
    private List<RelAntecedentesEnfermedades> relAntecedentesEnfermedadesList;
    @JoinColumn(name = "id_grupo_sanguineo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CatGrupoSanguineo idGrupoSanguineo;

    public AntecedentesGenerales() {
    }

    public AntecedentesGenerales(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargaGeneticaPaternal() {
        return cargaGeneticaPaternal;
    }

    public void setCargaGeneticaPaternal(String cargaGeneticaPaternal) {
        this.cargaGeneticaPaternal = cargaGeneticaPaternal;
    }

    public String getCargaGeneticaMaternal() {
        return cargaGeneticaMaternal;
    }

    public void setCargaGeneticaMaternal(String cargaGeneticaMaternal) {
        this.cargaGeneticaMaternal = cargaGeneticaMaternal;
    }

    public String getVivienda() {
        return vivienda;
    }

    public void setVivienda(String vivienda) {
        this.vivienda = vivienda;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public String getHigiene() {
        return higiene;
    }

    public void setHigiene(String higiene) {
        this.higiene = higiene;
    }

    public String getToxicomanias() {
        return toxicomanias;
    }

    public void setToxicomanias(String toxicomanias) {
        this.toxicomanias = toxicomanias;
    }

    public String getQuirurgicas() {
        return quirurgicas;
    }

    public void setQuirurgicas(String quirurgicas) {
        this.quirurgicas = quirurgicas;
    }

    public Boolean getHospitalizaciones() {
        return hospitalizaciones;
    }

    public void setHospitalizaciones(Boolean hospitalizaciones) {
        this.hospitalizaciones = hospitalizaciones;
    }

    public String getMotivoHospitalizacion() {
        return motivoHospitalizacion;
    }

    public void setMotivoHospitalizacion(String motivoHospitalizacion) {
        this.motivoHospitalizacion = motivoHospitalizacion;
    }

    public Boolean getTransfuciones() {
        return transfuciones;
    }

    public void setTransfuciones(Boolean transfuciones) {
        this.transfuciones = transfuciones;
    }

    public String getMotivoTransfuciones() {
        return motivoTransfuciones;
    }

    public void setMotivoTransfuciones(String motivoTransfuciones) {
        this.motivoTransfuciones = motivoTransfuciones;
    }

    public Boolean getTraumaticas() {
        return traumaticas;
    }

    public void setTraumaticas(Boolean traumaticas) {
        this.traumaticas = traumaticas;
    }

    public String getMotivoTraumaticas() {
        return motivoTraumaticas;
    }

    public void setMotivoTraumaticas(String motivoTraumaticas) {
        this.motivoTraumaticas = motivoTraumaticas;
    }

    public Boolean getAlergias() {
        return alergias;
    }

    public void setAlergias(Boolean alergias) {
        this.alergias = alergias;
    }

    public String getDescripcionAlergias() {
        return descripcionAlergias;
    }

    public void setDescripcionAlergias(String descripcionAlergias) {
        this.descripcionAlergias = descripcionAlergias;
    }

    public Boolean getRhPositivo() {
        return rhPositivo;
    }

    public void setRhPositivo(Boolean rhPositivo) {
        this.rhPositivo = rhPositivo;
    }

    public List<RelAntecedentesEnfermedades> getRelAntecedentesEnfermedadesList() {
        return relAntecedentesEnfermedadesList;
    }

    public void setRelAntecedentesEnfermedadesList(List<RelAntecedentesEnfermedades> relAntecedentesEnfermedadesList) {
        this.relAntecedentesEnfermedadesList = relAntecedentesEnfermedadesList;
    }

    public CatGrupoSanguineo getIdGrupoSanguineo() {
        return idGrupoSanguineo;
    }

    public void setIdGrupoSanguineo(CatGrupoSanguineo idGrupoSanguineo) {
        this.idGrupoSanguineo = idGrupoSanguineo;
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
        if (!(object instanceof AntecedentesGenerales)) {
            return false;
        }
        AntecedentesGenerales other = (AntecedentesGenerales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "consultorio.lgm.datos.entidades.AntecedentesGenerales[ id=" + id + " ]";
    }
    
}
