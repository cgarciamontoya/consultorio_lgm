package consultorio.lgm.web.mb;

import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.servicios.UsuarioServicio;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import consultorio.lgm.web.IVistaHelper;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "usuariosMB")
@ViewScoped
public class MBUsuarios implements Serializable {

    private static final long serialVersionUID = 7861673692979758604L;
    private static final String USUARIO_SESSION_ATRIBUTO = "nombreUsuario";
    @ManagedProperty(value = "#{servicioUsuario}")
    private UsuarioServicio servicioUsuario;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;
    private String confirmaPassword;
    private Usuario usuarioSeleccionado;
    private String nuevaContrasenia;
    private String actualContrasenia;
    private boolean edicion;
 

    /**
     * Constructor
     */
    public MBUsuarios() {
    }

    /**
     * PostConstructor
     */
    @PostConstruct
    private void init() {
        usuarios = servicioUsuario.recuperarTodos();
        usuario = new Usuario();
    }

    /**
     * Guarda o actualiza el usuarios introducido
     */
    public void guarda() {
       
        if (!sonDatosValidos()) {
            
            return;
        }
        //Actualizando el usuario
        if (usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
            try {
                servicioUsuario.actualizarUsuario(usuario);
                vistaHelper.agregarMensajeExito("usuario.actualizado.correcto");
                this.inicializaDatos();
                usuarios = servicioUsuario.recuperarTodos();
            } catch (ExcepcionServicio e) {
                String errorMjs = "usuario.guardar.error";

                switch (e.getNumeroError()) {
                    case LGMConstantesExcepciones.SERVICIO_USUARIO_DUPLICADO:
                        errorMjs = "usuario.error.duplicado";
                        break;
                    case LGMConstantesExcepciones.SERVICIO_USUARIO_NOMBRE_CORTO_DIFERENTE:
                        errorMjs = "usuario.error.cambiarnombrecorto";
                        break;
                    case LGMConstantesExcepciones.SERVICIO_USUARIO_NO_EXISTE:
                        errorMjs = "usuario.error.noexiste";
                        break;
                    default:
                        break;
                }
                vistaHelper.agregarMensajeError(errorMjs);
            }
            return;
        }

        try {
            servicioUsuario.guardarUsuario(usuario);
            this.inicializaDatos();
            usuarios = servicioUsuario.recuperarTodos();
            vistaHelper.agregarMensajeExito("usuario.guardado.correcto");
        } catch (ExcepcionServicio e) {
            String errorMjs = "usuario.guardar.error";
            switch (e.getNumeroError()) {
                case LGMConstantesExcepciones.SERVICIO_USUARIO_DUPLICADO:
                    errorMjs = "usuario.error.duplicado";
                    break;
                case LGMConstantesExcepciones.SERVICIO_USUARIO_NOMBRE_CORTO_DIFERENTE:
                    errorMjs = "usuario.error.cambiarnombrecorto";
                    break;
                case LGMConstantesExcepciones.SERVICIO_USUARIO_NO_EXISTE:
                    errorMjs = "usuario.error.noexiste";
                    break;
                default:
                    break;
            }
            vistaHelper.agregarMensajeError(errorMjs);
            usuario.setIdUsuario(null);
        } 


    }
    
    /**
     * Valida que los datos introducidos por el usuario sean correctos o tenga algun valor
     * @return boolean indica que es si son validos o no.
     */
    private boolean sonDatosValidos() {
        //Valida que los datos requeridos no estén vacíos
        boolean valido = true;
        if (usuario.getUsuario().isEmpty() || usuario.getNombre().isEmpty() || usuario.getApellidoPaterno().isEmpty()
                || usuario.getApellidoMaterno().isEmpty()) {
            if (usuario.getUsuario().isEmpty()) {
                vistaHelper.agregarMensajeError("usuario.datos.vacios.usuario");
                valido = false;
            }
            if (usuario.getNombre().isEmpty()) {
                vistaHelper.agregarMensajeError("usuario.datos.vacios.nombre");
                valido = false;
            }
            if (usuario.getApellidoPaterno().isEmpty()) {
                vistaHelper.agregarMensajeError("usuario.datos.vacios.ap.paterno");
                valido = false;
            }
            if (usuario.getApellidoMaterno().isEmpty()) {
                vistaHelper.agregarMensajeError("usuario.datos.vacios.ap.materno");
                valido = false;
            }

        } else {

            //Valida los caracteres y longitudes de usuario, password, apellido paterno, apellido materno y email
            if (usuario.getUsuario() != null && !usuario.getUsuario().matches("([a-zA-Z0-9_]){1,20}")) {
                vistaHelper.agregarMensajeError("usuario.datos.invalidos.usuario");
                valido = false;
            }
            if (usuario.getUsuario().length() < 6) {
                vistaHelper.agregarMensajeError("usuario.datos.invalidos.usuario.minimo");
                valido = false;
            }
            if (usuario.getContrasena() != null && !usuario.getContrasena().matches("([^'-\\/?\";:=[.,+*]]){1,20}")) {
                vistaHelper.agregarMensajeError("usuario.datos.invalidos.contrasenia");
                valido = false;
            }
            if (usuario.getNombre() != null && !usuario.getNombre().matches("([a-zA-Z ]){1,60}")) {
                vistaHelper.agregarMensajeError("usuario.datos.invalidos.nombre");
                valido = false;
            }
            if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().matches("([a-zA-Z ]){1,60}")) {
                vistaHelper.agregarMensajeError("usuario.datos.invalidos.ap.paterno");
                valido = false;
            }
            if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().matches("([a-zA-Z ]){1,60}")) {
                vistaHelper.agregarMensajeError("usuario.datos.invalidos.ap.materno");
                valido = false;
            }

            if (usuario.getIdUsuario() == null) {
                if (usuario.getContrasena().isEmpty()) {
                    vistaHelper.agregarMensajeError("usuario.datos.vacios.contrasenia");
                    valido = false;
                }
                if (usuario.getContrasena().length() < 6) {
                    vistaHelper.agregarMensajeError("usuario.datos.invalidos.contrasenia.minimo");
                    valido = false;
                }
                if (!usuario.getContrasena().equals(confirmaPassword)) {
                    vistaHelper.agregarMensajeError("usuario.datos.invalidos.contrasenia");
                    valido = false;
                }
            }
        }
        return valido;
    }

    /**
     * Realiza la busqueda de los usuarios por filtro
     */
    public void buscar() {
        if (usuario != null) {
            usuario.setUsuario(usuario.getUsuario().isEmpty() ? null : usuario.getUsuario());
            usuario.setNombre(usuario.getNombre().isEmpty() ? null : usuario.getNombre());
            usuario.setApellidoPaterno(usuario.getApellidoPaterno().isEmpty() ? null : usuario.getApellidoPaterno());
            usuario.setApellidoMaterno(usuario.getApellidoMaterno().isEmpty() ? null : usuario.getApellidoMaterno());
            usuario.setCedula(usuario.getCedula().isEmpty() ? null : usuario.getCedula());
            usuario.setIniciales(usuario.getIniciales().isEmpty() ? null : usuario.getIniciales());
            if (usuario.getNombre() == null  
                    && usuario.getUsuario() == null 
                    && usuario.getApellidoPaterno() == null 
                    && usuario.getApellidoMaterno() == null 
                    && usuario.getCedula()== null
                    && usuario.getIniciales() == null) {
                usuarios = servicioUsuario.recuperarTodos();
            } else {
                usuarios = servicioUsuario.recuperarPorFiltro(usuario);
            }
        }
    }

    /**
     * Elimina el usuario seleccionado
     */
    public void elimina() {
        try {
            servicioUsuario.eliminarUsuario(usuarioSeleccionado.getIdUsuario());

            vistaHelper.agregarMensajeExito("usuario.eliminado.correcto");
            init();
        } catch (ExcepcionServicio e) {
            String errorMjs = "perfil.eliminar.error";
            if (e.getNumeroError() == LGMConstantesExcepciones.SERVICIO_USUARIO_NO_EXISTE) {
                errorMjs = "usuario.error.noexiste";
            }
            vistaHelper.agregarMensajeError(errorMjs);

        }
    }

    /**
     * Asigna el usuario seleccionado en el datatable a un objeto de tipo usuario
     * @param evento SelectEvent
     */
    public void usuarioSeleccion(SelectEvent evento) {
        usuarioSeleccionado = (Usuario) evento.getObject();
        usuario = usuarioSeleccionado;
        edicion = true;
    }
    
    /**
     * Asigna el usuario seleccionado en el datatable a un objeto de tipo usuario
     */
    public void edita() {
        usuario = usuarioSeleccionado;
    }

    /**
     * Limpia todos los campos e inicializa la oficina
     */
    public void cancelaEdicion() {
        limpiaUsuario();
        edicion = false;
    }

    /**
     * Limpia los campos de la edicion de usuario.
     */
    public void limpiaUsuario() {
        init();

    }
    
    /**
     * Inicializa el usuario, roles, y el idOficinaSeleccianda
     */
    private void inicializaDatos() {
        usuario = new Usuario();
    }

    /**
     * Cambia la contraseña del usuario.
     */
    public void cambiarContrasenia() {
        boolean valido = true;
        Usuario usuarioLogin = servicioUsuario.recuperarUsuario(
                (String) vistaHelper.recuperaRequest().getSession().getAttribute(USUARIO_SESSION_ATRIBUTO));
        if (actualContrasenia.isEmpty() || nuevaContrasenia.isEmpty() || confirmaPassword.isEmpty()) {
            vistaHelper.agregarMensajeError("usuario.datos.vacios.contrasenia");
            valido = false;
        }
        if (!usuarioLogin.getContrasena().equals(actualContrasenia)) {
            vistaHelper.agregarMensajeError("usuario.error.diferentes.contrasenias.actual");
            valido = false;
        }
        if (!nuevaContrasenia.matches("([^'-\\/?\";:=[.,+*]]){1,20}")) {
            vistaHelper.agregarMensajeError("usuario.datos.invalidos.contrasenia");
            valido = false;
        }
        if (!nuevaContrasenia.equals(confirmaPassword)) {
            vistaHelper.agregarMensajeError("usuario.error.diferentes.contrasenias.nueva");
            valido = false;
        }
        if (nuevaContrasenia.length() < 6) {
            vistaHelper.agregarMensajeError("usuario.datos.invalidos.contrasenia.minimo");
            valido = false;
        }
        if (valido) {
            usuarioLogin.setContrasena(nuevaContrasenia);
            try {
                servicioUsuario.actualizarUsuario(usuarioLogin);
                vistaHelper.agregarMensajeExito("usuario.actualizado.contrasenia.correcto");
                actualContrasenia = "";
                nuevaContrasenia = "";
                confirmaPassword = "";
            } catch(ExcepcionServicio e) {
                vistaHelper.agregarMensajeError("usuario.buscar.error.contrasenia");
            }
            
        }
    }
    
    
    public void limpiaPasswords(CloseEvent event) {  
        actualContrasenia = "";
        nuevaContrasenia = "";
        confirmaPassword = ""; 
    } 

    // -------------------- Getters y Setters ----------------------------
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public UsuarioServicio getServicioUsuario() {
        return servicioUsuario;
    }

    public void setServicioUsuario(UsuarioServicio servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public IVistaHelper getVistaHelper() {
        return vistaHelper;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }

    public String getConfirmaPassword() {
        return confirmaPassword;
    }

    public void setConfirmaPassword(String confirmaPassword) {
        this.confirmaPassword = confirmaPassword;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    public String getNuevaContrasenia() {
        return nuevaContrasenia;
    }

    public void setNuevaContrasenia(String nuevaContrasenia) {
        this.nuevaContrasenia = nuevaContrasenia;
    }

    public String getActualContrasenia() {
        return actualContrasenia;
    }

    public void setActualContrasenia(String actualContrasenia) {
        this.actualContrasenia = actualContrasenia;
    }

    public boolean isEdicion() {
        return edicion;
    }

    public void setEdicion(boolean edicion) {
        this.edicion = edicion;
    }
    
}
