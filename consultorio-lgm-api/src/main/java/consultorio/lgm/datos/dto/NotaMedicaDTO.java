/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultorio.lgm.datos.dto;

import java.io.ByteArrayInputStream;

/**
 *
 * @author USUARIO
 */
public class NotaMedicaDTO {
    private ByteArrayInputStream archivo;
    private String nombreArchivo;
    private String urlNotaMedica;

    public ByteArrayInputStream getArchivo() {
        return archivo;
    }

    public void setArchivo(ByteArrayInputStream archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getUrlNotaMedica() {
        return urlNotaMedica;
    }

    public void setUrlNotaMedica(String urlNotaMedica) {
        this.urlNotaMedica = urlNotaMedica;
    }

}
