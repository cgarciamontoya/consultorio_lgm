/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.dao.basedatos;

import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.CatMpfDAO;
import java.util.List;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestCatMpfDAO extends TestDAOBaseDatosBase {
    
    @Autowired
    private CatMpfDAO dao;
    
    @Test
    public void test1RecuperaPorFiltro() throws Exception {
        List<CatMpf> resultado = dao.recuperaPorFiltro(null);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(8, resultado.size());
        
        CatMpf filtro = new CatMpf();
        filtro.setNombre("Hormonal");
        resultado = dao.recuperaPorFiltro(filtro);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(2, resultado.size());
    }
    
    @Test
    public void test2GuardarDuplicado() throws Exception {
        try {
            CatMpf filtro = new CatMpf();
            filtro.setNombre("Hormonal Oral");
            dao.guardar(filtro);
            Assert.fail("Fallo la prueba guardarDuplicado");
        } catch (ExcepcionDAO e) {
            Assert.assertEquals(LGMConstantesExcepciones.DAO_ENTIDAD_DUPLICADA, e.getNumeroError());
        }
    }
}
