/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.Paciente;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;
import consultorio.lgm.integracion.dao.basedatos.PacienteDAO;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class PacienteDAOImpl extends DAOBDGenerico<Paciente, Long>
            implements PacienteDAO {

    public PacienteDAOImpl() {
        super(Paciente.class);
    }

    @Override
    public List<Paciente> recuperaPorFiltro(String nombre, String paterno, String materno) {
        try {
            Query query = entityManager.createNamedQuery("Paciente.recuperaPorFiltro");
            query.setParameter("nombre", nombre != null && !nombre.isEmpty() ? "%" + nombre + "%" : "");
            query.setParameter("paterno", paterno != null && !paterno.isEmpty() ? "%" + paterno + "%" : "");
            query.setParameter("materno", materno != null && !materno.isEmpty() ? "%" + materno + "%" : "");
            return query.getResultList();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }

    @Override
    public boolean existePaciente(String nombre, String paterno, String materno, Date fechaNacimiento) {
        try {
            Query query = entityManager.createNamedQuery("Paciente.existePaciente");
            query.setParameter("nombre", nombre);
            query.setParameter("paterno", paterno);
            query.setParameter("materno", materno);
            query.setParameter("fechaNacimiento", fechaNacimiento);
            return (Boolean) query.getSingleResult();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
}
