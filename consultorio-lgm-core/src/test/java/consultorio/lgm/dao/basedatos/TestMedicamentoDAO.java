/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.dao.basedatos;

import consultorio.lgm.datos.entidades.CatPresentacion;
import consultorio.lgm.datos.entidades.Medicamento;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.MedicamentoDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestMedicamentoDAO extends TestDAOBaseDatosBase {
    
    @Autowired
    private MedicamentoDAO dao;
    
    @Test
    public void test1Guardar() throws Exception {
        Medicamento med = new Medicamento();
        med.setAntibiotico(false);
        med.setPresentacion(new CatPresentacion(1));
        med.setNombre("Naproxeno Sodico");
        med = dao.guardarMedicamento(med);
        Assert.assertNotNull(med.getId());
        Assert.assertTrue(med.getId() > 0);
        
        try {
            med = new Medicamento();
            med.setAntibiotico(false);
            med.setPresentacion(new CatPresentacion(1));
            med.setNombre("Naproxeno Sodico");
            dao.guardarMedicamento(med);
            Assert.fail("Fallo la prueba guardarMedicamento");
        } catch (ExcepcionDAO e) {
            Assert.assertEquals(LGMConstantesExcepciones.DAO_ENTIDAD_DUPLICADA, e.getNumeroError());
        }
        
    }
    
    @Test
    public void test2ConsultarFiltro() {
        Medicamento med = new Medicamento();
        med.setAntibiotico(false);
        med.setPresentacion(new CatPresentacion(1));
        med.setNombre("Naproxeno");
        
        List<Medicamento> resultado = dao.recuperaPorFiltro(med);
        Assert.assertNotNull(resultado);
        Assert.assertFalse(resultado.isEmpty());
    }
    
}
