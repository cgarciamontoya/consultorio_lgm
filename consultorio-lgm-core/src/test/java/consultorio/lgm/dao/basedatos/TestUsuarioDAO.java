/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package consultorio.lgm.dao.basedatos;

import consultorio.lgm.datos.entidades.Usuario;
import consultorio.lgm.integracion.dao.basedatos.UsuarioDAO;
import java.util.List;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 *
 * @author Usuario
 */
public class TestUsuarioDAO extends TestDAOBaseDatosBase {
    
    @Autowired
    private UsuarioDAO dao;
    
    @Test
    public void testConsultar() {
        List<Usuario> lista = dao.recuperarTodos();
        Assert.assertNotNull(lista);
        Assert.assertFalse(lista.isEmpty());
        for (Usuario usr : lista) {
            System.out.println("Usuario-Pass: " + usr.getUsuario() + " - " + usr.getContrasena());
        }
        
    }
    
    /*public void testGuardar() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Laura Fabiola");
        usuario.setApellidoPaterno("Garcia");
        usuario.setApellidoMaterno("Montoya");
        usuario.setUsuario("admin");
        usuario.setContrasena("admin");
        usuario.setIniciales("admin");
        dao.guardar(usuario);
    }*/
}
