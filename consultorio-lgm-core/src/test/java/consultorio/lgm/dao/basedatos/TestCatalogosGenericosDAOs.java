/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.dao.basedatos;

import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatPresentacion;
import consultorio.lgm.integracion.dao.basedatos.CatEscolaridadDAO;
import consultorio.lgm.integracion.dao.basedatos.CatEstadoCivilDAO;
import consultorio.lgm.integracion.dao.basedatos.CatGrupoSanguineoDAO;
import consultorio.lgm.integracion.dao.basedatos.CatPresentacionDAO;
import java.util.List;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestCatalogosGenericosDAOs extends TestDAOBaseDatosBase {
    
    @Autowired
    private CatEscolaridadDAO catEscolaridadDAO;
    @Autowired
    private CatEstadoCivilDAO catEstadoCivilDAO;
    @Autowired
    private CatGrupoSanguineoDAO catGrupoSanguineoDAO;
    @Autowired
    private CatPresentacionDAO catPresentacionDAO;
    
    @Test
    public void testCatEscolaridad() throws Exception {
        List<CatEscolaridad> resultado = catEscolaridadDAO.recuperaTodo();
        Assert.assertNotNull(resultado);
        Assert.assertEquals(6, resultado.size());
    }
    
    @Test
    public void testCatEstadoCivil() throws Exception {
        List<CatEstadoCivil> resultado = catEstadoCivilDAO.recuperaTodo();
        Assert.assertNotNull(resultado);
        Assert.assertEquals(4, resultado.size());
    }
    
    @Test
    public void testCatGrupoSanguineo() throws Exception {
        List<CatGrupoSanguineo> resultado = catGrupoSanguineoDAO.recuperaTodo();
        Assert.assertNotNull(resultado);
        Assert.assertEquals(4, resultado.size());
    }
    
    @Test
    public void testCatPresentacion() throws Exception {
        List<CatPresentacion> resultado = catPresentacionDAO.recuperaTodo();
        Assert.assertNotNull(resultado);
        Assert.assertEquals(4, resultado.size());
    }
}
