/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios.impl;

import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.CatPresentacion;
import consultorio.lgm.datos.entidades.Medicamento;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.ExcepcionServicio;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.integracion.dao.basedatos.CatEnfermedadesCronicasDAO;
import consultorio.lgm.integracion.dao.basedatos.CatEscolaridadDAO;
import consultorio.lgm.integracion.dao.basedatos.CatEstadoCivilDAO;
import consultorio.lgm.integracion.dao.basedatos.CatGrupoSanguineoDAO;
import consultorio.lgm.integracion.dao.basedatos.CatMpfDAO;
import consultorio.lgm.integracion.dao.basedatos.CatPresentacionDAO;
import consultorio.lgm.integracion.dao.basedatos.MedicamentoDAO;
import consultorio.lgm.servicios.CatalogosServicio;
import consultorio.lgm.servicios.ServicioBase;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Usuario
 */
public class CatalogosServicioImpl extends ServicioBase implements CatalogosServicio, Serializable {
    private static final long serialVersionUID = -4869139572617479605L;
    
    @Autowired
    private CatEnfermedadesCronicasDAO catEnfermedadesCronicasDAO;
    @Autowired
    private CatEscolaridadDAO catEscolaridadDAO;
    @Autowired
    private CatEstadoCivilDAO catEstadoCivilDAO;
    @Autowired
    private CatGrupoSanguineoDAO catGrupoSanguineoDAO;
    @Autowired
    private CatMpfDAO catMpfDAO;
    @Autowired
    private CatPresentacionDAO catPresentacionDAO;
    @Autowired
    private MedicamentoDAO medicamentoDAO;

    @Override
    public List<CatEnfermedadesCronicas> consultarEnfermedadesCronicas(CatEnfermedadesCronicas filtro) {
        try {
            return catEnfermedadesCronicasDAO.recuperaPorFiltro(filtro);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public CatEnfermedadesCronicas guardarEnfermedadesCronicas(CatEnfermedadesCronicas filtro) {
        try {
            return catEnfermedadesCronicasDAO.guardar(filtro);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_CATALOGOS_GUARDAR);
        }
    }

    @Override
    public void eliminarEnfermedadesCronicas(Integer id) {
        try {
            if (id == null || id == 0) {
                throw fabricaExcepciones.crear(ExcepcionDAO.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
            }
            catEnfermedadesCronicasDAO.eliminar(id);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionDAO.class,
                    LGMConstantesExcepciones.SERVICIO_CATALOGOS_ELIMINAR);
        }
    }

    @Override
    public List<CatEscolaridad> consultarEscolaridad() {
        try {
            return catEscolaridadDAO.recuperaTodo();
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public List<CatEstadoCivil> consultarEstadoCivil() {
        try {
            return catEstadoCivilDAO.recuperaTodo();
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public List<CatGrupoSanguineo> consultarGrupoSanguineo() {
        try {
            return catGrupoSanguineoDAO.recuperaTodo();
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public List<CatMpf> consultarMpf(CatMpf filtro) {
        try {
            return catMpfDAO.recuperaPorFiltro(filtro);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public CatMpf guardarMpf(CatMpf filtro) {
        try {
            if (filtro == null
                    || filtro.getNombre() == null
                    || filtro.getNombre().isEmpty()) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class,
                        LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
            }
            return catMpfDAO.guardar(filtro);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_CATALOGOS_GUARDAR);
        }
    }

    @Override
    public void eliminarMpf(Integer id) {
        try {
            if (id == null || id == 0) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class, LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
            }
            catMpfDAO.eliminar(id);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_CATALOGOS_ELIMINAR);
        }
    }

    @Override
    public List<CatPresentacion> consultarPresentacion() {
        try {
            return catPresentacionDAO.recuperaTodo();
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }

    @Override
    public List<Medicamento> consultarMedicamento(Medicamento filtro) {
        try {
            if (validacionFiltro(filtro) == 0) {
                return medicamentoDAO.recuperaTodo();
            }
            return medicamentoDAO.recuperaPorFiltro(filtro);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }
    
    @Override
    public List<Medicamento> consultarMedicamentoComplete(String nombre) {
        try {
            return medicamentoDAO.recuperaPorNombre(nombre);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_COMUNICACION_BD);
        }
    }
    
    private int validacionFiltro(Medicamento filtro) {
        int invalidos = 0;
        if (filtro == null) {
            return invalidos;
        }
        if (filtro.getAntibiotico()) {
            invalidos++;
        }
        if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
            invalidos++;
        }
        if (filtro.getPresentacion() != null && filtro.getPresentacion().getId() != null
                && filtro.getPresentacion().getId() > 0) {
            invalidos++;
        }
        return invalidos;
    }

    @Override
    public Medicamento guardarMedicamento(Medicamento filtro) {
        try {
            if (filtro == null
                    || filtro.getNombre() == null
                    || filtro.getNombre().isEmpty()
                    || filtro.getPresentacion() == null
                    || filtro.getPresentacion().getId() == null
                    || filtro.getPresentacion().getId() == 0) {
                throw fabricaExcepciones.crear(ExcepcionServicio.class,
                        LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
            }
            return medicamentoDAO.guardarMedicamento(filtro);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_CATALOGOS_GUARDAR);
        }
    }

    @Override
    public void eliminarMedicamento(Long id) {
        try {
            medicamentoDAO.eliminar(id);
        } catch (ExcepcionDAO e) {
            throw fabricaExcepciones.crear(ExcepcionServicio.class,
                    LGMConstantesExcepciones.SERVICIO_CATALOGOS_ELIMINAR);
        }
    }
    
}
