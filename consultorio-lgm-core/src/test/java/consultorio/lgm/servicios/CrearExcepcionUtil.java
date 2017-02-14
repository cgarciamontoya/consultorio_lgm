package consultorio.lgm.servicios;

import javax.persistence.PersistenceException;

import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

/**
 * Utilería para generar excepciones utilizadas en los mock de las pruebas
 * unitarias.
 * 
 * @author Daniel Alejandro López Hernández
 * 
 */
public class CrearExcepcionUtil {

    private static CrearExcepcionUtil crearExcepcionUtil = new CrearExcepcionUtil();

    private CrearExcepcionUtil() {
    }

    public static CrearExcepcionUtil getInstance() {
        return crearExcepcionUtil;
    }

    /**
     * Método utilizado para crear una PersistenceException con una
     * PSQLException anidada
     * 
     * @param sqlState
     *            Contiene el código de error que queremos que contenga la
     *            PSQLException
     * @return PersistenceException con la PSQLException anidada
     */
    public PersistenceException crearPersistenceExceptionConSQLState(String sqlState) {

        PersistenceException pe = new PersistenceException();
        PSQLState state = new PSQLState(sqlState);

        PSQLException psql = new PSQLException("error", state);
        Throwable cause = psql;
        pe.initCause(cause);
        return pe;
    }

}
