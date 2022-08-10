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
public class referenciasLaboralesDTO {
    private String nombre;
    private String direccion;
    private String tiempo_conocerlo;
    private String telefono;
    
    public referenciasLaboralesDTO() {
    }

    public referenciasLaboralesDTO(String nombre, String direccion, String tiempo_conocerlo, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tiempo_conocerlo = tiempo_conocerlo;
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

    public String getTiempo_conocerlo() {
        return tiempo_conocerlo;
    }

    public void setTiempo_conocerlo(String tiempo_conocerlo) {
        this.tiempo_conocerlo = tiempo_conocerlo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "referenciasLaboralesDTO{" + "nombre=" + nombre + ", direccion=" + direccion + ", tiempo_conocerlo=" + tiempo_conocerlo + ", telefono=" + telefono + '}';
    }
    
}
