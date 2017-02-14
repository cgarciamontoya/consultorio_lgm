package consultorio.lgm.excepciones;


public abstract class LGMExcepcion extends RuntimeException {
	
	private static final long serialVersionUID = -8134085117661789864L;
	
	private int numeroError; 
	public LGMExcepcion( int numeroError ){
		super ();
		this.numeroError = numeroError;
	}
	
	
	// -------------- Getters y Setters -----------------------
	public int getNumeroError() {
		return numeroError;
	}
}
