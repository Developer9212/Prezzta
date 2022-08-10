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
public class relacionesSociosDTO {
    private String nombre;
    private String numero_socio;
    private String parentesco;
    
    public relacionesSociosDTO() {
    }

    public relacionesSociosDTO(String nombre, String numero_socio, String parentesco) {
        this.nombre = nombre;
        this.numero_socio = numero_socio;
        this.parentesco = parentesco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero_socio() {
        return numero_socio;
    }

    public void setNumero_socio(String numero_socio) {
        this.numero_socio = numero_socio;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public String toString() {
        return "relacionesSociosDTO{" + "nombre=" + nombre + ", numero_socio=" + numero_socio + ", parentesco=" + parentesco + '}';
    }
    
}
