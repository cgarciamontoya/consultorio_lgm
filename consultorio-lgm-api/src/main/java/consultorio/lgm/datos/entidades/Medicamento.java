/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.datos.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "medicamento")
public class Medicamento implements Serializable {
    private static final long serialVersionUID = 4551238568807131806L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "antibiotico")
    private boolean antibiotico;
    @JoinColumn(name = "presentacion", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CatPresentacion presentacion;

    public Medicamento() {
    }

    public Medicamento(Long id) {
        this.id = id;
    }

    public Medicamento(Long id, String nombre, boolean antibiotico) {
        this.id = id;
        this.nombre = nombre;
        this.antibiotico = antibiotico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getAntibiotico() {
        return antibiotico;
    }

    public void setAntibiotico(boolean antibiotico) {
        this.antibiotico = antibiotico;
    }

    public CatPresentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(CatPresentacion presentacion) {
        this.presentacion = presentacion;
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
        if (!(object instanceof Medicamento)) {
            return false;
        }
        Medicamento other = (Medicamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "consultorio.lgm.datos.entidades.Medicamento[ id=" + id + " ]";
    }

}
