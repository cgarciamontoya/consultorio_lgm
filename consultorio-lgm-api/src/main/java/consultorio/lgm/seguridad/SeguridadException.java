package consultorio.lgm.seguridad;

public class SeguridadException extends RuntimeException {
	private static final long serialVersionUID = -7273066704404271244L;

	public SeguridadException (String mensaje){
		super(mensaje);
	}
}
