/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.RelAntecedentesEnfermedades;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;
import consultorio.lgm.integracion.dao.basedatos.RelAntecedentesEnfermedadesDAO;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class RelAntecedentesEnfermedadesDAOImpl extends DAOBDGenerico<RelAntecedentesEnfermedades, Long>
            implements RelAntecedentesEnfermedadesDAO {

    public RelAntecedentesEnfermedadesDAOImpl() {
        super(RelAntecedentesEnfermedades.class);
    }
    
    public List<RelAntecedentesEnfermedades> recuperaRelacionesAntecedentes(Long idAntecedentes) {
        try {
            Query query = entityManager.createNamedQuery("RelAntecedentesEnfermedades.recuperaRelacionesPaciente");
            query.setParameter("idAntecedentes", idAntecedentes);
            return query.getResultList();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
}
