package consultorio.lgm.web.mb;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import consultorio.lgm.web.IVistaHelper;

import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@ManagedBean (name="dashBoardMB")
@SessionScoped
public class MBDashBoard implements Serializable {
	
	private static final long serialVersionUID = 7568291581316519027L;

	private DashboardModel modelo;
	
	private String nombreUsuario ;
	
	@ManagedProperty (value="#{vistaJSFHelper}")
	private IVistaHelper vistasHelper;
	
	public MBDashBoard (){
		 modelo = new DefaultDashboardModel();  
	     DashboardColumn column1 = new DefaultDashboardColumn();  
	     DashboardColumn column2 = new DefaultDashboardColumn();  
	     DashboardColumn column3 = new DefaultDashboardColumn();  
	     DashboardColumn column4 = new DefaultDashboardColumn();
	          
	     column1.addWidget("usuarios");  
	     column1.addWidget("perfiles");  
	     modelo.addColumn(column1); 
	     
	     column2.addWidget("consultaConciliacion");  
	     column2.addWidget("conciliacionManual");  
	     modelo.addColumn(column2);  
	     
	       
	     column3.addWidget("confirmacionConciliacion");  
	     column3.addWidget("inconsistencias");
	     modelo.addColumn(column3);  
	     
	     column4.addWidget("bitacora");  
	     column4.addWidget("configuracion");
	     modelo.addColumn( column4 );
	}
  
      
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
      
    public DashboardModel getModelo() {  
        return modelo;  
    }

	public String getNombreUsuario() {
		if ( nombreUsuario == null ){
			if ( vistasHelper.recuperaRequest() != null )
				nombreUsuario = (String) vistasHelper.recuperaRequest().getSession().getAttribute ("usuario") ;
		}
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public IVistaHelper getVistasHelper() {
		return vistasHelper;
	}

	public void setVistasHelper(IVistaHelper vistasHelper) {
		this.vistasHelper = vistasHelper;
	}

}
