/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.integracion.dao.basedatos.CatGrupoSanguineoDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;

/**
 *
 * @author Usuario
 */
public class CatGrupoSanguineoDAOImpl extends DAOBDGenerico<CatGrupoSanguineo, Integer>
            implements CatGrupoSanguineoDAO {

    public CatGrupoSanguineoDAOImpl() {
        super(CatGrupoSanguineo.class);
    }
}
