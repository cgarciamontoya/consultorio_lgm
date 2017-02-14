/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.integracion.dao.basedatos.CatEscolaridadDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;

/**
 *
 * @author Usuario
 */
public class CatEscolaridadDAOImpl extends DAOBDGenerico<CatEscolaridad, Integer>
            implements CatEscolaridadDAO{

    public CatEscolaridadDAOImpl() {
        super(CatEscolaridad.class);
    }
    
}
