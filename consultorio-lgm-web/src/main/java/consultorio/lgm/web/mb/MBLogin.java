/* * 
 *Descripción: Este componente permite autenticar al usuario con el fin de ingresar al sistema Conciliación Bancaria.
 *Autor: hanshernandez
 *Fecha de creación: 28/03/2014
*/

package consultorio.lgm.web.mb;

import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import consultorio.lgm.web.IVistaHelper;

@ManagedBean (name="loginMB")
@RequestScoped
public class MBLogin {
    private String error;

    private static final String ERROR_INVALIDO = "login.acceso.numero.error.invalido";
    private static final String USUARIO_PASS_INCORRECTO = "login.acceso.usuariopassword.incorrecto";
    private static final String USUARIO_BLOQUEADO = "login.acceso.usuario.bloqueado";
    private static final String USUARIO_REQUERIDO = "login.acceso.usuario.requerido";
    private static final String USUARIO_CHAR_INV = "login.acceso.usuario.caracteres.invalidos";
    private static final String PASS_CHAR_INV = "login.acceso.pass.caracteres.invalidos";
    private static final String ERROR_CONEXION_BASE_DATOS = "general.error.bd.no.disponible.login";
   
    @ManagedProperty (value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    /**
     * 
     */
    public MBLogin() {}

    /**
     * 
     * @return 
     */
    public String logout (){
        vistaHelper.recuperaSesion().invalidate();
        vistaHelper.redirect("/web-pages/seguridad/login.jsf");
        return "";
    }
    /**
     * 
     */
    public void muestraMensajeError (){
            vistaHelper.agregarMensajeError(USUARIO_PASS_INCORRECTO);
    }
    // ----------------- Getters y Setters -----------------------------
    /**
     * 
     * @return 
     */
    public String getError() {
            if(vistaHelper.recuperaRequest().getParameter("error") == null) {
            } else {
                int errorInt ;
                try {
                    errorInt = new Integer(vistaHelper.recuperaRequest().getParameter("error"));
                } catch (NumberFormatException e) {
                    vistaHelper.agregarMensajeError(ERROR_INVALIDO);
                    return error;
                }
                if (errorInt <= 0){
                    
                    vistaHelper.agregarMensajeError(USUARIO_PASS_INCORRECTO);
                    return error;
                }
                switch ( errorInt ){
                    case LGMConstantesExcepciones.ACCESO_USUARIO_BLOQUEADO :
                        if (!vistaHelper.recuperaRequest().getParameter("time").equalsIgnoreCase("-1"))
                        {
                            vistaHelper.agregarMensajeErrorSimple("El usuario esta bloqueado por "
                                    + vistaHelper.recuperaRequest().getParameter("time") + " minutos");
                        } else {
                            vistaHelper.agregarMensajeError(USUARIO_BLOQUEADO);
                        }
                        break;
                    case LGMConstantesExcepciones.ACCESO_USUARIO_REQUERIDO :
                        vistaHelper.agregarMensajeError(USUARIO_REQUERIDO);
                        break;
                    case LGMConstantesExcepciones.ACCESO_USUARIO_CHAR_INV :
                        vistaHelper.agregarMensajeError(USUARIO_CHAR_INV);
                        break;
                    case LGMConstantesExcepciones.ACCESO_PASS_CHAR_INV :
                        vistaHelper.agregarMensajeError(PASS_CHAR_INV);
                        break;
                    case LGMConstantesExcepciones.DAO_ERROR_CONEXION_BASE_DATOS :
                        vistaHelper.agregarMensajeError(ERROR_CONEXION_BASE_DATOS);
                        break;
                    
                    
                }
        }
            return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public IVistaHelper getVistaHelper() {
            return vistaHelper;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
            this.vistaHelper = vistaHelper;
    }
}