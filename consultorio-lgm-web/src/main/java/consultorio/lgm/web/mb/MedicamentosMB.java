/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.mb;

import consultorio.lgm.datos.entidades.CatPresentacion;
import consultorio.lgm.datos.entidades.Medicamento;
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
@ManagedBean(name = "medicamentosMB")
@ViewScoped
public class MedicamentosMB implements Serializable {
    private static final long serialVersionUID = 6312428137182605743L;
    
    private Medicamento filtro;
    private Integer idPresentacion;
    private List<Medicamento> listaMedicamentos;
    private List<Medicamento> listaMedicamentosFiltrado;
    private List<CatPresentacion> listaPresentaciones;
    @ManagedProperty(value = "#{vistaJSFHelper}")
    private IVistaHelper vistaHelper;
    @ManagedProperty(value = "#{catalogosServicio}")
    private CatalogosServicio catalogosServicio;
    
    @PostConstruct
    public void init() {
        buscar();
        filtro = new Medicamento();
        filtro.setPresentacion(new CatPresentacion());
        listaPresentaciones = catalogosServicio.consultarPresentacion();
    }
    
    public void buscar() {
        listaMedicamentos = null;
        try {
            listaMedicamentos = catalogosServicio.consultarMedicamento(filtro);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_CONSULTA);
        }
    }
    
    public void guardar() {
        try {
            if (filtro == null
                    || filtro.getNombre() == null
                    || filtro.getNombre().isEmpty()
                    || filtro.getPresentacion() == null
                    || filtro.getPresentacion().getId() == null
                    || filtro.getPresentacion().getId() == 0) {
                vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_FILTROS_VACIOS);
                return;
            }
            catalogosServicio.guardarMedicamento(filtro);
            limpiar();
            listaMedicamentos = catalogosServicio.consultarMedicamento(null);
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
            catalogosServicio.eliminarMedicamento(filtro.getId());
            limpiar();
            listaMedicamentos = catalogosServicio.consultarMedicamento(null);
        } catch (ExcepcionServicio e) {
            vistaHelper.agregarMensajeError(Constantes.ERROR_CATALOGOS_ELIMINAR);
        }
    }
    
    public void seleccionarMedicamento(SelectEvent e) {
        filtro = (Medicamento) e.getObject();
        idPresentacion = filtro.getPresentacion().getId();
    }
    
    public void limpiar() {
        filtro = new Medicamento();
        filtro.setPresentacion(new CatPresentacion());
        listaMedicamentos = null;
        listaMedicamentosFiltrado = null;
        idPresentacion = null;
    }

    public Medicamento getFiltro() {
        return filtro;
    }

    public void setFiltro(Medicamento filtro) {
        this.filtro = filtro;
    }

    public Integer getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public List<Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void setListaMedicamentos(List<Medicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    public List<Medicamento> getListaMedicamentosFiltrado() {
        return listaMedicamentosFiltrado;
    }

    public void setListaMedicamentosFiltrado(List<Medicamento> listaMedicamentosFiltrado) {
        this.listaMedicamentosFiltrado = listaMedicamentosFiltrado;
    }

    public List<CatPresentacion> getListaPresentaciones() {
        return listaPresentaciones;
    }

    public void setListaPresentaciones(List<CatPresentacion> listaPresentaciones) {
        this.listaPresentaciones = listaPresentaciones;
    }

    public void setVistaHelper(IVistaHelper vistaHelper) {
        this.vistaHelper = vistaHelper;
    }

    public void setCatalogosServicio(CatalogosServicio catalogosServicio) {
        this.catalogosServicio = catalogosServicio;
    }
}
