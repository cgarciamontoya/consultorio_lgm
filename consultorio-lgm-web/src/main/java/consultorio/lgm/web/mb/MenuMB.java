/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "menuMB")
@ViewScoped
public class MenuMB implements Serializable {
    private static final long serialVersionUID = -9118691827784960504L;
    
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    
    @PostConstruct
    public void init() {
        limpiarPacienteSesion();
    }
    
    public void limpiarPacienteSesion() {
        vistaHelper.recuperaSesion().removeAttribute(Constantes.ID_PACIENTE);
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }
    
    
}
