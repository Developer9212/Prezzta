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
public class gastosDTO {
    private String alimentacion;
    private String vestido_calzado;
    private String servicios_vivienda;
    private String transporte;
    private String escuela;
    private String deudas;
    private String total_gastos;
    
    public gastosDTO() {
    }

    public gastosDTO(String alimentacion, String vestido_calzado, String servicios_vivienda, String transporte, String escuela, String deudas, String total_gastos) {
        this.alimentacion = alimentacion;
        this.vestido_calzado = vestido_calzado;
        this.servicios_vivienda = servicios_vivienda;
        this.transporte = transporte;
        this.escuela = escuela;
        this.deudas = deudas;
        this.total_gastos = total_gastos;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public String getVestido_calzado() {
        return vestido_calzado;
    }

    public void setVestido_calzado(String vestido_calzado) {
        this.vestido_calzado = vestido_calzado;
    }

    public String getServicios_vivienda() {
        return servicios_vivienda;
    }

    public void setServicios_vivienda(String servicios_vivienda) {
        this.servicios_vivienda = servicios_vivienda;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getDeudas() {
        return deudas;
    }

    public void setDeudas(String deudas) {
        this.deudas = deudas;
    }

    public String getTotal_gastos() {
        return total_gastos;
    }

    public void setTotal_gastos(String total_gastos) {
        this.total_gastos = total_gastos;
    }

    @Override
    public String toString() {
        return "gastosDTO{" + "alimentacion=" + alimentacion + ", vestido_calzado=" + vestido_calzado + ", servicios_vivienda=" + servicios_vivienda + ", transporte=" + transporte + ", escuela=" + escuela + ", deudas=" + deudas + ", total_gastos=" + total_gastos + '}';
    }
    
}
