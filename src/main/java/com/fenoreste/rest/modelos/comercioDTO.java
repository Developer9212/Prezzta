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
public class comercioDTO {
    private String giro;
    private String fecha_comienzo;
    private String domicilio;
    private String telefono;
    private String ingreso_mensual;
    private String otros_ingresos;
    private String fuente_otros_ingresos;

    public comercioDTO() {
    }

    public comercioDTO(String giro, String fecha_comienzo, String domicilio, String telefono, String ingreso_mensual, String otros_ingresos, String fuente_otros_ingresos) {
        this.giro = giro;
        this.fecha_comienzo = fecha_comienzo;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.ingreso_mensual = ingreso_mensual;
        this.otros_ingresos = otros_ingresos;
        this.fuente_otros_ingresos = fuente_otros_ingresos;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getFecha_comienzo() {
        return fecha_comienzo;
    }

    public void setFecha_comienzo(String fecha_comienzo) {
        this.fecha_comienzo = fecha_comienzo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIngreso_mensual() {
        return ingreso_mensual;
    }

    public void setIngreso_mensual(String ingreso_mensual) {
        this.ingreso_mensual = ingreso_mensual;
    }

    public String getOtros_ingresos() {
        return otros_ingresos;
    }

    public void setOtros_ingresos(String otros_ingresos) {
        this.otros_ingresos = otros_ingresos;
    }

    public String getFuente_otros_ingresos() {
        return fuente_otros_ingresos;
    }

    public void setFuente_otros_ingresos(String fuente_otros_ingresos) {
        this.fuente_otros_ingresos = fuente_otros_ingresos;
    }

    @Override
    public String toString() {
        return "comercioDTO{" + "giro=" + giro + ", fecha_comienzo=" + fecha_comienzo + ", domicilio=" + domicilio + ", telefono=" + telefono + ", ingreso_mensual=" + ingreso_mensual + ", otros_ingresos=" + otros_ingresos + ", fuente_otros_ingresos=" + fuente_otros_ingresos + '}';
    }

}
