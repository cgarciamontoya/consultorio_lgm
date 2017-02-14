/**
 * Clase que forma parte del componente ReportesManagerServicio
 */
package consultorio.lgm.reportes;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Interfaz que deben implementar todos los generadores de reportes.
 * @author hanshernandez
 */
public interface Reporte {
    /**
     * Método encargado de generar el reporte en el OutputStream indicado con el formato indicado.
     * 
     * @param detalle Lista de elementos a mostrar en el reporte, si este elemento es null, 
     * se asume que el reporte realiza una consulta directa a la base de datos 
     * por lo que el reporte es procesado utilizando una conexión a la base de datos, en caso
     * de no ser null, se procesa el reporte utilizando el detalle.
     * @param parametros Es un mapa que contiene la lista de parámetros que se le pasa 
     * al generador de reportes específico.
     * @param tipoReporte Tipo del reporte para exportar (PDF, XLS o CSV).
     * @param salida OutputStream donde se escribirá el reporte generado. 
     */
    void generarReporte(@SuppressWarnings("rawtypes")List detalle, 
            Map<String, Object> parametros, TipoReporte tipoReporte, OutputStream salida);
}
