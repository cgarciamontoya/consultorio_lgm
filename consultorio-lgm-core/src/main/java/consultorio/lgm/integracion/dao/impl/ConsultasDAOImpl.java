/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.Consulta;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.ConsultasDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class ConsultasDAOImpl extends DAOBDGenerico<Consulta, Long>
        implements ConsultasDAO, Serializable {
    private static final long serialVersionUID = 6779427563215477032L;

    public ConsultasDAOImpl() {
        super(Consulta.class);
    }

    @Override
    public List<Consulta> recuperaPorFiltro(Long idPaciente, Date fecha) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("Select c.* From consulta c ");
            if (idPaciente != null && idPaciente > 0) {
                query.append("Where c.id_paciente = ")
                        .append(idPaciente);
            }
            if (fecha != null) {
                if (query.toString().contains("Where")) {
                    query.append(" And ");
                } else {
                    query.append("Where ");
                }
                query.append("c.fecha = '")
                        .append(new SimpleDateFormat("yyyy-MM-dd").format(fecha))
                        .append("'");
            }
            query.append(" Order by c.fecha Desc");
            Query qry = entityManager.createNativeQuery(query.toString(), Consulta.class);
            return qry.getResultList();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }

    @Override
    public Consulta recuperaUltimaConsulta(Long idPaciente) {
        try {
            if (idPaciente == null || idPaciente == 0) {
                throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_VALORES_NULL);
            }
            Query query = entityManager.createNamedQuery("Consultas.recuperaUltimaConsulta");
            query.setParameter("idPaciente", idPaciente);
            return (Consulta) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
    @Override
    public void guardarURLReceta(Long idConsulta, String ruta) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("update consulta set ruta_receta = '")
                    .append(ruta)
                    .append("' where id_consulta = ")
                    .append(idConsulta);
            Query qry = entityManager.createNativeQuery(query.toString());
            qry.executeUpdate();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
    @Override
    public Consulta recuperaNotaMedica(Long idPaciente) {
        try {
            if (idPaciente == null || idPaciente == 0) {
                throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_VALORES_NULL);
            }
            Query query = entityManager.createNamedQuery("Consultas.recuperarNotaMedica");
            query.setParameter("idPaciente", idPaciente);
            return (Consulta) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
}
