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
public class referenciasDTO {
    private String nombre;
    private String direccion;
    private String parentesco;
    private String telefono;
    
    public referenciasDTO() {
    }

    public referenciasDTO(String nombre, String direccion, String parentesco, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.parentesco = parentesco;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "referenciasDTO{" + "nombre=" + nombre + ", direccion=" + direccion + ", parentesco=" + parentesco + ", telefono=" + telefono + '}';
    }
    
}
