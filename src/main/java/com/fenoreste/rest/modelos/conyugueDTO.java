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
public class conyugueDTO {
    private String edad;
    private String direccion;
    private String ocupacion;
    private String cp;
    private String ciudad;
    private String estado;
    private String lugar_trabajo;
    private String antiguedad;
    private String domicilio_empleo;
    private String telefono_empleo;
    private String historial;
    
    public conyugueDTO() {
    }

    public conyugueDTO(String edad, String direccion, String ocupacion, String cp, String ciudad, String estado, String lugar_trabajo, String antiguedad, String domicilio_empleo, String telefono_empleo, String historial) {
        this.edad = edad;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
        this.cp = cp;
        this.ciudad = ciudad;
        this.estado = estado;
        this.lugar_trabajo = lugar_trabajo;
        this.antiguedad = antiguedad;
        this.domicilio_empleo = domicilio_empleo;
        this.telefono_empleo = telefono_empleo;
        this.historial = historial;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLugar_trabajo() {
        return lugar_trabajo;
    }

    public void setLugar_trabajo(String lugar_trabajo) {
        this.lugar_trabajo = lugar_trabajo;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getDomicilio_empleo() {
        return domicilio_empleo;
    }

    public void setDomicilio_empleo(String domicilio_empleo) {
        this.domicilio_empleo = domicilio_empleo;
    }

    public String getTelefono_empleo() {
        return telefono_empleo;
    }

    public void setTelefono_empleo(String telefono_empleo) {
        this.telefono_empleo = telefono_empleo;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    @Override
    public String toString() {
        return "conyugueDTO{" + "edad=" + edad + ", direccion=" + direccion + ", ocupacion=" + ocupacion + ", cp=" + cp + ", ciudad=" + ciudad + ", estado=" + estado + ", lugar_trabajo=" + lugar_trabajo + ", antiguedad=" + antiguedad + ", domicilio_empleo=" + domicilio_empleo + ", telefono_empleo=" + telefono_empleo + ", historial=" + historial + '}';
    }
    
}
