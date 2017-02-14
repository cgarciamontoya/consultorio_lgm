/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.web.filtro;

import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.servicios.UsuarioServicio;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * Autor: cgarcia.
 * Fecha: 23/01/2014
 */
public class FiltroAutorizacionPantalla implements Filter {

    /**
     * Pantalla de error.
     */
    private static final String PAGINA_ERROR =
            "/web-pages/error/acceso-denegado.jsf";
    /**
     * Servicio de usuario.
     */
    private UsuarioServicio usuarioServicio;
    /**
     * Configuracion de filtro.
     */
    private FilterConfig filterConfig;
    /**
     * Tamaño del arreglo de excepciones.
     */
    private static final int SIZE_ARREGLO = 6;
    /**
     * Posicion 0.
     */
    private static final int POS_ARREGLO_0 = 0;
    /**
     * Posicion 1.
     */
    private static final int POS_ARREGLO_1 = 1;
    /**
     * Posicion 2.
     */
    private static final int POS_ARREGLO_2 = 2;
    /**
     * Posicion 3.
     */
    private static final int POS_ARREGLO_3 = 3;
    /**
     * Posicion 4.
     */
    private static final int POS_ARREGLO_4 = 4;
    
    /**
     * Posicion 5.
     */
    private static final int POS_ARREGLO_5 = 5;
    
    /**
     * Arreglo de excepciones de pantallas.
     */
    private static String[] excepcionesUrl;
    static {
        excepcionesUrl = new String[SIZE_ARREGLO];
        excepcionesUrl[POS_ARREGLO_0] =
                "/web-pages/error/recurso-no-encontrado.jsf";
        excepcionesUrl[POS_ARREGLO_1] =
                "/web-pages/general/inicio.jsf";
        excepcionesUrl[POS_ARREGLO_2] =
                "/web-pages/error/acceso-denegado.jsf";
        excepcionesUrl[POS_ARREGLO_3] =
                "/web-pages/seguridad/login.jsf";
        excepcionesUrl[POS_ARREGLO_4] =
                "/web-pages/seguridad/login-error.jsf";
        excepcionesUrl[POS_ARREGLO_5] =
                "/web-pages/error/sesion-expirada.jsf";
        
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        filterConfig.getServletContext().
                log("FiltroAutorizacionPantalla inicializado");
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest != null) {
            String path = httpRequest.getServletPath();
            if (path.contains("/web-pages/")
                    && !excepcionValida(path)) {
                String username = (String) httpRequest.getSession().
                        getAttribute("nombreUsuario");
                //Si username es null, quiere decir que no existe una sesión válida, en ese caso redireccionamos a login.
                if ( username == null){
                	 RequestDispatcher requestDispatcher = request.getRequestDispatcher(excepcionesUrl[POS_ARREGLO_5]);
                     requestDispatcher.forward(request, response);
                     return;
                }
                Usuario usuario = usuarioServicio.recuperarUsuario(username);
                if (usuario == null) {
                        RequestDispatcher requestDispatcher =
                                request.getRequestDispatcher(PAGINA_ERROR);
                        requestDispatcher.forward(request, response);
                        return;
                }
            }
        }
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    /**
     * Metodo para validar si es una url valida.
     * @param path Url solicitada.
     * @return True si es valida, false si no lo es.
     */
    private boolean excepcionValida(String path) {
        boolean valida = false;
        for (int i = 0; i < excepcionesUrl.length; i++) {
            if (path.equals(excepcionesUrl[i])) {
                valida = true;
                break;
            }
        }
        return valida;
    }

    @Override
    public void destroy() {
        this.filterConfig.getServletContext().
                log("FiltroAutorizacionPantalla destruido");
    }
    
    public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

}
