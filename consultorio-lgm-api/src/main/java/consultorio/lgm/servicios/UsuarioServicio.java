package consultorio.lgm.servicios;

import java.util.List;

import consultorio.lgm.datos.entidades.Usuario;

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
public interface UsuarioServicio {

    /**
     * Permite guardar un {@link Usuario} en el subsistema de persistencia.
     * @param usuario {@link Usuario} objeto que contiene los datos
     *     del usuario.
     * @return {@link Usuario} regresa el usuario con el id del
     *     usuario establecido.
     */
    Usuario guardarUsuario(Usuario usuario);

    /**
     * Permite actualizar un {@link Usuario} en el subsistema de persistencia.
     * @param usuario objeto de tipo {@link Usuario} que contiene los datos del
     * usuario a actualizar.
     */
    void actualizarUsuario(Usuario usuario);

    /**
     * Permite eliminar un {@link Usuario} en el subsistema de persistencia.
     * @param idUsuario {@link Long} ID que corresponde al usuario que
     * se desea eliminar.
     */
    void eliminarUsuario(Integer idUsuario);

    /**
     * Bloquea un {@link Usuario} para que no se pueda loguear en el sistema.
     * @param idUsuario {@link Long} ID que corresponde al usuario que
     * se desea bloquear.
     */
    void bloqueaUsuario(Integer idUsuario);

    /**
     * Permite recuperar un {@link Usuario} en base al nombre corto del usuario.
     * @param usuario Nombre corto del usuario a recuperar.
     * @return Si existe, regresa el {@link Usuario} que coincide con el nombre
     * corto, de lo contrario regresa null.
     */
    Usuario recuperarUsuario(String usuario);

    /**
     * Permite recuperar todos los usuarios del subsistema de persistencia.
     * @return regresa la lista completa de {@link Usuario}s del
     * subsistema de persistencia.
     */
    List<Usuario> recuperarTodos();

    /**
     * Permite recuperar los usuarios del subsistema de persistencia
     * según los atributos proporcionados por el usuario.
     * @param usuario objeto de tipo {@link Usuario} que contiene los datos del
     * usuario a actualizar.
     * @return regresa la lista de {@link Usuario}s del
     * subsistema de persistencia.
     */
    List<Usuario> recuperarPorFiltro(Usuario usuario);
    
    Usuario recuperaPorId(Integer id);

}
