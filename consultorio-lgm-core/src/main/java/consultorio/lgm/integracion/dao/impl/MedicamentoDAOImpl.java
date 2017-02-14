/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.Medicamento;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;
import consultorio.lgm.integracion.dao.basedatos.MedicamentoDAO;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class MedicamentoDAOImpl extends DAOBDGenerico<Medicamento, Long>
        implements MedicamentoDAO {

    public MedicamentoDAOImpl() {
        super(Medicamento.class);
    }

    @Override
    public List<Medicamento> recuperaPorFiltro(Medicamento filtro) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("Select m.* from medicamento m ");
            if (filtro.getPresentacion() != null && filtro.getPresentacion().getId() != null
                    && filtro.getPresentacion().getId() > 0) {
                query.append("Inner Join cat_presentacion cp On m.presentacion = cp.id ")
                        .append("Where cp.id = ")
                        .append(filtro.getPresentacion().getId());
            }
            if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
                if (!query.toString().contains("Where")) {
                    query.append("Where ");
                } else {
                    query.append("and ");
                }
                query.append("LOWER(m.nombre) Like '%")
                        .append(filtro.getNombre().toLowerCase())
                        .append("%' ");
            }
            query.append("and m.antibiotico = ")
                    .append(filtro.getAntibiotico());
            Query qry = entityManager.createNativeQuery(query.toString(), Medicamento.class);
            return qry.getResultList();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
    @Override
    public List<Medicamento> recuperaPorNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return recuperaTodo();
        }
        try {
            Query query = entityManager.createNamedQuery("Medicamento.recuperaPorNombreComplete");
            query.setParameter("nombre", "%" + nombre + "%");
            return query.getResultList();
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }

    @Override
    public Medicamento guardarMedicamento(Medicamento medicamento) {
        boolean entidadDuplicada = false;
        if (medicamento == null || medicamento.getNombre() == null
                || medicamento.getNombre().isEmpty()
                || medicamento.getPresentacion() == null
                || medicamento.getPresentacion().getId() == null
                || medicamento.getPresentacion().getId() == 0) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_VALORES_NULL);
        }
        try {
            Query query = entityManager.createNamedQuery("Medicamento.recuperaPorNombre");
            query.setParameter("nombre", medicamento.getNombre());
            query.setParameter("antibiotico", medicamento.getAntibiotico());
            query.setParameter("idPresentacion", medicamento.getPresentacion().getId());
            List<Medicamento> lista = query.getResultList();
            if (lista == null || lista.isEmpty()) {
                medicamento = guardar(medicamento);
            } else {
                entidadDuplicada = true;
            }
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
        if (entidadDuplicada) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_ENTIDAD_DUPLICADA);
        }
        return medicamento;
    }
    
}
