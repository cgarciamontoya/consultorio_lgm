/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultorio.lgm.reportes;

import consultorio.lgm.constantes.LGMConstantes;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author USUARIO
 */
public class NotaMedicaReporte extends ReporteBase implements Reporte {

    @Override
    public void generarReporte(List detalle, Map<String, Object> parametros, TipoReporte tipoReporte, OutputStream salida) {
        llenarInformacionComun(parametros);
        JasperPrint jasperPrint = llenarReporte(LGMConstantes.NOMBRE_REPORTE_NOTA_MEDICA,
                detalle, parametros, true);
        exportarReporte(jasperPrint, tipoReporte, salida);
    }
    
}
