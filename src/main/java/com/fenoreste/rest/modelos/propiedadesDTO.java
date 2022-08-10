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
public class propiedadesDTO {
    private String valor;
    private String adeudo;
    private String saldo;
    private String ubicacion;
    
    public propiedadesDTO() {
    }

    public propiedadesDTO(String valor, String adeudo, String saldo, String ubicacion) {
        this.valor = valor;
        this.adeudo = adeudo;
        this.saldo = saldo;
        this.ubicacion = ubicacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getAdeudo() {
        return adeudo;
    }

    public void setAdeudo(String adeudo) {
        this.adeudo = adeudo;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "propiedadesDTO{" + "valor=" + valor + ", adeudo=" + adeudo + ", saldo=" + saldo + ", ubicacion=" + ubicacion + '}';
    }
    
}
