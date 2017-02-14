/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.dao.basedatos;

import consultorio.lgm.datos.entidades.AntecedentesGenerales;
import consultorio.lgm.datos.entidades.AntecedentesGinecologicos;
import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.Paciente;
import consultorio.lgm.integracion.dao.basedatos.PacienteDAO;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestPacienteDAO extends TestDAOBaseDatosBase {
    
    @Autowired
    private PacienteDAO pacienteDAO;
    
    @Test
    public void test1GuardarPaciente() throws Exception {
        Paciente paciente = pacienteDAO.guardar(buildPaciente("Carlos", "Garcia", "Montoya", false));
        Assert.assertNotNull(paciente);
        Assert.assertNotNull(paciente.getId());
        Assert.assertTrue(paciente.getId() > 0);
        
        paciente = pacienteDAO.guardar(buildPaciente("Sandra", "Silva", "Silva", true));
        Assert.assertNotNull(paciente);
        Assert.assertNotNull(paciente.getId());
        Assert.assertTrue(paciente.getId() > 0);
        
        paciente = pacienteDAO.guardar(buildPaciente("Sonia", "Garcia", "Silva", true));
        Assert.assertNotNull(paciente);
        Assert.assertNotNull(paciente.getId());
        Assert.assertTrue(paciente.getId() > 0);
    }
    
    @Test
    public void test2ConsultarFiltro() throws Exception {
        List<Paciente> pacientes = pacienteDAO.recuperaPorFiltro("Carlos", "Garcia", "");
        Assert.assertNotNull(pacientes);
        Assert.assertFalse(pacientes.isEmpty());
        Assert.assertEquals(1, pacientes.size());
        Assert.assertNotNull(pacientes.get(0).getIdAntecedentes());
        Assert.assertNotNull(pacientes.get(0).getIdAntecedentes().getId());
        
        pacientes = pacienteDAO.recuperaPorFiltro("Sonia", "Garcia", "Silva");
        Assert.assertNotNull(pacientes);
        Assert.assertFalse(pacientes.isEmpty());
        Assert.assertEquals(1, pacientes.size());
        
        pacientes = pacienteDAO.recuperaPorFiltro("", "", "Silva");
        Assert.assertNotNull(pacientes);
        Assert.assertFalse(pacientes.isEmpty());
        Assert.assertEquals(2, pacientes.size());
    }
    
    @Test
    public void test3ExistePaciente() throws Exception {
        boolean existe = pacienteDAO.existePaciente("Carlos", "Garcia", "Montoya", new Date());
        Assert.assertTrue(existe);
        
        existe = pacienteDAO.existePaciente("Sonia", "Garcia", "Silva", new Date());
        Assert.assertTrue(existe);
        
        existe = pacienteDAO.existePaciente("Laura", "Garcia", "Montoya", new Date());
        Assert.assertFalse(existe);
    }
    
    private Paciente buildPaciente(String nombre, String paterno, String materno, boolean femenino) {
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellidoPaterno(paterno);
        paciente.setApellidoMaterno(materno);
        paciente.setFechaNacimiento(new Date());
        paciente.setEdad(30);
        paciente.setGeneroFemenino(false);
        paciente.setDireccion("Cerro del Grillo 209");
        paciente.setTelefono("4921126205");
        paciente.setReligion("Catolica");
        paciente.setIdEscolaridad(new CatEscolaridad(1));
        paciente.setIdEstadoCivil(new CatEstadoCivil(1));
        AntecedentesGenerales ag = new AntecedentesGenerales();
        ag.setCargaGeneticaPaternal("Ninguna");
        ag.setCargaGeneticaMaternal("Ninguna");
        ag.setVivienda("Rentada");
        ag.setAlimentacion("Balanceada");
        ag.setHigiene("Todos los dias");
        ag.setToxicomanias("Ninguna");
        ag.setQuirurgicas("Hernia");
        ag.setHospitalizaciones(true);
        ag.setMotivoHospitalizacion("Hernia");
        ag.setIdGrupoSanguineo(new CatGrupoSanguineo(1));
        paciente.setIdAntecedentes(ag);
        if (femenino) {
            AntecedentesGinecologicos agi = new AntecedentesGinecologicos();
            agi.setMenarca(13);
            agi.setCicloMenstrualRegular(true);
            agi.setIvsa(25);
            agi.setNumeroParejas(1);
            agi.setIdCatMpf(new CatMpf(1));
            agi.setGesta(new Short("1"));
            agi.setParto(new Short("0"));
            agi.setCesarea(new Short("1"));
            paciente.setIdAntecedentesGinecologicos(agi);
        }
        return paciente;
    }
}
