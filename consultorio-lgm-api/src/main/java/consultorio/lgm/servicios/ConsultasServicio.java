/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios;

import consultorio.lgm.datos.dto.ConsultaDTO;
import consultorio.lgm.datos.dto.NotaMedicaDTO;
import consultorio.lgm.datos.dto.RecetaDTO;
import consultorio.lgm.datos.entidades.Consulta;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public interface ConsultasServicio {
    
    List<ConsultaDTO> recuperaPorFiltro(Long idPaciente, Date fecha);
    Consulta recuperaUltimaConsulta(Long idPaciente);
    Consulta guardarConsulta(Consulta filtro);
    Consulta recuperaPorId(Long idConsulta);
    RecetaDTO generarReceta(Consulta consulta);
    NotaMedicaDTO generarNotaMedica(Map<String, Object> parametros);
}
