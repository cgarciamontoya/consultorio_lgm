package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;
import consultorio.lgm.integracion.dao.basedatos.UsuarioDAO;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UsuarioDAOImpl extends DAOBDGenerico<Usuario, Integer> implements UsuarioDAO {

    /**
     * Constructor de la clase
     */
    public UsuarioDAOImpl() {
        super(Usuario.class);
    }

    @Override
    public Usuario recuperarPorId(Integer idUsuario) {
        if (idUsuario == null) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_VALORES_NULL);
        }
        try {
            Query query = super.entityManager.createNamedQuery("Usuario.recuperarPorId");
            query.setParameter("id", idUsuario);
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e); 
        }
    }

    @Override
    public Usuario recuperarPorUsuario(String usuario) {
        if (usuario == null || usuario.isEmpty()) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_VALORES_NULL);
        }
        try {
            Query query = super.entityManager.createNamedQuery("Usuario.recuperarPorUsuario");
            query.setParameter("usuario", usuario);
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Usuario> recuperarTodos() {
        try {
            Query query = super.entityManager.createNamedQuery("Usuario.recuperarTodos");
            return (List<Usuario>) query.getResultList();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Usuario> recuperarPorFiltro(Usuario usuario) {
        if (usuario.getNombre() == null  
                && usuario.getUsuario() == null 
                && usuario.getApellidoPaterno() == null 
                && usuario.getApellidoMaterno() == null 
                && usuario.getCorreo() == null
                && usuario.getIniciales() == null) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_FILTRO_VALORES_NULL);
        }
        try {
            Query query = super.entityManager.createNamedQuery("Usuario.recuperarPorFiltro");
            query.setParameter("nombre", usuario.getNombre());
            query.setParameter("usuario", usuario.getUsuario());
            query.setParameter("apellidoPaterno", usuario.getApellidoPaterno());
            query.setParameter("apellidoMaterno", usuario.getApellidoMaterno());
            query.setParameter("correo", usuario.getCorreo());
            query.setParameter("iniciales", usuario.getIniciales());
            return (List<Usuario>) query.getResultList();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
}
