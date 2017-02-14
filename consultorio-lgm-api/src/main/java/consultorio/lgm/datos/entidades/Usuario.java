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
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedStringType;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "usuario")
@TypeDefs(value={
        @TypeDef(name = "cadenaCifrada", typeClass = EncryptedStringType.class
                , parameters = { @Parameter (name = "encryptorRegisteredName", value = "cifradorCadenaHibernate") })
        ,@TypeDef(name = "fixedCadenaCifrada", typeClass = EncryptedStringType.class
        , parameters = { @Parameter (name = "encryptorRegisteredName", value = "fixedCifradorCadenaHibernate") })
})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer idUsuario;
    @Type(type = "fixedCadenaCifrada")
    @Basic(optional = false)
    @Column(name = "usuario", length=255)
    private String usuario;
    @Type(type = "cadenaCifrada")
    @Basic(optional = false)
    @Column(name = "contrasena", length=255)
    private String contrasena;
    @Type(type = "fixedCadenaCifrada")
    @Basic(optional = false)
    @Column(name = "nombre", length=255)
    private String nombre;
    @Type(type = "fixedCadenaCifrada")
    @Basic(optional = false)
    @Column(name = "apellidopaterno", length=255)
    private String apellidoPaterno;
    @Type(type = "fixedCadenaCifrada")
    @Basic(optional = false)
    @Column(name = "apellidomaterno", length=255)
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "iniciales")
    private String iniciales;
    @Column(name = "correo")
    private String correo;
    @Column(name = "cedula")
    private String cedula;

    public Usuario() {
    }

    public Usuario(Integer idusuario) {
        this.idUsuario = idusuario;
    }

    public Usuario(Integer idusuario, String usuario, String contrasena, String nombre, String apellidopaterno, String apellidomaterno, String iniciales) {
        this.idUsuario = idusuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellidoPaterno = apellidopaterno;
        this.apellidoMaterno = apellidomaterno;
        this.iniciales = iniciales;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario[ idusuario=" + idUsuario + " ]";
    }
    
}
