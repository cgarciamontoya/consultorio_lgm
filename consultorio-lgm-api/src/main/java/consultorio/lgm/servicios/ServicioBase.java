package consultorio.lgm.servicios;

import consultorio.lgm.excepciones.LGMConstantesExcepciones;
import consultorio.lgm.excepciones.LGMFabricaExcepciones;
import consultorio.lgm.excepciones.ExcepcionServicio;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public abstract class ServicioBase {
    public static ThreadLocal<String> usuarioSistema ;//= new ThreadLocal <String> ();
    protected LGMFabricaExcepciones fabricaExcepciones = LGMFabricaExcepciones.recuperaInstancia();
    public String obtenerUsuario(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null){
            return "SISTEMA";            
        }
        return auth.getName();
    }
    
    public void validaDatosNulos (Object objeto , String ... metodos){
        java.lang.reflect.Method metodo;
        for (String metodoStr : metodos){
            try {
                metodo = objeto.getClass().getMethod(metodoStr);
                if(metodo.invoke(objeto) == null) {
                    throw fabricaExcepciones.crear(ExcepcionServicio.class , 
                            LGMConstantesExcepciones.SERVICIO_VALORES_NULL);
                }
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}