/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios;

import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.CatPresentacion;
import consultorio.lgm.datos.entidades.Medicamento;
import java.util.List;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestCatalogosServicio extends TestServicioBase {
    
    @Autowired
    private CatalogosServicio catalogosServicio;

    @Test
    public void testConsultarEnfermedadesCroncias() throws Exception {
        List<CatEnfermedadesCronicas> catalogo = catalogosServicio.consultarEnfermedadesCronicas(null);
        Assert.assertNotNull(catalogo);
        Assert.assertFalse(catalogo.isEmpty());
    }

    @Test
    public void testConsultarEscolaridad() throws Exception {
        List<CatEscolaridad> catalogo = catalogosServicio.consultarEscolaridad();
        Assert.assertNotNull(catalogo);
        Assert.assertFalse(catalogo.isEmpty());
    }

    @Test
    public void testConsultarEstadoCivil() throws Exception {
        List<CatEstadoCivil> catalogo = catalogosServicio.consultarEstadoCivil();
        Assert.assertNotNull(catalogo);
        Assert.assertFalse(catalogo.isEmpty());
    }

    @Test
    public void testConsultarGrupoSanguineo() throws Exception {
        List<CatGrupoSanguineo> catalogo = catalogosServicio.consultarGrupoSanguineo();
        Assert.assertNotNull(catalogo);
        Assert.assertFalse(catalogo.isEmpty());
    }

    @Test
    public void testConsultarMpf() throws Exception {
        List<CatMpf> catalogo = catalogosServicio.consultarMpf(null);
        Assert.assertNotNull(catalogo);
        Assert.assertFalse(catalogo.isEmpty());
    }

    @Test
    public void testConsultarPresentacion() throws Exception {
        List<CatPresentacion> catalogo = catalogosServicio.consultarPresentacion();
        Assert.assertNotNull(catalogo);
        Assert.assertFalse(catalogo.isEmpty());
    }

    @Test
    public void testConsultarMedicamento() throws Exception {
        Medicamento med = new Medicamento();
        med.setNombre("Naproxeno");
        med.setAntibiotico(false);
        med.setPresentacion(new CatPresentacion(1));
        catalogosServicio.guardarMedicamento(med);
        
        List<Medicamento> catalogo = catalogosServicio.consultarMedicamento(med);
        Assert.assertNotNull(catalogo);
        Assert.assertFalse(catalogo.isEmpty());
    }

}
