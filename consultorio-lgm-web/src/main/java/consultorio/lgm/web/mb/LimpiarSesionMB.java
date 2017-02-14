/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "limpiarSesionMB")
@ViewScoped
public class LimpiarSesionMB implements Serializable {
    private static final long serialVersionUID = 3285823675309433568L;
    
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    
    public void init() {
        vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_PACIENTE);
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }
    
    
}
