/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.basedatos;

import consultorio.lgm.datos.entidades.Paciente;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface PacienteDAO extends IDAOBDGenerico<Paciente, Long> {
    
    List<Paciente> recuperaPorFiltro(String nombre, String paterno, String materno);
    boolean existePaciente(String nombre, String paterno, String materno, Date fechaNacimiento);
}
