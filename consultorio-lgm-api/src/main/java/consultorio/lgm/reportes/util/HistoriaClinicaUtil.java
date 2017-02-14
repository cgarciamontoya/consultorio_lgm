/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultorio.lgm.reportes.util;

import consultorio.lgm.datos.entidades.AntecedentesGenerales;
import consultorio.lgm.datos.entidades.AntecedentesGinecologicos;
import consultorio.lgm.datos.entidades.Consulta;
import consultorio.lgm.datos.entidades.Paciente;
import consultorio.lgm.datos.entidades.RelAntecedentesEnfermedades;
import consultorio.lgm.reportes.vo.HistoriaClinicaVO;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author cgarcia
 */
public class HistoriaClinicaUtil {
    
    private static final int TAB_DEFAULT = 5;
    private static final String TAB = "&#09;";
    private static final String LINEA = "_";
    private static final int REP_LINEA = 102;
    private static final int SPC_TAB = 7;
    private static final String SALTO_LINEA = "<br>";
    private static final String ESPACIADOR = "&nbsp;&nbsp;&nbsp;";
    private static final String NO_ESPECIFICADO = "No Especificado";
    
    private static DateFormat formateador;
    
    public static HistoriaClinicaVO crearHC(Paciente paciente, Consulta notaMedica) {
        formateador = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(crearFichaIdentificacion(paciente));
        sb.append(crearAHF(paciente));
        sb.append(crearAPP(paciente.getIdAntecedentes()));
        sb.append(crearAPNP(paciente.getIdAntecedentes()));
        if (paciente.getGeneroFemenino()) {
            sb.append(crearGO(paciente.getIdAntecedentesGinecologicos()));
        }
        if (notaMedica != null) {
            sb.append(crearNotaMedica(notaMedica));
        }
        return new HistoriaClinicaVO(sb.toString());
    }
    
    private static String crearLinea() {
        String linea = "<font color=\"#9999FF\">";
        for (int i = 0; i < REP_LINEA;i++) {
            linea+=LINEA;
        }
        return linea + "</font>" + SALTO_LINEA;
    }
    
    private static int calcularCentrado(int longitud) {
        int dif = REP_LINEA - longitud;
        int inicio = dif / 2;
        return inicio / SPC_TAB;
    }
    
    private static String crearEncabezado(String titulo) {
        StringBuilder enc = new StringBuilder();
        enc.append(crearLinea());
        for (int i = 0; i < calcularCentrado(titulo.length()); i++) {
            enc.append(TAB);
        }
        enc.append("<b>")
                .append(titulo)
                .append("</b>")
                .append(SALTO_LINEA)
                .append(SALTO_LINEA);
        return enc.toString();
    }
    
    private static String etiquetaB(String etiqueta) {
        return "<b>" + etiqueta + ": </b>";
    }
    
    private static String crearFichaIdentificacion(Paciente paciente) {
        StringBuilder fi = new StringBuilder();
        fi.append(crearEncabezado("FICHA DE IDENTIFICACION"));
        //Se agrega el nombre
        fi.append(etiquetaB("Nombre"))
                .append(paciente.getNombre()).append(" ")
                .append(paciente.getApellidoPaterno()).append(" ")
                .append(paciente.getApellidoMaterno()).append(" ")
                .append(SALTO_LINEA);
        //Se agrega el domicilio y telefono
        fi.append(etiquetaB("Direcci&oacute;n"))
                .append(paciente.getDireccion())
                .append(ESPACIADOR)
                .append(etiquetaB("Tel&eacute;fono"))
                .append(paciente.getTelefono() != null && !paciente.getTelefono().isEmpty() ?
                        paciente.getTelefono() : "Ninguno")
                .append(SALTO_LINEA);
        //Se agrega fecha naciento, edad y sexo
        fi.append("<b>Fecha Nac.:</b> ")
                .append(formateador.format(paciente.getFechaNacimiento()))
                .append(ESPACIADOR)
                .append("<b>Edad:</b> ")
                .append(paciente.getEdad())
                .append(ESPACIADOR)
                .append("<b>Sexo:</b> ")
                .append(paciente.getGeneroFemenino() ? "Femenino" : "Masculino")
                .append(SALTO_LINEA);
        //Se agrega escolaridad, edo civil, y religicion
        fi.append("<b>Escolaridad:</b> ")
                .append(paciente.getIdEscolaridad().getDescripcion())
                .append(ESPACIADOR)
                .append("<b>Edo. Civil:</b> ")
                .append(paciente.getIdEstadoCivil().getDescripcion())
                .append(ESPACIADOR)
                .append("<b>Religi&oacute;n:</b> ")
                .append(paciente.getReligion() != null && !paciente.getReligion().isEmpty() ?
                        paciente.getReligion() : "Ninguna")
                .append(SALTO_LINEA)
                .append(SALTO_LINEA);
        return fi.toString();
    }
    
    private static String crearAHF(Paciente paciente) {
        StringBuilder sb = new StringBuilder();
        sb.append(crearEncabezado("ANTECEDENTES HEREDO FAMILIARES"))
                .append(etiquetaB("C.G.P."))
                .append(paciente.getIdAntecedentes().getCargaGeneticaPaternal() != null &&
                        !paciente.getIdAntecedentes().getCargaGeneticaPaternal().isEmpty() ?
                        paciente.getIdAntecedentes().getCargaGeneticaPaternal() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("C.G.M."))
                .append(paciente.getIdAntecedentes().getCargaGeneticaMaternal() != null &&
                        !paciente.getIdAntecedentes().getCargaGeneticaMaternal().isEmpty() ?
                        paciente.getIdAntecedentes().getCargaGeneticaMaternal() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(SALTO_LINEA);
        return sb.toString();
    }
    
    private static String crearAPP(AntecedentesGenerales ag) {
        StringBuilder sb = new StringBuilder();
        sb.append(crearEncabezado("ANTECEDENTES PERSONALES PATOLOGICOS"))
                .append(etiquetaB("Enf. Cr&oacute;nicas"));
        if (ag.getRelAntecedentesEnfermedadesList() != null && !ag.getRelAntecedentesEnfermedadesList().isEmpty()) {
            String enf = "";
            for (RelAntecedentesEnfermedades ae : ag.getRelAntecedentesEnfermedadesList()) {
                enf += ae.getIdEnfermedad().getNombre() + ", ";
            }
            enf = enf.substring(0, enf.length() - 2);
            sb.append(enf);
        } else {
            sb.append(NO_ESPECIFICADO);
        }
        sb.append(SALTO_LINEA)
                .append(etiquetaB("Quir&uacute;rgicas"))
                .append(ag.getQuirurgicas() != null && !ag.getQuirurgicas().isEmpty() ?
                        ag.getQuirurgicas() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Hospitalizaciones"))
                .append(ag.getHospitalizaciones() != null && ag.getHospitalizaciones() &&
                        ag.getMotivoHospitalizacion() != null && !ag.getMotivoHospitalizacion().isEmpty() ?
                    ag.getMotivoHospitalizacion() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Transfusiones"))
                .append(ag.getTransfuciones() != null && ag.getTransfuciones() &&
                        ag.getMotivoTransfuciones() != null && !ag.getMotivoTransfuciones().isEmpty() ?
                        ag.getMotivoTransfuciones() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Traum&aacute;ticas"))
                .append(ag.getTraumaticas() != null && ag.getTraumaticas() &&
                        ag.getMotivoTraumaticas() != null && !ag.getMotivoTraumaticas().isEmpty() ?
                        ag.getMotivoTraumaticas() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Alergias"))
                .append(ag.getAlergias() != null && ag.getAlergias() &&
                        ag.getDescripcionAlergias() != null && !ag.getDescripcionAlergias().isEmpty() ?
                        ag.getDescripcionAlergias() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Gpo. Sanguineo"))
                .append(ag.getIdGrupoSanguineo().getNombre())
                .append(ag.getRhPositivo() ? "+" : "-")
                .append(SALTO_LINEA)
                .append(SALTO_LINEA);
        return sb.toString();
    }
    
    private static String crearAPNP(AntecedentesGenerales ag) {
        StringBuilder sb = new StringBuilder();
        sb.append(crearEncabezado("ANTECEDENTES PERSONALES NO PATOLOGICOS"))
                .append(etiquetaB("Vivienda"))
                .append(ag.getVivienda() != null && !ag.getVivienda().isEmpty() ?
                        ag.getVivienda() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Alimentaci&oacute;n"))
                .append(ag.getAlimentacion() != null && !ag.getAlimentacion().isEmpty() ?
                        ag.getAlimentacion() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Higiene"))
                .append(ag.getHigiene() != null && !ag.getHigiene().isEmpty() ?
                        ag.getHigiene() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Toxicoman&iacute;as"))
                .append(ag.getToxicomanias() != null && !ag.getToxicomanias().isEmpty() ?
                        ag.getToxicomanias() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(SALTO_LINEA);
        return sb.toString();
    }
    
    private static String crearGO(AntecedentesGinecologicos ag) {
        StringBuilder sb = new StringBuilder();
        sb.append(crearEncabezado("ANTECEDENTES GINECO OBSTETRICOS"))
                .append(etiquetaB("Menarca"))
                .append(ag.getMenarca())
                .append(ESPACIADOR)
                .append(etiquetaB("Ciclo Menstrual"))
                .append(ag.getCicloMenstrualRegular() != null && ag.getCicloMenstrualRegular() ?
                        "Regular" : "Irregular")
                .append(ESPACIADOR)
                .append(etiquetaB("Duraci&oacute;n"))
                .append(ag.getDuracionCicloMenstrual())
                .append(SALTO_LINEA)
                .append(etiquetaB("I.V.S.A."))
                .append(ag.getIvsa())
                .append(ESPACIADOR)
                .append(etiquetaB("Num. Parejas"))
                .append(ag.getNumeroParejas())
                .append(ESPACIADOR)
                .append(etiquetaB("M.P.F."))
                .append(ag.getIdCatMpf().getNombre())
                .append(SALTO_LINEA)
                .append(etiquetaB("Gestas"))
                .append(ag.getGesta())
                .append(ESPACIADOR)
                .append(etiquetaB("Partos"))
                .append(ag.getParto())
                .append(ESPACIADOR)
                .append(etiquetaB("Abortos"))
                .append(ag.getAbortos())
                .append(SALTO_LINEA)
                .append(etiquetaB("Cesareas"))
                .append(ag.getCesarea())
                .append(ESPACIADOR)
                .append(etiquetaB("F.U.M"))
                .append(ag.getFechaUltimaMenstruacion() != null ?
                        formateador.format(ag.getFechaUltimaMenstruacion()) : NO_ESPECIFICADO)
                .append(ESPACIADOR)
                .append(etiquetaB("F.P.P."))
                .append(ag.getFechaProbableParto() != null ?
                        formateador.format(ag.getFechaProbableParto()) : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Expl. Mama"))
                .append(ag.getExploracionMama() != null && ag.getExploracionMama() ?
                        "Si" : "No")
                .append(ESPACIADOR)
                .append(etiquetaB("Complicaciones"))
                .append(ag.getComplicacionesEmbarazo() != null && !ag.getComplicacionesEmbarazo().isEmpty() ?
                        ag.getComplicacionesEmbarazo() : NO_ESPECIFICADO)
                .append(SALTO_LINEA)
                .append(etiquetaB("Papanicolau"))
                .append(ag.getPapanicolaou() != null && ag.getPapanicolaou() &&
                        ag.getResultadoPapanicolaou() != null && !ag.getResultadoPapanicolaou().isEmpty() ?
                        ag.getResultadoPapanicolaou() : "No")
                .append(SALTO_LINEA)
                .append(SALTO_LINEA);
        return sb.toString();
    }
    
    private static String crearNotaMedica(Consulta notaMedica) {
        DecimalFormat df = new DecimalFormat("###0.00");
        
        StringBuilder sb = new StringBuilder();
        sb.append(crearEncabezado("NOTA MEDICA"))
                .append(etiquetaB("Peso"))
                .append(df.format(notaMedica.getPeso()))
                .append(" Kg.")
                .append(ESPACIADOR)
                .append(etiquetaB("Talla"))
                .append(df.format(notaMedica.getTalla()))
                .append(" mts.")
                .append(ESPACIADOR)
                .append(etiquetaB("F.C."))
                .append(notaMedica.getFrecuenciaCardiaca() != null && notaMedica.getFrecuenciaCardiaca() > 0 ?
                        notaMedica.getFrecuenciaCardiaca() + " ppm" : "NA")
                .append(ESPACIADOR)
                .append(etiquetaB("F.R."))
                .append(notaMedica.getFrecuenciaRespiratoria() != null && notaMedica.getFrecuenciaRespiratoria() > 0 ?
                        notaMedica.getFrecuenciaRespiratoria() + "rpm" : "NA")
                .append(ESPACIADOR)
                .append(etiquetaB("T.A."));
                if (notaMedica.getTaDiastolica() != null && notaMedica.getTaDiastolica() > 0
                        && notaMedica.getTaSistolica() != null && notaMedica.getTaSistolica() > 0) {
                    sb.append(notaMedica.getTaSistolica()).append("/").append(notaMedica.getTaDiastolica());
                } else {
                    sb.append("NA");
                }
                sb.append(ESPACIADOR)
                        .append(etiquetaB("Temp."))
                        .append(notaMedica.getTemperatura() != null && notaMedica.getTemperatura() > 0 ?
                                df.format(notaMedica.getTemperatura()) + " C" : "NA")
                        .append(ESPACIADOR)
                        .append(etiquetaB("Glucosa"))
                        .append(notaMedica.getGlucosa() != null && notaMedica.getGlucosa() > 0 ?
                                df.format(notaMedica.getGlucosa()) + " mg/dl" : "NA")
                        .append(SALTO_LINEA)
                        .append(etiquetaB("Motivo Consulta"))
                        .append(notaMedica.getMotivoConsulta())
                        .append(SALTO_LINEA)
                        .append(etiquetaB("Plan"))
                        .append(notaMedica.getPlan() != null && !notaMedica.getPlan().isEmpty() ?
                                notaMedica.getPlan() : NO_ESPECIFICADO)
                        .append(SALTO_LINEA)
                        .append(etiquetaB("Dx"))
                        .append(notaMedica.getDiagnostico())
                        .append(SALTO_LINEA);
        return sb.toString();
    }
}
