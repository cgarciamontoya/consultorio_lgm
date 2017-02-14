package consultorio.lgm.seguridad;

import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.seguridad.ISeguridadManager;
import consultorio.lgm.servicios.UsuarioServicio;

public class SeguridadManager implements ISeguridadManager {

	private UsuarioServicio servicioUsuario ;
	
	/**
	 * Constructor de la clase.
	 */
	public SeguridadManager () {
		super();
	}
	
	@Override
	public Usuario login(String usuarioStr, String passwordStr ) {
		if ( usuarioStr == null || usuarioStr.isEmpty() ){
			return null;
		}
		
		Usuario usuario= servicioUsuario.recuperarUsuario( usuarioStr );
		if ( usuario != null && ! usuario.getContrasena().equals( passwordStr ) ){
			return null;
		}
			
		return usuario;
		
	}
	
	// ----------------------------- Getters y Setters ---------------------------------
	public void setServicioUsuario(UsuarioServicio servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

}
