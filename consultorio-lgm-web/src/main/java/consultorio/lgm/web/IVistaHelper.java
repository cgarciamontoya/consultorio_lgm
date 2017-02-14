package consultorio.lgm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Contiene métodos para ayudar a los diferentes componetes a procesar información relacionada con las vistas.
 * 
 * Principios: 	- Composite Reuse Principle
 * 				- Inversión de Dependencia
 * Patrones  : 
 * 
 *  @author Yohualy Montejano Rodríguez
 */

public interface IVistaHelper {
	
	/**
    * Recupera el valor de la llave que recibe como parámetro del archivo de mensajes.
    * 
    * @param llave Llave de la cual se necesita recuperar el valor.
    * @return El valor correspondiente a la llave {@link String}.
    */
   public String recuperaValorMensaje (String llave);
   
   
   /**
    * Agrega un mensaje de error al contexto de Java Server Faces.
    * 
    * @param llaveMensaje Llave del mensaje que será agregada al contexto de JSF.
    */
   public void agregarMensajeError (String llaveMensaje) ;
   
   /**
    * Agrega un mensaje de error al contexto de Java Server Faces sin la necesidad de un mensaje en properties.
    * 
    * @param llaveMensaje Llave del mensaje que será agregada al contexto de JSF.
    */
   public void agregarMensajeErrorSimple(String llaveMensaje) ;
   
   /**
    * Agrega el mensaje de una excepción al contexto de Java Server Faces.
    * 
    * @param exception Excepción de la que se recupera el mensaje que será agregado al contexto de JSF.
    */
   public void agregarMensajeExcepcion (Exception exception) ;
   
   
   /**
    * Agrega un mensaje de éxito al contexto de Java Server Faces.
    * 
    * @param mensaje Mensaje que será agregada al contexto de JSF.
    */
   public void agregarMensajeExito (String mensaje) ;
   
   /**
    * Regresa la petición del contexto.
    * 
    * @return {@link HttpServletRequest}
    */
   public HttpServletRequest recuperaRequest ();
   
   /**
    * Regresa el response.
    * @return HttpServletResponse
    */
   public HttpServletResponse recuperaResponse();
   
   /**
    * Regresa la sesion del usuario.
    * 
    * @return {@link HttpSession}
    */
   public HttpSession recuperaSesion ();
   
   /**
    * Genera un redireccinamiento a la url especificada.
    */
   public void redirect (String url);
}
