package consultorio.lgm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Descripción: Clase de Utilería para el manejo de fechas.
 *
 * @author Heidy Jannet Hernández Díaz
 *
 */
public final class UtileriaFechas {

    /**
     * Constructor
     */
    private UtileriaFechas() {
    }

    /**
     * Obtiene la fecha que corresponde al primer día del mes.
     *
     * @param anio Año al que pertenece la fecha requerida.
     * @param mes Mes al que pertenece la fecha requerida. Se espera que el mes tenga valores de 1 a 12.
     * @return Fecha inicial del mes.
     */
    public static Date obtenerFechaInicial(Integer anio, Integer mes) {

        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes - 1);
        calendario.set(Calendar.DATE, calendario.getActualMinimum(Calendar.DAY_OF_MONTH));

        return calendario.getTime();
    }

    /**
     * Obtiene la fecha que corresponde al último día del mes.
     *
     * @param anio Año al que corresponde la fecha calculada.
     * @param mes Mes al que corresponde la fecha calculada. Se espera que el mes tenga valores de 1 a 12.
     * @return Fecha final del mes.
     */
    public static Date obtenerFechaFinal(Integer anio, Integer mes) {
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes - 1);
        calendario.set(Calendar.DATE, calendario.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendario.getTime();
    }

    /**
     * Establece la fecha inicial del mes a partir de la fecha actual
     *
     * @return fecha de inicio delmes.
     */
    public static Date obtenerFechaInicial() {
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.DATE, calendario.getMinimum(Calendar.DAY_OF_MONTH));
        calendario.set(Calendar.HOUR, calendario.getMinimum(Calendar.HOUR_OF_DAY));
        calendario.set(Calendar.MINUTE, calendario.getMinimum(Calendar.MINUTE));
        calendario.set(Calendar.SECOND, calendario.getMinimum(Calendar.SECOND));
        calendario.set(Calendar.MILLISECOND, calendario.getMinimum(Calendar.MILLISECOND));

        return calendario.getTime();
    }

    /**
     * Establece la fecha final del mes a partir de la fecha actual. Incluye la última hora de mes: 23:59:59
     *
     * @return fecha de final del mes.
     */
    public static Date obtenerFechaFinal() {
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.DATE, calendario.getMaximum(Calendar.DAY_OF_MONTH));
        calendario.set(Calendar.HOUR, calendario.getMaximum(Calendar.HOUR_OF_DAY));
        calendario.set(Calendar.MINUTE, calendario.getMaximum(Calendar.MINUTE));
        calendario.set(Calendar.SECOND, calendario.getMaximum(Calendar.SECOND));
        calendario.set(Calendar.MILLISECOND, calendario.getMaximum(Calendar.MILLISECOND));

        return calendario.getTime();
    }

    /**
     * Convierte una fecha en string a un tipo de dato Date
     *
     * @param fechaString Fecha en formato string
     * @return Date
     */
    public static Date obtenerFechaAPartirString(String fechaString) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/aaaa");
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(fechaString);
        } catch (ParseException ex) {
        }

        return fecha;
    }

    /**
     * Suma o resta el número de meses indicado en el campo cantidadMeses a la fecha establecida por el anio y el mes Si
     * cantidadMeses es positivo entonces lo suma, si cantidad meses es negatico entonces lo resta.
     *
     * @param anio Anio de la fecha
     * @param mes mes de la fecha
     * @param cantidadMeses numero de meses que se agregará o restará a la fecha actual
     * @return Instancia de Calendar con la fecha establecida
     */
    public static Calendar agregarMesAFecha(int anio, int mes, int cantidadMeses) {

        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes - 1);
        calendario.set(Calendar.DATE, calendario.getActualMinimum(Calendar.DAY_OF_MONTH));

        calendario.add(Calendar.MONTH, cantidadMeses);

        return calendario;
    }

    /**
     * Obtiene el número de mes a partir de la fecha actual
     *
     * @return el número de mes del 1 al 12
     */
    public static int obtenerMesFechaActual() {
        Calendar calendario = Calendar.getInstance();
        return calendario.get(Calendar.MONTH) + 1;
    }

    /**
     * Obtiene el año a partir de la fecha actual
     *
     * @return el número de mes del 1 al 12
     */
    public static int obtenerAnioFechaActual() {
        Calendar calendario = Calendar.getInstance();
        return calendario.get(Calendar.YEAR);
    }

    /**
     * Obtiene el número de mes a partir de la fecha dada
     *
     * @param fecha de la que se requiere obtener el mes
     * @return el número de mes del 1 al 12
     */
    public static int obtenerMesFecha(Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        return calendario.get(Calendar.MONTH) + 1;
    }

    /**
     * Obtiene el año partir de la fecha dada
     *
     * @param fecha fecha de la que se requiere obtener el año
     * @return año
     */
    public static int obtenerAnioFecha(Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        return calendario.get(Calendar.YEAR);
    }

    public static String obtenerNombreMes(int mes) {
        switch (mes) {
            case 1:
                return "ENERO";
            case 2:
                return "FEBRERO";
            case 3:
                return "MARZO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAYO";
            case 6:
                return "JUNIO";
            case 7:
                return "JULIO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SEPTIEMBRE";
            case 10:
                return "OCTUBRE";
            case 11:
                return "NOVIEMBRE";
            default:
                return "DICIEMBRE";
        }
    }

}
