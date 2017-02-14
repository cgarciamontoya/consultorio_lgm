/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.convertidores;

import consultorio.lgm.datos.entidades.Medicamento;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.servicios.CatalogosServicio;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Usuario
 */
@FacesConverter("medicamentoConverter")
public class MedicamentoConverter implements Converter{

    private CatalogosServicio catalogosServicio;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            catalogosServicio = (CatalogosServicio) FacesContextUtils.getWebApplicationContext(fc).
                    getBean("catalogosServicio");
            List<Medicamento> lista = catalogosServicio.consultarMedicamentoComplete(string);
            if (lista == null || lista.isEmpty()) {
                return null;
            }
            return lista.get(0);
        } catch (ExcepcionServicio e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            return ((Medicamento) o).getNombre();
        }
        return null;
    }
    
}
