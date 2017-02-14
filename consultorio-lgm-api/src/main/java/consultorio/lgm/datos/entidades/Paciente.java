/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.datos.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "paciente")
public class Paciente implements Serializable {
    private static final long serialVersionUID = -2045723503821654839L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "genero_femenino")
    private boolean generoFemenino;
    @Basic(optional = false)
    @Column(name = "edad")
    private int edad;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "religion")
    private String religion;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @JoinColumn(name = "id_estado_civil", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CatEstadoCivil idEstadoCivil;
    @JoinColumn(name = "id_escolaridad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CatEscolaridad idEscolaridad;
    @JoinColumn(name = "id_antecedentes_ginecologicos", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private AntecedentesGinecologicos idAntecedentesGinecologicos;
    @JoinColumn(name = "id_antecedentes", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private AntecedentesGenerales idAntecedentes;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Usuario usuario;

    public Paciente() {
    }

    public Paciente(Long id) {
        this.id = id;
    }

    public Paciente(Long id, String nombre, String apellidoPaterno, boolean generoFemenino, int edad, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.generoFemenino = generoFemenino;
        this.edad = edad;
        this.direccion = direccion;
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

    public boolean getGeneroFemenino() {
        return generoFemenino;
    }

    public void setGeneroFemenino(boolean generoFemenino) {
        this.generoFemenino = generoFemenino;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public CatEstadoCivil getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(CatEstadoCivil idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public CatEscolaridad getIdEscolaridad() {
        return idEscolaridad;
    }

    public void setIdEscolaridad(CatEscolaridad idEscolaridad) {
        this.idEscolaridad = idEscolaridad;
    }

    public AntecedentesGinecologicos getIdAntecedentesGinecologicos() {
        return idAntecedentesGinecologicos;
    }

    public void setIdAntecedentesGinecologicos(AntecedentesGinecologicos idAntecedentesGinecologicos) {
        this.idAntecedentesGinecologicos = idAntecedentesGinecologicos;
    }

    public AntecedentesGenerales getIdAntecedentes() {
        return idAntecedentes;
    }

    public void setIdAntecedentes(AntecedentesGenerales idAntecedentes) {
        this.idAntecedentes = idAntecedentes;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "consultorio.lgm.datos.entidades.Paciente[ id=" + id + " ]";
    }
    
}
