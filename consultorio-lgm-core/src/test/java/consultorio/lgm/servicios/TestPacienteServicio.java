/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios;

import consultorio.lgm.datos.dto.PacienteDTO;
import consultorio.lgm.datos.entidades.AntecedentesGenerales;
import consultorio.lgm.datos.entidades.AntecedentesGinecologicos;
import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.Paciente;
import java.util.Date;
import java.util.List;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestPacienteServicio extends TestServicioBase {
    
    @Autowired
    private PacienteServicio pacienteServicio;
    private Paciente paciente1;
    private Paciente paciente2;
    
    @Test
    public void test1Guardar() throws Exception {
        paciente1 = pacienteServicio.guardarPaciente(buildPaciente("Carlos", "Garcia", "Montoya", false));
        Assert.assertNotNull(paciente1);
        Assert.assertNotNull(paciente1.getId());
        Assert.assertTrue(paciente1.getId() > 0);
        
        paciente2 = pacienteServicio.guardarPaciente(buildPaciente("Sonia", "Garcia", "Silva", true));
        Assert.assertNotNull(paciente2);
        Assert.assertNotNull(paciente2.getId());
        Assert.assertTrue(paciente2.getId() > 0);
    }
    @Test
    public void test2RecuperaPorId() throws Exception {
        Paciente paciente = pacienteServicio.consultarPacienteId(paciente1.getId());
        Assert.assertNotNull(paciente);
        Assert.assertEquals(paciente1.getId(), paciente.getId());
        Assert.assertEquals(paciente1.getNombre(), paciente.getNombre());
        Assert.assertEquals(paciente1.getApellidoPaterno(), paciente.getApellidoPaterno());
        Assert.assertEquals(paciente1.getApellidoMaterno(), paciente.getApellidoMaterno());
    }
    @Test
    public void test3ConsultarPorFiltro() throws Exception {
        List<PacienteDTO> pacientes = pacienteServicio.consultarPacientes("", "Garcia", "");
        Assert.assertNotNull(pacientes);
        Assert.assertFalse(pacientes.isEmpty());
        Assert.assertEquals(2, pacientes.size());
    }
    @Test
    public void test4ExistePaciente() throws Exception {
        boolean resultado = pacienteServicio.existePaciente("Carlos", "Garcia", "Montoya", new Date());
        Assert.assertTrue(resultado);
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
