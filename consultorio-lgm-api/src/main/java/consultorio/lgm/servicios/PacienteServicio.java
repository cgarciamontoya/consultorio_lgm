/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios;

import consultorio.lgm.datos.dto.PacienteDTO;
import consultorio.lgm.datos.entidades.Consulta;
import consultorio.lgm.datos.entidades.Paciente;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface PacienteServicio {
    
    List<PacienteDTO> consultarPacientes(String nombre, String paterno, String materno);
    boolean existePaciente(String nombre, String paterno, String materno, Date fechaNacimiento);
    Paciente consultarPacienteId(Long id);
    Paciente guardarPaciente(Paciente paciente);
    Paciente guardarPaciente(Paciente paciente, List<String> idsEnfCronicas);
    PacienteDTO consultarPacienteDTOId(Long id);
    Consulta obtenerNotaMedica(Long id);

    ByteArrayInputStream exportarHistoriaClinica(Long id);
}
