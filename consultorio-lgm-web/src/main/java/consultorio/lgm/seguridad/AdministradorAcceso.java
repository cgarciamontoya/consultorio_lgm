package consultorio.lgm.seguridad;


import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.excepciones.ExcepcionAcceso;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.excepciones.LGMFabricaExcepciones;
import consultorio.lgm.servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AdministradorAcceso implements AuthenticationProvider {
	
	private static final String USUARIO_REQUERIDO = "login.acceso.usuario.requerido";
	private static final String USUARIO_CHAR_INV = "login.acceso.usuario.caracteres.invalidos";
	private static final String PASS_CHAR_INV = "login.acceso.pass.caracteres.invalidos";
	
    @Autowired
    private IVistaHelper vistasHelper;
    
    @Autowired
    private UsuarioServicio servicioUsuario;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	if (!this.verificaParametros(authentication)) {
    		return authentication;
    	}
    	
    	
    	HttpServletRequest httpServletRequest = 
    	        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    
    	Authentication nuevaAuthentication = null;
     
    	nuevaAuthentication = recuperaObjetoAuthentication(authentication, httpServletRequest);
    	
    	return nuevaAuthentication;
    }
    
    /**
     * Realiza las validaciones de intentos, hora de bloqueo, y que sea la misma contraseña del usuario almacenado 
     * con el usuario que intenta autenticarse.
     * @param authentication contiene las credenciales del usuario
     * @param httpServletRequest contiene los valores del ServletRequest
     * @return Authentication
     */
    private Authentication recuperaObjetoAuthentication(Authentication authentication, HttpServletRequest httpServletRequest) {
    	
    	Usuario usuario = null;
		try {
                    usuario = servicioUsuario.recuperarUsuario(authentication.getName());
		} catch (ExcepcionDAO excepcion) {
                    throw new BadCredentialsException(vistasHelper.recuperaValorMensaje(Constantes.ERROR_CONEXION_BD_LOGIN) , excepcion);
		}
    	if (usuario == null) {
    	    throw new BadCredentialsException(vistasHelper.recuperaValorMensaje(Constantes.USUARIO_PASSWORD_INCORRECTO));
    	}
        
        if (!usuario.getContrasena().equals(authentication.getCredentials())) {
            throw new BadCredentialsException(vistasHelper.recuperaValorMensaje(Constantes.USUARIO_PASSWORD_INCORRECTO));
        }
    	
    	//Ponemos el nombre de usuario en sesion.
    	httpServletRequest.getSession().setAttribute("usuario", 
    	        usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " + usuario.getApellidoMaterno());
        httpServletRequest.getSession().setAttribute("nombreUsuario", authentication.getName());
    	
    	List<GrantedAuthority> authorities = new ArrayList<>();
         authorities.add ( new SimpleGrantedAuthority("ROLE_AUTHENTICATED") );
         
        if (usuario.getUsuario().equalsIgnoreCase("lauragm") || 
                usuario.getUsuario().equalsIgnoreCase("admin")) {
            authorities.add ( new SimpleGrantedAuthority("ROLE_ADMIN") );
        }
    	
    	Authentication nuevaAuthentication = new UsernamePasswordAuthenticationToken(authentication.getName(),
                authentication.getCredentials(), authorities);
    	return nuevaAuthentication;
    }
    
    private boolean verificaParametros(Authentication authentication)
    {
	    if (authentication == null || authentication.getName() == null
				|| authentication.getName().isEmpty() || authentication.getCredentials() == null 
				|| ((String) authentication.getCredentials()).isEmpty()) {
	    	
	    	ExcepcionAcceso excepcion = 
	                LGMFabricaExcepciones.recuperaInstancia().crear(ExcepcionAcceso.class, 
	                        LGMConstantesExcepciones.ACCESO_USUARIO_REQUERIDO);
	        throw new BadCredentialsException(vistasHelper.recuperaValorMensaje(USUARIO_REQUERIDO), excepcion);
		}
		//Validando los datos usados para el logueo
		if (!authentication.getName().matches("([a-zA-Z0-9_]){1,20}")) {
			ExcepcionAcceso excepcion = 
	                LGMFabricaExcepciones.recuperaInstancia().crear(ExcepcionAcceso.class, 
	                        LGMConstantesExcepciones.ACCESO_USUARIO_CHAR_INV);
	        throw new BadCredentialsException(vistasHelper.recuperaValorMensaje(USUARIO_CHAR_INV), excepcion);
		}
		if (!((String) authentication.getCredentials()).matches("([^'-\\/?\";:=[.,+*]]){1,20}")) {
			ExcepcionAcceso excepcion = 
	                LGMFabricaExcepciones.recuperaInstancia().crear(ExcepcionAcceso.class, 
	                        LGMConstantesExcepciones.ACCESO_PASS_CHAR_INV);
	        throw new BadCredentialsException(vistasHelper.recuperaValorMensaje(PASS_CHAR_INV), excepcion);
		}
		return true;
    }
    
    @Override
    public boolean supports(Class<?> arg0) {
    	return true;
    }
    
    // ----------------------------- Getters y Setters ----------------------------------------
    /**
     * Setter para vistasHelper
     * @param helperVistas instancia de VistaHelper
     */
    public void setVistasHelper(IVistaHelper helperVistas) {
    	this.vistasHelper = helperVistas;
    }
    
    /**
     * Setter para usuarioServicio
     * @param usuarioServicio instancia de UsuarioServicio
     */
    public void setServicioUsuario(UsuarioServicio usuarioServicio) {
    	this.servicioUsuario = usuarioServicio;
    }
}
