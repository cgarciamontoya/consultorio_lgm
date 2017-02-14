package consultorio.lgm.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VistaJSFHelper implements IVistaHelper, Serializable {
	
	private static final long serialVersionUID = 8190993233209780819L;

	@Override
	public String recuperaValorMensaje(String llave) {
		
		 try {
		       	ResourceBundle bundle = ResourceBundle.getBundle( "mensajes" );
		       	return bundle.getString( llave );
		 } catch (MissingResourceException e) {
			 return null;
		 }
	}

	@Override
	public void agregarMensajeError (String llaveMensaje) {
		String mensaje = recuperaValorMensaje ( llaveMensaje );
		if ( mensaje == null)
			throw new RuntimeException ("No se encontró ningún mensaje para la llave: " + llaveMensaje );
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.addMessage ( null, facesMessage );
		}

	}
	
	@Override
	public void agregarMensajeErrorSimple(String mensaje) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			context.addMessage ( null, facesMessage );
		}

	}

	@Override
	public void agregarMensajeExcepcion(Exception exception) {
		 String mensaje = exception.getLocalizedMessage();
	     FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje , mensaje );
	     FacesContext.getCurrentInstance().addMessage( null, facesMessage);
	}

	@Override
	public void agregarMensajeExito(String llaveMensaje) {
		String mensaje = recuperaValorMensaje ( llaveMensaje );
		if ( mensaje == null)
			throw new RuntimeException ("No se encontró ningún mensaje para la llave: " + llaveMensaje );
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	@Override
	public HttpServletRequest recuperaRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			return (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest() ) ;
		}
		return null;
	}
	
	@Override
	public HttpServletResponse recuperaResponse() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			return (HttpServletResponse) (FacesContext.getCurrentInstance().getExternalContext().getResponse() ) ;
		}
		return null;
	}

	@Override
	public HttpSession recuperaSesion() {
		return recuperaRequest().getSession( false );
	}

	@Override
	public void redirect (String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect( recuperaRequest().getContextPath() + url );
		} catch (IOException e) {
			throw new RuntimeException ( e );
		}
		
	}
	
	

}
