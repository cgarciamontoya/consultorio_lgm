/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.Configuracion;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.integracion.dao.basedatos.ConfiguracionDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class ConfiguracionDAOImpl extends DAOBDGenerico<Configuracion, Integer> implements ConfiguracionDAO {

    public ConfiguracionDAOImpl() {
        super(Configuracion.class);
    }

    
    @Override
    public Configuracion consultarConfiguracion(String nombreParametro) {
        Query qry = entityManager.createNamedQuery("Configuracion.consultarParametro");
        qry.setParameter("parametro", nombreParametro);
        try {
            return (Configuracion) qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
}
