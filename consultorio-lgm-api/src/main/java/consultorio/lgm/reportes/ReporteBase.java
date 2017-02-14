/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.reportes;

import consultorio.lgm.excepciones.ExcepcionReporte;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.excepciones.LGMFabricaExcepciones;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author farodriguez
 */
public abstract class ReporteBase {
    
    /**
     * Variables InputStream que contienen el escudo de Zacatecas que esta presente en todos los certificados.
     */
    protected InputStream medicinaInputStream;
    
    /**
     * Variables InputStream que contienen el logotipo del gobierno de Zacatecas 
     * que esta presente en todos los certificados.
     */
    protected InputStream uazInputStream;
    private static final float ANCHO_CARACTER = 8.0f;
    
    //DataSource del cual se obtendrá la conexión a la base de datos que se le pasará a JasperReport.
    protected DataSource lgmDataSource;
    
    private void cargarImagenes() {
        medicinaInputStream = ReporteBase.class.getResourceAsStream("/reportes/logomedicina.jpg");
        uazInputStream = ReporteBase.class.getResourceAsStream("/reportes/logouaz.jpg");
    }
    
    /**
     * Clase encargada de crear los reportes a partir de datos enviados por
     * parametros utilizando plantillas con extención jasper.
     */
    public ReporteBase() {

    }
    
    /**
     * 
     * @param parametros parametros esperados de información común entre los certificados
     */
    protected void llenarInformacionComun(Map<String, Object> parametros){
        cargarImagenes();
        parametros.put("EscudoMedicina", medicinaInputStream);
        parametros.put("EscudoUAZ", uazInputStream);
    }
    
    /**
     * 
     * @param nombreReporte es el nombre de la plantilla que se utilizara para generar el certificado
     * @param detalle lista de detalles a utilizar para el certificado
     * @param parametros parametros enviados para el certificado
     * @return objeto JasperPrint que contiene la ruta del archivo jasper.
     */
    protected JasperPrint llenarReporte(String nombreReporte, 
            @SuppressWarnings("rawtypes") List detalle, Map<String, Object> parametros, boolean emptyDS){
        //JasperPrint donde generaremos el reporte.
        JasperPrint jasperPrint;
        try {
            //Ruta del reporte (.jasper)
            URL reporte = this.getClass().getResource("/reportes/" + nombreReporte);
            JasperReport reporteJasper = (JasperReport) JRLoader.loadObject(reporte);
            //Si el detalle está vacío recuperamos una conexión a la base de datos y llenamos el reporte usándola.
            if (detalle == null) {
                if (emptyDS) {
                    jasperPrint = JasperFillManager.fillReport(reporteJasper, parametros, new JREmptyDataSource());
                } else {
                    jasperPrint = JasperFillManager.fillReport(reporteJasper, parametros, lgmDataSource.getConnection());
                }
            } else {
                jasperPrint = JasperFillManager.fillReport(reporteJasper, parametros, new JRBeanCollectionDataSource(detalle));
            }
        } catch (Exception e) {
            throw LGMFabricaExcepciones.recuperaInstancia() 
                        .crear(ExcepcionReporte.class, LGMConstantesExcepciones.REPORTE_ERROR_LLENAR_REPORTE);
        }
        //Si el reporte está vacío lanzamos una excepción.
        if (jasperPrint.getPages().size() == 0) {
            throw LGMFabricaExcepciones.recuperaInstancia() 
                            .crear(ExcepcionReporte.class, LGMConstantesExcepciones.REPORTE_ERROR_REPORTE_VACIO);
        }

        return jasperPrint;
    }
    
    /**
     * 
     * @param jasperPrint objeto que contiene la ruta del archivo jasper a utilizar.
     * @param tipoReporte especificación de que tipo de formato de salida se espera del reporte.
     * @param salida lugar de salida del certificado creado.
     */
    protected void  exportarReporte(JasperPrint jasperPrint, TipoReporte tipoReporte, OutputStream salida) {        
        if (jasperPrint != null) {
            try{
                JRExporter exporter;
                switch(tipoReporte) {
                    case PDF:
                        JasperExportManager.exportReportToPdfStream(jasperPrint , salida);
                    return;
                    case XLS:
                        exporter = new JExcelApiExporter();
                        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                    break;
                    case CSV:
                        exporter = new JRCsvExporter();
                        exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, "|");
                    break;
                    case DOCX:
                        exporter = new JRDocxExporter();
                    break;
                    case PPT:
                        exporter = new JRPptxExporter();
                    break;
                    default:
                        exporter = new JRTextExporter();
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, ANCHO_CARACTER);
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, ANCHO_CARACTER);
                    break;
                }
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, salida);
                    exporter.exportReport();

            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }

    public void setLgmDataSource(DataSource lgmDataSource) {
        this.lgmDataSource = lgmDataSource;
    }
    
    
}