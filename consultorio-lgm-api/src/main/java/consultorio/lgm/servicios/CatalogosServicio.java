/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.servicios;

import consultorio.lgm.datos.entidades.CatEnfermedadesCronicas;
import consultorio.lgm.datos.entidades.CatEscolaridad;
import consultorio.lgm.datos.entidades.CatEstadoCivil;
import consultorio.lgm.datos.entidades.CatGrupoSanguineo;
import consultorio.lgm.datos.entidades.CatMpf;
import consultorio.lgm.datos.entidades.CatPresentacion;
import consultorio.lgm.datos.entidades.Medicamento;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface CatalogosServicio {
    
    List<CatEnfermedadesCronicas> consultarEnfermedadesCronicas(CatEnfermedadesCronicas filtro);
    CatEnfermedadesCronicas guardarEnfermedadesCronicas(CatEnfermedadesCronicas filtro);
    void eliminarEnfermedadesCronicas(Integer id);
    
    List<CatEscolaridad> consultarEscolaridad();
    List<CatEstadoCivil> consultarEstadoCivil();
    List<CatGrupoSanguineo> consultarGrupoSanguineo();
    
    List<CatMpf> consultarMpf(CatMpf filtro);
    CatMpf guardarMpf(CatMpf filtro);
    void eliminarMpf(Integer id);
    
    List<CatPresentacion> consultarPresentacion();
    
    List<Medicamento> consultarMedicamento(Medicamento filtro);
    List<Medicamento> consultarMedicamentoComplete(String nombre);
    Medicamento guardarMedicamento(Medicamento filtro);
    void eliminarMedicamento(Long id);
}
