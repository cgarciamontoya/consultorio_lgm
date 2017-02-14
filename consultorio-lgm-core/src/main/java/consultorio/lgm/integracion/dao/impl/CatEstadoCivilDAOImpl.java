/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.integracion.dao.basedatos.CatEstadoCivilDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;

/**
 *
 * @author Usuario
 */
public class CatEstadoCivilDAOImpl extends DAOBDGenerico<CatEstadoCivil, Integer>
        implements CatEstadoCivilDAO {

    public CatEstadoCivilDAOImpl() {
        super(CatEstadoCivil.class);
    }
}
