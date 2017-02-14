package consultorio.lgm.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public final class SQLUtil {
    public static final String OPERADOR_IGUAL = "=";
    public static final String OPERADOR_DIFERENTE = "<>";
    public static final String SIGNO_COMA = ",";
    
    /**
     * Constructor de la clase.
     */
    private SQLUtil() { }
    
    /**
     * Se encarga de tomar un objeto, obtener el valor de sus atributos y si no son null, genera una cadena del 
     * tipo campo = valor, donde el operador = es pasado como parámetro, campo es el nombre del atributo y valor
     * es el valor del objeto.
     * @param entidad Entidad de la que se requiere tomar sus valores.
     * @param operador Operador utilizado para la relación.
     * @return Lista de Strings conteniendo las relaciones.
     */
    public static List<String> analiza(Object entidad , String operador) {
        List<String> listaResultado = new ArrayList<String>();
        String resultado;
        try {
            for (Field field : entidad.getClass().getDeclaredFields()) {
                resultado = armaCadena(entidad , field.getName() , operador);
                if (resultado != null) {
                    listaResultado.add(resultado);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return listaResultado;
    }
    
    /**
     * Se encarga de tomar un objeto, obtener el valor los atributos que recibe como parámetro y si no son null
     * , genera una cadana del tipo campo = valor, donde los operadores son pasados como parámetros, atributos 
     * son los nombres que recibe como parámetro y valor es el valor del objeto.
     * @param entidad Entidad de la que se requiere tomar sus valores.
     * @param atributos lista de atributos.
     * @param operadores lista de operadores.
     * @return Lista de Strings conteniendo las relaciones.
     */
    public static List<String> analiza(Object entidad , String [] atributos, String []operadores) {
        List<String> listaResultado = new ArrayList<String>();
        String resultado;
        if (atributos.length != operadores.length) {
            throw new RuntimeException("El número de atributos es diferente al número de operadores.");
        }
        try {
            for (int indice = 0; indice < atributos.length; indice++) {
                resultado = armaCadena(entidad, atributos[indice] , operadores[indice]);
                if (resultado != null) {
                    listaResultado.add(resultado);
                } 
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return listaResultado;
    }
    
    /**
     * Se encarga de tomar un objeto, obtener el valor los atributos que recibe como parámetro y si no son null
     * , genera una cadana del tipo campo = valor, donde el operador es pasado como parámetro, atributos son 
     * los nombres que recibe como parámetro y valor es el valor del objeto.
     * @param entidad Entidad de la que se requiere tomar sus valores.
     * @param atributos lista de atributos.
     * @param operador Operador usado en la conjunción.
     * @return Lista de Strings conteniendo las relaciones.
     */
    public static List<String> analiza(Object entidad , String [] atributos, String operador) {
        List<String> listaResultado = new ArrayList<String>();
        String resultado;
        try {
            for (int indice = 0; indice < atributos.length; indice++) {
                resultado = armaCadena(entidad, atributos[indice] , operador);
                if (resultado != null) {
                    listaResultado.add(resultado);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return listaResultado;
    }
    
    /**
     * Se engarga de generar una cadena del tipo atributo operador valor en base al atributo de un objeto.
     * @param entidad Entidad de la que se generará la cadena.
     * @param atributo Atributo que será usado en la parte izquierda de la cadena.
     * @param operador Operador que será usado en medio de la cadena.
     * @return Regresa una cadena con el formato atributo operador (=,<,>, etc.) valor (valor tomado del objeto)
     * @throws Exception
     */
    public static String armaCadena(Object entidad, String atributo, String operador) throws Exception {
    	
        Column columna;
        Field field;
        Object valor = null;
        BeanInfo info = Introspector.getBeanInfo(entidad.getClass(), Object.class);
        PropertyDescriptor[] descriptores = info.getPropertyDescriptors(); 
        for (PropertyDescriptor descriptor : descriptores) {  
            String nombre = descriptor.getName();  
            Class<?> type = descriptor.getPropertyType();
            Method getter = descriptor.getReadMethod();
            if (nombre.equals(atributo)) {
                if (getter != null) {
                    valor = getter.invoke(entidad);
                } else if (type.getName().equals(Boolean.class.getName())) {
                    throw new RuntimeException("Los métodos con prefijo 'is' no son soportados.");
                }
                if (valor != null) {
                    String tipoStr = type.getName();
                    if (!(("long".equals(tipoStr) || "byte".equals(tipoStr) || "int".equals(tipoStr)
                            || "short".equals(tipoStr) || "float".equals(tipoStr) || "double".equals(tipoStr))
                                        && ("0".equals(valor.toString()) || "0.0".equals(valor.toString())))) {
                        field = entidad.getClass().getDeclaredField(nombre);
                        columna = field.getAnnotation(Column.class);
                        
                        if ("java.lang.String".equals(tipoStr) || "java.lang.Character".equals(tipoStr)) {
                            valor = "'" + valor + "'";
                        }
                        if ("java.util.Date".equals(tipoStr)) {
                        	
                            valor = "'" + new SimpleDateFormat("yyyy-MM-dd").format(valor) + "'";
                        }
                        if (columna != null) {
                            return columna.name() + operador + valor;
                        } else {
                            return nombre + operador + valor;
                        }
                    }
                }
            }
        }  
        return null;
    }
    
    /**
     * Se encarga de armar una cadena en base a un valor
     * @param listaCondiciones Lista de cadenas a las cuales se agregará las cadenas encontradas.
     * @param nombreCampo Nombre del campo de la tabla de la base de datos.
     * @param valor Valor contra el que se comparará el campo.
     * @param operador operador utilizado para la comparación.
     */
    public static void armaCadena(List<String>listaCondiciones, String nombreCampo, Object valor, String operador) {
        if (valor != null) {
            String tipoStr = valor.getClass().getName();
            if (!(("long".equals(tipoStr) || "byte".equals(tipoStr) || "int".equals(tipoStr)
                        || "short".equals(tipoStr) || "float".equals(tipoStr) || "double".equals(tipoStr))
                                    && ("0".equals(valor.toString()) || "0.0".equals(valor.toString())))) {
                if ("java.lang.String".equals(tipoStr) || "java.lang.Character".equals(tipoStr)) {
                    valor = "'" + valor + "'";
                }
                if ("java.util.Date".equals(tipoStr)) {
                    valor = "'" + new SimpleDateFormat("yyyy-MM-dd").format(valor) + "'";
                }
                listaCondiciones.add(nombreCampo + operador + valor);
            }
        }
    }
    
    /**
     * Se encarga de armar una cadena en base a un valor
     * @param listaCondiciones Lista de cadenas a las cuales se agregará las cadenas encontradas.
     * @param nombreCampo Nombre del campo de la tabla de la base de datos.
     * @param valorInicio Valor inicial.
     * @param valorFin Valor final.
     */
    public static void armaCadenaBetween(List<String>listaCondiciones, String nombreCampo, Date valorInicio, Date valorFin) {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (valorInicio != null && valorFin != null) {
            listaCondiciones.add(nombreCampo + " BETWEEN '" + simpleDateFormat.format(valorInicio) 
                                                    + "' AND '" + simpleDateFormat.format(valorFin) + "' ");
        }
    }
    
    /**
     * Se encarga de generar la clausula where en base a la lista de cadenas que recibe y el operador.
     * @param condicionesWhere Lista de cadenas en formato campo operador valor.
     * @param operador Operador usado para la conjunción de las condiciones(AND u OR).
     * @param iniciaWhere indica si se debe iniciar la cláusula Where o ya está iniciada.
     * @return Cláusula where generada.
     */
    public static String generaClausulaWhere(List<String> condicionesWhere , String operador, boolean iniciaWhere) {
        StringBuilder where = new StringBuilder(" ");
        final String constESPACIO = " ";
        if (condicionesWhere.size() > 0) {
            if (iniciaWhere) {
                where.append(" Where ");
            } else {
                where.append(" " + operador + " ");
            }
            String constOPERADOR = "";
            for (String condicion : condicionesWhere) {
                where.append(constOPERADOR + constESPACIO +  condicion);
                constOPERADOR = constESPACIO + operador;
            }
        }
        return where.toString();
    }
    
    /**
     * Permite agregar a una cadena la lista generada de campos para ordenar por(Order By).
     * @param sqlNativo Cadena a la que se le agregará la cláusula Order By
     * @param ordenaPor campos de la tabla incluídos en la cláusula Order By
     * @return Cadena SQL generada.
     */
    public static String agregaOrdenaPor(String sqlNativo, String... ordenaPor) {
        if (ordenaPor != null && ordenaPor.length > 0) {
            String constCOMA = " Order By ";
            for (String ordena : ordenaPor) {
                sqlNativo += constCOMA + ordena;
                constCOMA = SIGNO_COMA;
            }
        } 
        return sqlNativo;
    }
}
