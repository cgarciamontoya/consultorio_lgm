/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.dao.basedatos;

import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.CatEnfermedadesCronicasDAO;
import java.util.List;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestCatEnfermedadesCronicasDAO extends TestDAOBaseDatosBase {
    @Autowired
    private CatEnfermedadesCronicasDAO dao;
    
    @Test
    public void testRecuperaPorFiltro() {
        CatEnfermedadesCronicas filtro = new CatEnfermedadesCronicas();
        filtro.setNombre("Diabetes");
        List<CatEnfermedadesCronicas> resultado = dao.recuperaPorFiltro(filtro);
        Assert.assertNotNull(resultado);
        Assert.assertFalse(resultado.isEmpty());
        Assert.assertEquals(1, resultado.size());
        
        filtro = new CatEnfermedadesCronicas();
        filtro.setNombre("Hipertension");
        resultado = dao.recuperaPorFiltro(filtro);
        Assert.assertNotNull(resultado);
        Assert.assertFalse(resultado.isEmpty());
        Assert.assertEquals(1, resultado.size());
    }
    
    @Test
    public void testGuardarDuplicado() throws Exception {
        CatEnfermedadesCronicas filtro = new CatEnfermedadesCronicas();
        filtro.setNombre("Hipertension");
        try {
            dao.guardar(filtro);
            Assert.fail("Fallo la prueba guardarDuplicado");
        } catch (ExcepcionDAO e) {
            Assert.assertEquals(LGMConstantesExcepciones.DAO_ENTIDAD_DUPLICADA, e.getNumeroError());
        }
    }
    
}
