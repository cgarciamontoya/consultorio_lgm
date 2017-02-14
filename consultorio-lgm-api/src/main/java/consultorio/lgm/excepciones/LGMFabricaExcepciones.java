package consultorio.lgm.excepciones;

import javax.persistence.PersistenceException;

import org.postgresql.util.PSQLException;

public class LGMFabricaExcepciones {

    private static LGMFabricaExcepciones instancia = new LGMFabricaExcepciones();

    private LGMFabricaExcepciones() {

    }

    public static LGMFabricaExcepciones recuperaInstancia() {
        return instancia;
    }

    public <T extends LGMExcepcion> T crear(Class<T> clase, int numeroError) {
        try {
            return clase.getConstructor(int.class).newInstance(numeroError);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public <T extends LGMExcepcion> T crear(Class<T> clase, RuntimeException runtimeException) {

        String claveError;
        int numeroError = 0;

        try {
            if (runtimeException instanceof PersistenceException) {
                Throwable throwable = runtimeException;
                while (throwable != null && !(throwable instanceof PSQLException)) {
                    throwable = throwable.getCause();
                }
                if (throwable instanceof PSQLException) {
                    claveError = ((PSQLException) throwable).getSQLState();
                    if (claveError.equals(LGMConstantesExcepciones.DAO_CODIGO_ERROR_INTERRUPCION_BD)) {
                        numeroError = LGMConstantesExcepciones.DAO_ERROR_INTERRUPCION_BD;
                    } else if (claveError.equals(LGMConstantesExcepciones.DAO_CODIGO_ERROR_VIOLACION_INTEGRIDAD)) {
                        numeroError = LGMConstantesExcepciones.DAO_ERROR_ERROR_VIOLACION_INTEGRIDAD;
                    } else {
                        numeroError = LGMConstantesExcepciones.DAO_ERROR_BD;
                    }
                }
            }

            return clase.getConstructor(int.class).newInstance(numeroError);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
