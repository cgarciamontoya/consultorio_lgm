/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios;

import consultorio.lgm.datos.dto.ConsultaDTO;
import consultorio.lgm.datos.entidades.AntecedentesGenerales;
import consultorio.lgm.datos.entidades.AntecedentesGinecologicos;
import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.Consulta;
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
public class TestConsultasServicio extends TestServicioBase {
    
    @Autowired
    private ConsultasServicio consultasServicio;
    @Autowired
    private PacienteServicio pacienteServicio;
    
    @Test
    public void test1Consultar() throws Exception {
        Paciente paciente1 = pacienteServicio.guardarPaciente(buildPaciente("Carlos", "Garcia", "Montoya", false));
        Assert.assertNotNull(paciente1);
        Assert.assertNotNull(paciente1.getId());
        Assert.assertTrue(paciente1.getId() > 0);
        
        Consulta filtro = new Consulta();
        filtro.setDiagnostico("Gripe");
        filtro.setExploracionFisica("Ninguna");
        filtro.setFecha(new Date());
        filtro.setIdPaciente(paciente1);
        filtro.setMotivoConsulta("Dolor muscular");
        
        consultasServicio.guardarConsulta(filtro);
        
        List<ConsultaDTO> consultas = consultasServicio.recuperaPorFiltro(1L, null);
        Assert.assertNotNull(consultas);
        Assert.assertFalse(consultas.isEmpty());
        Assert.assertNotNull(consultas.get(0).getNombrePaciente());
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
            AntecedentesGinecologicos agi = new AntecedentesGinecologicos();
            agi.setIdCatMpf(new CatMpf());
            paciente.setIdAntecedentesGinecologicos(agi);
        return paciente;
    }
}
