/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.basedatos;

import consultorio.lgm.datos.entidades.Configuracion;

/**
 *
 * @author Usuario
 */
public interface ConfiguracionDAO extends IDAOBDGenerico<Configuracion, Integer> {
    
    public Configuracion consultarConfiguracion(String nombreParametro);
}
