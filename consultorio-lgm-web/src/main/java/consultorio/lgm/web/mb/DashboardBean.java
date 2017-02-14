/**
 * Requerimientos cubiertos: RF-PantallaPrincipal.
 * Descripción: Este componente tiene la responsabilidad de generar el dashboard para el usuario.
 * Autor: capinedo
 * Fecha de creación: 26/03/2014
 */

package consultorio.lgm.web.mb;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
 * Clase para la inicialización del dashboard
 * @author capinedo
 * Fecha: 28/03/2014
 */

@ManagedBean(name = "dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {
    private final DashboardModel modelo;

    /**
     * Metodo que se encarga de configurar el dashboard.
     */
    public DashboardBean() {
        modelo = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        DashboardColumn column4 = new DefaultDashboardColumn();
        DashboardColumn column5 = new DefaultDashboardColumn();

        column1.addWidget("notificaciones");
        column2.addWidget("busquedas");
        column3.addWidget("administracion");
        column4.addWidget("configuracion");
        column5.addWidget("cambiarPassword");

        modelo.addColumn(column1);
        modelo.addColumn(column2);
        modelo.addColumn(column3);
        modelo.addColumn(column4);
        modelo.addColumn(column5);
    }

    /**
     * Devuelve el valor del atributo modelo.
     * @return Valor del atributo modelo.
     */
    public DashboardModel getModelo() {
        return modelo;
    }
}
