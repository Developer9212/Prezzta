/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.modelos;

/**
 *
 * @author wilmer
 */
public class codeudorDTO {
    private String ciudad;
    private String direccion;
    
    public codeudorDTO(){
    }

    public codeudorDTO(String ciudad, String direccion) {
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "codeudorDTO{" + "ciudad=" + ciudad + ", direccion=" + direccion + '}';
    }
    
}
