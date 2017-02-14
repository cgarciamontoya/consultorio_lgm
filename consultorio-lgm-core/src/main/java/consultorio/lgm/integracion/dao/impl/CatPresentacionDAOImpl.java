/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.CatPresentacion;
import consultorio.lgm.integracion.dao.basedatos.CatPresentacionDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;

/**
 *
 * @author Usuario
 */
public class CatPresentacionDAOImpl extends DAOBDGenerico<CatPresentacion, Integer>
        implements CatPresentacionDAO {

    public CatPresentacionDAOImpl() {
        super(CatPresentacion.class);
    }
    
}
