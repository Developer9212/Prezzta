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
public class negocioDTO {
    private String ingresos;
    private String compras;
    private String pago_sueldos;
    private String pago_renta;
    private String pago_creditos;
    private String otros;
    private String total_negocio;
    private String horario_dias_laborables;
    private String domicilio;
    private String fuentes_otros_negocios;
    
    public negocioDTO() {
    }

    public negocioDTO(String ingresos, String compras, String pago_sueldos, String pago_renta, String pago_creditos, String otros, String total_negocio, String horario_dias_laborables, String domicilio, String fuentes_otros_negocios) {
        this.ingresos = ingresos;
        this.compras = compras;
        this.pago_sueldos = pago_sueldos;
        this.pago_renta = pago_renta;
        this.pago_creditos = pago_creditos;
        this.otros = otros;
        this.total_negocio = total_negocio;
        this.horario_dias_laborables = horario_dias_laborables;
        this.domicilio = domicilio;
        this.fuentes_otros_negocios = fuentes_otros_negocios;
    }

    public String getIngresos() {
        return ingresos;
    }

    public void setIngresos(String ingresos) {
        this.ingresos = ingresos;
    }

    public String getCompras() {
        return compras;
    }

    public void setCompras(String compras) {
        this.compras = compras;
    }

    public String getPago_sueldos() {
        return pago_sueldos;
    }

    public void setPago_sueldos(String pago_sueldos) {
        this.pago_sueldos = pago_sueldos;
    }

    public String getPago_renta() {
        return pago_renta;
    }

    public void setPago_renta(String pago_renta) {
        this.pago_renta = pago_renta;
    }

    public String getPago_creditos() {
        return pago_creditos;
    }

    public void setPago_creditos(String pago_creditos) {
        this.pago_creditos = pago_creditos;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getTotal_negocio() {
        return total_negocio;
    }

    public void setTotal_negocio(String total_negocio) {
        this.total_negocio = total_negocio;
    }

    public String getHorario_dias_laborables() {
        return horario_dias_laborables;
    }

    public void setHorario_dias_laborables(String horario_dias_laborables) {
        this.horario_dias_laborables = horario_dias_laborables;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getFuentes_otros_negocios() {
        return fuentes_otros_negocios;
    }

    public void setFuentes_otros_negocios(String fuentes_otros_negocios) {
        this.fuentes_otros_negocios = fuentes_otros_negocios;
    }

    @Override
    public String toString() {
        return "negocioDTO{" + "ingresos=" + ingresos + ", compras=" + compras + ", pago_sueldos=" + pago_sueldos + ", pago_renta=" + pago_renta + ", pago_creditos=" + pago_creditos + ", otros=" + otros + ", total_negocio=" + total_negocio + ", horario_dias_laborables=" + horario_dias_laborables + ", domicilio=" + domicilio + ", fuentes_otros_negocios=" + fuentes_otros_negocios + '}';
    }
    
}
