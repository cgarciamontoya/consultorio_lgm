/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.basedatos;

import consultorio.lgm.datos.entidades.Consulta;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface ConsultasDAO extends IDAOBDGenerico<Consulta, Long> {
    
    List<Consulta> recuperaPorFiltro(Long idPaciente, Date fecha);
    Consulta recuperaUltimaConsulta(Long idPaciente);
    void guardarURLReceta(Long idConsulta, String ruta);
    Consulta recuperaNotaMedica(Long id);
}
