/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * hanshernandez
 */

package consultorio.lgm.servicios.impl;

import consultorio.lgm.excepciones.ExcepcionReporte;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.reportes.Reporte;
import consultorio.lgm.reportes.TipoReporte;
import consultorio.lgm.servicios.ReportesManagerServicio;
import consultorio.lgm.servicios.ServicioBase;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class ReportesManagerServicioImpl extends ServicioBase implements ReportesManagerServicio, Serializable {
    private static final long serialVersionUID = 4465769534531268061L;

    private Map<String, Object> generadoresReporte;

    
    @Override
    public ByteArrayInputStream generarReporte(List detalle, Map<String, Object> parametros, String nombreGeneradorReporte, TipoReporte tipoReporte) {
        
        
        //Validamos que el generador exista en la lista de generadores.
        if (!generadoresReporte.containsKey(nombreGeneradorReporte)) {
            throw new IllegalArgumentException("El reporte que intenta generar no existe.");
        }
        
        if (parametros == null) {
            throw new IllegalArgumentException("Los parametros no pueden ser null.");
        }
        
        if (tipoReporte == null) {
            throw new IllegalArgumentException("El tipo de reporte no pueden ser null.");
        }
   
        //Escribiendo el reporte en un archivo temporal.
        FileOutputStream fileOutputStream;
        File reporteTemporal;
        try {
            reporteTemporal = File.createTempFile(nombreGeneradorReporte , ".tmp");
            fileOutputStream = new FileOutputStream(reporteTemporal);
            Reporte reporte = (Reporte) generadoresReporte.get(nombreGeneradorReporte);
            reporte.generarReporte(detalle, parametros, tipoReporte, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw fabricaExcepciones.crear(ExcepcionReporte.class, 
                    LGMConstantesExcepciones.REPORTE_ERROR_CREAR_ARCHIVO_TEMPORAL);
        } 
        try {
            ByteArrayInputStream resultado = new ByteArrayInputStream(FileUtils.readFileToByteArray(reporteTemporal)); 
            reporteTemporal.delete();
            return resultado;
        } catch (IOException e) {
            throw fabricaExcepciones.crear(ExcepcionReporte.class, 
                    LGMConstantesExcepciones.REPORTE_ERROR_LEER_ARCHIVO_REPORTE);
        }
    }

    public void setGeneradoresReporte(Map<String, Object> generadores) {
        this.generadoresReporte = generadores;
    }
}
