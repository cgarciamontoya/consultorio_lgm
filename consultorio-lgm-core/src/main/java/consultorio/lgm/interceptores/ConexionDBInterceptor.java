package consultorio.lgm.interceptores;

import consultorio.lgm.excepciones.LGMFabricaExcepciones;
import consultorio.lgm.excepciones.ExcepcionDAO;
import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import java.lang.reflect.Method;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.exception.JDBCConnectionException;

public class ConexionDBInterceptor implements MethodInterceptor {
    public static final String ERRORES_CONEXION[] = {"08000","08003","08006","08001","08004","08007","08P01","53300"};
    
    @Override
    public Object invoke(MethodInvocation invocacion) throws Throwable {
        try {
            //System.out.println ("---------- Ejecutando: " + invocacion.getThis().getClass().getName() + "." + invocacion.getMethod().getName() );
            return invocacion.proceed();
        } catch (Exception e) {
            Throwable excepcion = e;
            while (true){
                if ( excepcion instanceof JDBCConnectionException ){
                	//System.out.println(new Date() + "Lanzando excepción DAO_ERROR_CONEXION_BASE_DATOS");
                    throw LGMFabricaExcepciones.recuperaInstancia(). 
                            crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_ERROR_CONEXION_BASE_DATOS);
                }
                
                if ( "org.postgresql.util.PSQLException".equals( excepcion.getClass().getName() ) ){
                    Method metodo = excepcion.getClass().getMethod("getSQLState");
                    String sqlState = (String) metodo.invoke(excepcion);
                    if (esErrorConexion(sqlState)) {
                    	//System.out.println(new Date() + "Lanzando excepción DAO_ERROR_CONEXION_BASE_DATOS");
                        throw LGMFabricaExcepciones.recuperaInstancia(). 
                                crear(ExcepcionDAO.class, LGMConstantesExcepciones.DAO_ERROR_CONEXION_BASE_DATOS);
                    }
                }
                if ( excepcion.getCause() != null ){
                    excepcion = excepcion.getCause();
                }else{
                    break;
                }
            }
            throw e;
        }
    }
    
    private boolean esErrorConexion (String sqlCode){
        for (String codigo : ERRORES_CONEXION) {
            if (codigo.equals(sqlCode)) {
                return true;
            }
        }
        return false;
    }

}
