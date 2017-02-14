/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.integracion.dao.impl;

import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.CatEnfermedadesCronicasDAO;
import consultorio.lgm.integracion.dao.basedatos.DAOBDGenerico;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class CatEnfermedadesCronicasDAOImpl extends DAOBDGenerico<CatEnfermedadesCronicas, Integer>
        implements CatEnfermedadesCronicasDAO {

    public CatEnfermedadesCronicasDAOImpl() {
        super(CatEnfermedadesCronicas.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CatEnfermedadesCronicas> recuperaPorFiltro(CatEnfermedadesCronicas filtro) {
        if (filtro == null
                || filtro.getNombre() == null
                || filtro.getNombre().isEmpty()) {
            return recuperaTodo();
        }
        Query query = entityManager.createNamedQuery("CatEnfermedadesCronicas.recuperaPorFiltro");
        query.setParameter("nombre", "%" + filtro.getNombre() + "%");
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
    }
    
    public CatEnfermedadesCronicas guardar(CatEnfermedadesCronicas filtro) {
        boolean entidadDuplicada = false;
        if (filtro == null) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_VALORES_NULL);
        }
        try {
            if (filtro.getId() == null || filtro.getId().intValue() == 0) {
                Query query = entityManager.createNamedQuery("CatEnfermedadesCronicas.recuperaPorNombre");
                query.setParameter("nombre", filtro.getNombre());

                    List<CatEnfermedadesCronicas> resultado = query.getResultList();
                    if (resultado == null || resultado.isEmpty()) {
                        filtro = super.guardar(filtro);
                    } else {
                        entidadDuplicada = true;
                    }

            } else {
                super.actualizar(filtro);
            }
        } catch (RuntimeException e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, e);
        }
        if (entidadDuplicada) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_ENTIDAD_DUPLICADA);
        }
        return filtro;
    }
    
}
