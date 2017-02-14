/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.servicios.CatalogosServicio;
import consultorio.lgm.web.Constantes;
import consultorio.lgm.web.IVistaHelper;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "mpfMB")
@ViewScoped
public class MpfMB implements Serializable {
    private static final long serialVersionUID = 3296208999168334507L;
    
    private CatMpf filtro;
    private List<CatMpf> listaMpf;
    private List<CatMpf> listaMpfFiltrado;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    @ManagedProperty(value = "#{catalogosServicio}")
    private CatalogosServicio catalogosServicio;
    
    @PostConstruct
    public void init() {
        filtro = new CatMpf();
        buscar();
    }
    
    public void buscar() {
        listaMpf = null;
        try {
            listaMpf = catalogosServicio.consultarMpf(filtro);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_CONSULTA);
        }
    }
    
    public void guardar() {
        try {
            if (filtro == null
                    || filtro.getNombre() == null
                    || filtro.getNombre().isEmpty()) {
                vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_FILTROS_VACIOS);
                return;
            }
            catalogosServicio.guardarMpf(filtro);
            limpiar();
            listaMpf = catalogosServicio.consultarMpf(null);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_GUARDAR);
        }
    }
    
    public void eliminar() {
        if (filtro == null
                || filtro.getId() == null
                || filtro.getId() == 0) {
            vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_ELIMINAR_FILTROS_VACIOS);
            return;
        }
        try {
            catalogosServicio.eliminarEnfermedadesCronicas(filtro.getId());
            limpiar();
            listaMpf = catalogosServicio.consultarMpf(null);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_ELIMINAR);
        }
    }
    
    public void seleccionarCatalogo(SelectEvent e) {
        filtro = (CatMpf) e.getObject();
    }
    
    public void limpiar() {
        filtro = new CatMpf();
        listaMpf = null;
        listaMpfFiltrado = null;
    }

    public CatMpf getFiltro() {
        return filtro;
    }

    public void setFiltro(CatMpf filtro) {
        this.filtro = filtro;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }

    public void setCatalogosServicio(CatalogosServicio catalogosServicio) {
        this.catalogosServicio = catalogosServicio;
    }

    public List<CatMpf> getListaMpf() {
        return listaMpf;
    }

    public void setListaMpf(List<CatMpf> listaMpf) {
        this.listaMpf = listaMpf;
    }

    public List<CatMpf> getListaMpfFiltrado() {
        return listaMpfFiltrado;
    }

    public void setListaMpfFiltrado(List<CatMpf> listaMpfFiltrado) {
        this.listaMpfFiltrado = listaMpfFiltrado;
    }

    
    
}
