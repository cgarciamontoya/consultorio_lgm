/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.basedatos;

import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface CatEnfermedadesCronicasDAO extends IDAOBDGenerico<CatEnfermedadesCronicas, Integer> {
    
    List<CatEnfermedadesCronicas> recuperaPorFiltro(CatEnfermedadesCronicas filtro);
    CatEnfermedadesCronicas guardar(CatEnfermedadesCronicas filtro);
}
