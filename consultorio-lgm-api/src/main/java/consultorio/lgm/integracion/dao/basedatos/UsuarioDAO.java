package consultorio.lgm.integracion.dao.basedatos;

import java.util.List;

import consultorio.lgm.datos.entidades.Usuario;

public interface UsuarioDAO extends IDAOBDGenerico <Usuario, Integer> {

    /**
     * Recupera un {@link Usuario} por el atributo de id.
     * @param idUsuario identifiador del usuario a buscar
     * @return regresa un {@link Usuario}
     */
    Usuario recuperarPorId(Integer idUsuario);

    /**
     * Recupera un {@link Usuario} por el atributo de usuario.
     * @param usuario nombre del usuario con el que se identifica.
     * @return regresa un {@link Usuario}
     */
    Usuario recuperarPorUsuario(String usuario);

    /**
     * Recupera todos los Usuarios del sistema que no esten eliminados.
     * @return regresa la lista de {@link Usuario}s
     */
    List<Usuario> recuperarTodos();

   /**
    * Método para la búsqueda y filtrado de los registros de usuario,
    * según los parámetros pasados en la entidad.
    * @param usuario objeto de tipo  {@link Usuario} con los datos a buscar.
    * @return lista de usuarios que coinciden con los filtros.
    */
    List<Usuario> recuperarPorFiltro(Usuario usuario);

}
