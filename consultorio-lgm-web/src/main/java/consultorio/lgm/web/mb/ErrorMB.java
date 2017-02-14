package consultorio.lgm.web.mb;

import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;

import javax.annotation.PostConstruct;
import javax.faces.application.ViewExpiredException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.commons.lang.exception.ExceptionUtils;



import consultorio.lgm.web.IVistaHelper;

@ManagedBean (name="errorMB")
@RequestScoped
public class ErrorMB {
	
	private String error;
	private String detalle;
	
	@ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
	
	public ErrorMB(){
		
	}
	
	public void forceStatusCode200(ComponentSystemEvent event)
	{
		vistaHelper.recuperaResponse().setStatus( HttpServletResponse.SC_OK );
	}
	
	@PostConstruct
	public void muestraError(){
		
		HttpServletRequest request = vistaHelper.recuperaRequest();
		vistaHelper.recuperaResponse().setStatus( HttpServletResponse.SC_OK );
		if (request.getAttribute("javax.servlet.error.exception_type") == null){
			return ;
		}
		
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		
		if ( throwable instanceof ViewExpiredException ){
			error = vistaHelper.recuperaValorMensaje("mensaje.session.expirada");
			return;
		}
		
		if ( throwable instanceof NullPointerException ){
			error = vistaHelper.recuperaValorMensaje("mensaje.null.pointer");
			//detalle = ExceptionUtils.getStackTrace(throwable); // throwable.getStackTrace().toString();
			return;
		}
		
		ExcepcionDAO excepcionDAO; 
		if ( (excepcionDAO = recuperaExcepcionDAO (throwable)) != null ){
			switch (excepcionDAO.getNumeroError() ){
				case LGMConstantesExcepciones.DAO_ERROR_CONEXION_BASE_DATOS:
					error = vistaHelper.recuperaValorMensaje("general.error.bd.no.disponible");
				return;
			}
		}
		
		//ExcepcionServicio excepcionServicio;
		//if ( (excepcionServicio = recuperaExcepcionServicio (throwable)) != null ){
		//	System.out.println ("Ocurrió un excepción servicio con número: " + excepcionServicio.getNumeroError() );
			
		//}
		
		error = vistaHelper.recuperaValorMensaje("general.error.desconocido");
		//detalle = ExceptionUtils.getStackTrace(throwable);
	}
	
	public void  manejaAjaxError(){
		HttpServletRequest request = vistaHelper.recuperaRequest();
		//System.out.println ("\n\n ---- Mensaje Ajax Error: " + request.getAttribute("javax.servlet.error.exception_type") );
	}
	
	
	public ExcepcionServicio recuperaExcepcionServicio (Throwable error){
		while (true){
			if (error instanceof ExcepcionServicio){
				return (ExcepcionServicio)error;
			}
			if (error.getCause() != null){
				error = error.getCause();
			}else{
				return null;
			}
			
		} 
	}
	
	public ExcepcionDAO recuperaExcepcionDAO (Throwable error){
		while (true){
			if (error instanceof ExcepcionDAO){
				return (ExcepcionDAO)error;
			}
			if (error.getCause() != null){
				error = error.getCause();
			}else{
				return null;
			}
			
		} 
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public IVistaHelper getVistaHelper() {
		return vistaHelper;
	}

	public void setVistaHelper(IVistaHelper vistaHelper) {
		this.vistaHelper = vistaHelper;
	}
}
