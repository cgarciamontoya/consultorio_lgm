package consultorio.lgm.web;

/**
 * Clase de constantes para la capa de vista.
 */
public final class Constantes {

	/**
	 * Constructor de la clase.
	 */
	private Constantes() {
	}

	/**
	 * Constante que almacena la llave para recuperar el mensaje que se le
	 * muestra al usuario cuando intenta loguearse y el usuario o password son
	 * incorrectos.
	 */
	public static final String USUARIO_PASSWORD_INCORRECTO = "usuario.password.incorrecto";
	/**
	 * Constante que almacena la llave para recuperar el mensaje que se le
	 * muestra al usuario cuando intenta loguearse y los datos del usuario de la
	 * base de datos son incorrectos.
	 */
	public static final String USUARIO_BD_INCORRECTO = "usuario.bd.incorrecto";
	/**
	 * Constante que almacena la llave para recuperar el mensaje que se le
	 * muestra al usuario cuando intenta loguearse este está bloqueado.
	 */
	public static final String USUARIO_BLOQUEADO = "usuario.bloqueado";
	/**
	 * Constante para el tipo de contenido xls.
	 */
	public static final String CONTENT_TYPE_XLS = "application/xls";
	/**
	 * Constante para el tipo de contenido csv.
	 */
	public static final String CONTENT_TYPE_CSV = "application/csv";
        public static final String CONTENT_TYPE_PDF = "application/pdf";

	/**
	 * Constante para recuperar el número de registros por página.
	 */
	public static final String PAGINACION_REGISTROS_PAGINA = "general.paginacion.registros.pagina";
	/**
	 * Constante que almacena el número de minutos en que el usuario esta
	 * bloqueado.
	 */
	public static final int USUARIO_MIN_BLOQUEADO = 30;
        
        public static final String ERROR_CONEXION_BD_LOGIN = "general.error.bd.no.disponible.login";

	/**
	 * Constante para consultar el formato del correo.
	 */
	public static final String GENERAL_FORMATO_CORREO = "general.formato.correoElectronico";
	
        public static final String ERROR_CATALOGOS_CONSULTA = "catalogos.error.consulta";
        public static final String ERROR_CATALOGOS_GUARDAR = "catalogos.error.guardar";
        public static final String ERROR_CATALOGOS_ELIMINAR = "catalogos.error.eliminar";
        public static final String ERROR_CATALOGOS_FILTROS_VACIOS = "catalogos.error.filtros.vacios";
        public static final String ERROR_CATALOGOS_ELIMINAR_FILTROS_VACIOS = "catalogos.error.eliminar.filtrosVacios";
        
        public static final String ID_PACIENTE = "idPacienteSesion";
        public static final String ID_CONSULTA = "idConsultaSesion";
        public static final String ID_USUARIO = "idUsuario";
}
