package consultorio.lgm.excepciones;

public final class LGMConstantesExcepciones {

	// --------------------- Errores Generales (1-999) --------------------------------------
	// --------------------- Errores DAOs (1000-1999)-------------------------------------------	
	/**
     * Indica que ocurrió un error en el DAO por interrupció de la conexión con la BD.
     */
    public static final int DAO_ERROR_BD = 1000;
    public static final int DAO_ERROR_INTERRUPCION_BD = 1001;
    public static final int DAO_ERROR_ERROR_VIOLACION_INTEGRIDAD = 1002;
    public static final int DAO_ERROR_PARAMETROS_INVALIDOS = 1003;
    public static final int DAO_ERROR_CONEXION_BASE_DATOS = 1004;
    public static final int DAO_ERROR_ENTIDAD_NO_ENCONTRADA = 1005;
    public static final String DAO_CODIGO_ERROR_INTERRUPCION_BD = "57P01";
    public static final String DAO_CODIGO_ERROR_VIOLACION_INTEGRIDAD = "23000";
    public static final int DAO_FILTRO_VALORES_NULL = 1006;
    public static final int DAO_VALORES_NULL = 1007;
    public static final int DAO_ENTIDAD_DUPLICADA = 1008;
    
	
	// --------------------- Errores Servicios (2000 - 2999) --------------------------------------
	    /**
     * Indica que el usuario que se intenta guardar ya existe en la base de
     * datos.
     */
    public static final int SERVICIO_USUARIO_DUPLICADO = 2000;
    /**
     * Indica que se intenta actualizar un usuario con un nombre corto de
     * usuario diferente.
     */
    public static final int SERVICIO_USUARIO_NOMBRE_CORTO_DIFERENTE = 2001;
    /**
     * Indica que se intenta guardar una entidad con un id diferente de null al
     * momento de guardar.
     */
    public static final int SERVICIO_ID_ENTIDAD_NO_NULL = 2002;
    /**
     * Existen valores que no deben ser null en la entidad que se intenta
     * almacenar/modificar.
     */
    public static final int SERVICIO_VALORES_NULL = 2003;
    /**
     * El usuario que se intenta modificar/eliminar no existe.
     */
    public static final int SERVICIO_USUARIO_NO_EXISTE = 2004;
    /**
     * El atributo eliminar de la entidad no puede ser cambiado modificado.
     */
    public static final int SERVICIO_MODIFICAR_ATRIBUTO_ELIMINAR = 2005;
    /**
     * Código de error de comunicación con la base de datos.
     */
    public static final int SERVICIO_COMUNICACION_BD = 2006;
    public static final int SERVICIO_CATALOGOS_GUARDAR = 2007;
    public static final int SERVICIO_CATALOGOS_ELIMINAR = 2008;
    public static final int SERVICIO_PACIENTE_GUARDAR = 2009;
    public static final int SERVICIO_CONSULTAS_RECETA_EXISTE = 2010;
    public static final int SERVICIO_CONSULTAS_CREAR_PDF = 2011;
    public static final int SERVICIO_CONSULTAS_GUARDAR_RUTA_PDF = 2012;
    
    
    //---------- FIN Errores Sevicio HSBCBatchLayout---------------------------------------
	
	// --------------------- Errores Acceso al Sistema (3000 - 3999) --------------------------------------
	/**
	 * Indica que el usuario está bloqueado.
	 */
    public static final int ACCESO_USUARIO_BLOQUEADO = 3000;
    public static final int ACCESO_USUARIO_REQUERIDO = 3001;
    public static final int ACCESO_USUARIO_CHAR_INV = 3002;
    public static final int ACCESO_PASS_CHAR_INV = 3003;
    public static final int DATOS_ENTRADA_INVALIDOS = 2200;
    public static final int ERROR_CONSULTAR = 2201;
   
    
 // --------------------- Errores de reportes (4000 - 4999) --------------------------------------
    public static final int REPORTE_ERROR_CREAR_ARCHIVO_TEMPORAL = 4000;
    public static final int REPORTE_ERROR_LEER_ARCHIVO_REPORTE = 4001;
    public static final int REPORTE_ERROR_REPORTE_VACIO = 4002;
    public static final int REPORTE_ERROR_LLENAR_REPORTE = 4003;
    public static final int REPORTE_ERROR_EXPORTAR = 4004;
    public static final int REPORTE_ERROR_FORMATO_INCORRECTO = 4005;

    public static Object recuperaInstancia() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //Errores del DAO de conexion en el WS
    public static final int WS_ERROR_CONEXION = 5001;
    public static final int WS_ERROR_GENERAR_DOCUMENTO_PDF = 5002;
    public static final int WS_ERROR_AXIS_FAULT = 5003;
    public static final int WS_ERROR_FORMATO_URL = 5004;
    public static final int WS_DATOS_RESPUESTA_NO_VALIDOS = 5005;
    public static final int WS_PARAMETROS_NO_VALIDOS = 5006;
    
    private LGMConstantesExcepciones (){}
	
}
