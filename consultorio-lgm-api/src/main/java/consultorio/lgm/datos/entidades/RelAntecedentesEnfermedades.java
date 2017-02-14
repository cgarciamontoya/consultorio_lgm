/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.datos.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "rel_antecedentes_enfermedades")
public class RelAntecedentesEnfermedades implements Serializable {
    private static final long serialVersionUID = 2075949923055926766L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "id_enfermedad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CatEnfermedadesCronicas idEnfermedad;
    @JoinColumn(name = "id_antecedentes", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AntecedentesGenerales idAntecedentes;

    public RelAntecedentesEnfermedades() {
    }

    public RelAntecedentesEnfermedades(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatEnfermedadesCronicas getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(CatEnfermedadesCronicas idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public AntecedentesGenerales getIdAntecedentes() {
        return idAntecedentes;
    }

    public void setIdAntecedentes(AntecedentesGenerales idAntecedentes) {
        this.idAntecedentes = idAntecedentes;
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
        if (!(object instanceof RelAntecedentesEnfermedades)) {
            return false;
        }
        RelAntecedentesEnfermedades other = (RelAntecedentesEnfermedades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "consultorio.lgm.datos.entidades.RelAntecedentesEnfermedades[ id=" + id + " ]";
    }
    
}
