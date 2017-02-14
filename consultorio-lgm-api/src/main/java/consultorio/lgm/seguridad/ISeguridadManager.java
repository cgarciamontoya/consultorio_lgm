package consultorio.lgm.seguridad;

import consultorio.lgm.datos.entidades.Usuario;


public interface ISeguridadManager {
	
	//TODO: Debe regresar un objeto usuario en lugar de void.
	//TODO: Cambiar la excepción SQLException por la excepción en particular (si aplica) que es lanzada cuando ocurre un error por conexión a la base de datos.
	/**
	 * Permite realizar un logueo en el sistema (ó subsistema) de seguridad que se esté utilizando actualmente.
	 * 
	 * @param usuario
	 * @param password
	 * @return Regresa los datos del usuario en caso de que el logueo sea exitoso.
	 * @throws SeguridadException En caso de que el usuario no exista o el password sea incorrecto.
	 * @throws SQLException 
	 */
	public Usuario login (String usuarioStr, String passwordStr);
}
