package consultorio.lgm.seguridad;

import consultorio.lgm.excepciones.ExcepcionAcceso;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class ManejadorAccesoFalla implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, 
					HttpServletResponse httpServletResponse, AuthenticationException authenticationException)
			throws IOException, ServletException {
		if ( authenticationException.getCause() != null && authenticationException.getCause() instanceof ExcepcionAcceso ){
			if (LGMConstantesExcepciones.ACCESO_USUARIO_BLOQUEADO != ((ExcepcionAcceso) authenticationException.getCause()).getNumeroError()) {
				httpServletResponse.sendRedirect( httpServletRequest.getContextPath() + 
						"/web-pages/seguridad/login.jsf?error=" + ((ExcepcionAcceso) authenticationException.getCause()).getNumeroError() );
			} else {
				httpServletResponse.sendRedirect( httpServletRequest.getContextPath() + 
						"/web-pages/seguridad/login.jsf?error=" + ((ExcepcionAcceso) authenticationException.getCause()).getNumeroError()  
						+ "&time=" + authenticationException.getMessage());
			}
			return;
		}
		
		if ( authenticationException.getCause() != null && authenticationException.getCause() instanceof ExcepcionDAO ){
			httpServletResponse.sendRedirect( httpServletRequest.getContextPath() + 
						"/web-pages/seguridad/login.jsf?error=" + ((ExcepcionDAO) authenticationException.getCause()).getNumeroError()  
						+ "&time=" + authenticationException.getMessage());
			return;
		}
		
		
		
		httpServletResponse.sendRedirect( httpServletRequest.getContextPath() + "/web-pages/seguridad/login.jsf?error=0" );
		
		//RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher ( "/web-pages/seguridad/login.jsf?error=1" );
		//requestDispatcher.forward(httpServletRequest, httpServletResponse );
	}

}
