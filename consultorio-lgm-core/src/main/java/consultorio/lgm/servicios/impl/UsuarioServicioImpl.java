package consultorio.lgm.servicios.impl;

import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.UsuarioDAO;
import consultorio.lgm.servicios.ServicioBase;
import consultorio.lgm.servicios.UsuarioServicio;
import java.io.Serializable;
import java.util.List;




/** Requerimientos cubiertos:RF-Usuarios-01.
 * RF-Usuarios-02
 * RF-Usuarios-03
 * RF-Usuarios-04
 * RF-Usuarios-05
 * RF-Usuarios-07
 * RF-Usuarios-08
 * Descripción: Éste componente representa la lógica del negocio en cuanto a la 
 * sección de usuarios dentro del Sistema de Certificado Firma Digital
 * @author mmarquez
 * Fecha creación:  25/03/2014
*/
public class UsuarioServicioImpl extends ServicioBase implements Serializable, UsuarioServicio {

    

    private static final long serialVersionUID = 1L;

    private UsuarioDAO usuarioDAO;

    /**
     * Constructor de la clase.
     */
    public UsuarioServicioImpl() {
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        validarDatosComunesUsuario(usuario);

        try {
            Usuario usuarioAlmacenado = usuarioDAO.recuperarPorUsuario(usuario.getUsuario());
            if (usuarioAlmacenado != null) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_USUARIO_DUPLICADO);
            }
            return usuarioDAO.guardarYVacia(usuario);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, e.getNumeroError());
        }

    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        validarDatosComunesUsuario(usuario);
        try {
            Usuario usuarioActual = usuarioDAO.recuperarPorId(usuario.getIdUsuario());
            if (usuarioActual == null) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_USUARIO_NO_EXISTE);
            }
            // Lanzamos una excepción si se intenta cambiar el nombre corto del
            // usuario.
            if (!usuario.getUsuario().equals(usuarioActual.getUsuario())) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class,
                        LGMConstantesExcepciones.SERVICIO_USUARIO_NOMBRE_CORTO_DIFERENTE);
            }
    
            usuarioDAO.guardarYVacia(usuario);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, e.getNumeroError());
        }
    }

    /**
     * Valida los datos que siempre debe tener el usuario.
     * @param usuario usuario a validar. 
     */
    private void validarDatosComunesUsuario(Usuario usuario) {
        if (usuario == null || usuario.getUsuario() == null || usuario.getUsuario().isEmpty() 
                || usuario.getContrasena() == null || usuario.getContrasena().isEmpty()
                || usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno().isEmpty()
                || usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno().isEmpty()) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
    }

    @Override
    public void eliminarUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        try {
            Usuario usuarioActual = usuarioDAO.recuperarPorId(idUsuario);
            if (usuarioActual == null) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_USUARIO_NO_EXISTE);
            }
            usuarioDAO.eliminar(usuarioActual.getIdUsuario());
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, e.getNumeroError());
        }
    }

    @Override
    public void bloqueaUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        try {
            Usuario usuarioActual = usuarioDAO.recuperarPorId(idUsuario);
            if (usuarioActual == null) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_USUARIO_NO_EXISTE);
            }
            usuarioDAO.guardarYVacia(usuarioActual);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, e.getNumeroError());
        }
    }

    @Override
    public Usuario recuperarUsuario(String usuarioStr) {
        if (usuarioStr == null || usuarioStr.isEmpty()) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        try {
            return usuarioDAO.recuperarPorUsuario(usuarioStr); 
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, e.getNumeroError());
        }
    }

    @Override
    public List<Usuario> recuperarTodos() {
        try {
            return usuarioDAO.recuperarTodos();
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, e.getNumeroError());
        }
    }

    @Override
    public List<Usuario> recuperarPorFiltro(Usuario usuario) {
        if (usuario == null) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
        }
        try {
            return usuarioDAO.recuperarPorFiltro(usuario); 
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, e.getNumeroError());
        }
    }

    // ------------------------------- Setters y Getters
    // -------------------------------------
    /**
     * Setter para UsuarioDAO
     * @param daoUsuario instancia de UsuarioDAO
     */
    public void setUsuarioDAO(UsuarioDAO daoUsuario) {
        this.usuarioDAO = daoUsuario;
    }

}
