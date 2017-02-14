/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
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
@ManagedBean(name = "enfermedadesCronicasMB")
@ViewScoped
public class EnfermedadesCronicasMB implements Serializable {
    private static final long serialVersionUID = 8386600293812728881L;
    
    private CatEnfermedadesCronicas filtro;
    private List<CatEnfermedadesCronicas> listaEnfermedades;
    private List<CatEnfermedadesCronicas> listaEnfermedadesFiltrado;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    @ManagedProperty(value = "#{catalogosServicio}")
    private CatalogosServicio catalogosServicio;
    
    @PostConstruct
    public void init() {
        filtro = new CatEnfermedadesCronicas();
        buscar();
    }
    
    public void buscar() {
        listaEnfermedades = null;
        try {
            listaEnfermedades = catalogosServicio.consultarEnfermedadesCronicas(filtro);
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
            catalogosServicio.guardarEnfermedadesCronicas(filtro);
            limpiar();
            listaEnfermedades = catalogosServicio.consultarEnfermedadesCronicas(null);
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
            listaEnfermedades = catalogosServicio.consultarEnfermedadesCronicas(null);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_ELIMINAR);
        }
    }
    
    public void seleccionarCatalogo(SelectEvent e) {
        filtro = (CatEnfermedadesCronicas) e.getObject();
    }
    
    public void limpiar() {
        filtro = new CatEnfermedadesCronicas();
        listaEnfermedades = null;
        listaEnfermedadesFiltrado = null;
    }

    public CatEnfermedadesCronicas getFiltro() {
        return filtro;
    }

    public void setFiltro(CatEnfermedadesCronicas filtro) {
        this.filtro = filtro;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }

    public void setCatalogosServicio(CatalogosServicio catalogosServicio) {
        this.catalogosServicio = catalogosServicio;
    }

    public List<CatEnfermedadesCronicas> getListaEnfermedades() {
        return listaEnfermedades;
    }

    public void setListaEnfermedades(List<CatEnfermedadesCronicas> listaEnfermedades) {
        this.listaEnfermedades = listaEnfermedades;
    }

    public List<CatEnfermedadesCronicas> getListaEnfermedadesFiltrado() {
        return listaEnfermedadesFiltrado;
    }

    public void setListaEnfermedadesFiltrado(List<CatEnfermedadesCronicas> listaEnfermedadesFiltrado) {
        this.listaEnfermedadesFiltrado = listaEnfermedadesFiltrado;
    }
    
    
}
