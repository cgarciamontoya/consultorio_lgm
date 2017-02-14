/* *
 * Este componente incluye también la clase base para los generadores de reportes.
 * Esta clase contiene la funcionalidad para 
 * agregar elementos comunes al reporte así como la funcionalidad de exportar el reporte a los diferentes formatos.
 *
 *Autor: hanshernandez
 *Fecha de creación: 08/02/2014 
*/ 
package consultorio.lgm.servicios;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

//import mx.gob.finanzas.scb.reportes.TipoReporte;
import consultorio.lgm.reportes.TipoReporte;

/** 
 * Interfaz principal del componente ReportesManagerServicio.
 * @author hanshernandez.
 */ 
public interface ReportesManagerServicio {
    /**
     * Método encargado de interactuar con el generador de reporte solicitado de tal manera 
     * que permita crear un reporte
     * en el formato especificado y regresar los bytes que conforman el mismo.
     * @param detalle Lista de elementos a mostrar en el reporte, si este elemento es null, 
     * se asume que el reporte realiza una consulta directa a la base de datos 
     * por lo que el reporte es procesado utilizando una conexión a la base de datos, en caso
     * de no ser null, se procesa el reporte utilizando el detalle.
     * @param parametros Es un mapa que contiene la lista de parámetros que se le pasa 
     * al generador de reportes específico.
     * @param nombreGeneradorReporte Nombre del generador de reportes a ser utilizado para generar el reporte.
     * @param tipoReporte Tipo del reporte para exportar (PDF, XLS o CSV).
     * @return ByteArrayInputStream con el conjunto de bytes que forman el archivo.
     */
    ByteArrayInputStream generarReporte(@SuppressWarnings("rawtypes") 
            List detalle, Map<String, Object> parametros, String nombreGeneradorReporte, TipoReporte tipoReporte);
}
