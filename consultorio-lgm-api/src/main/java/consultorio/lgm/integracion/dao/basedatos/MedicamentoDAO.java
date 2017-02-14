/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.basedatos;

import consultorio.lgm.datos.entidades.Medicamento;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface MedicamentoDAO extends IDAOBDGenerico<Medicamento, Long> {
    
    List<Medicamento> recuperaPorFiltro(Medicamento filtro);
    List<Medicamento> recuperaPorNombre(String nombre);
    Medicamento guardarMedicamento(Medicamento medicamento);
}
