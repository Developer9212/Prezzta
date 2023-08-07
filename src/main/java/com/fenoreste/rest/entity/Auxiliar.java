/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


/**
 *
 * @author Elliot
 */
@Entity
@Table(name = "auxiliares")
@Data
public class Auxiliar{   
	
    @EmbeddedId
    private AuxiliarPK pk;
    @Column(name = "idorigen")
    private Integer idorigen;
    @Column(name = "idgrupo")
    private Integer idgrupo;
    @Column(name = "idsocio")
    private Integer idsocio;
    @Column(name = "fechaape")
    @Temporal(TemporalType.DATE)
    private Date fechaape;
    @Column(name = "elaboro")
    private Integer elaboro;
    @Column(name = "autorizo")
    private Integer autorizo;
    @Column(name = "estatus")
    private Short estatus;
    @Column(name = "tasaio")
    private BigDecimal tasaio;
    @Column(name = "tasaim")
    private BigDecimal tasaim;
    @Column(name = "tasaiod")
    private BigDecimal tasaiod;
    @Column(name = "montosolicitado")
    private BigDecimal montosolicitado;
    @Column(name = "montoautorizado")
    private BigDecimal montoautorizado;
    @Column(name = "montoprestado")
    private BigDecimal montoprestado;
    @Column(name = "idfinalidad")
    private Integer idfinalidad;
    @Column(name = "plazo")
    private short plazo;
    @Column(name = "periodoabonos")
    private short periodoabonos;
    @Column(name = "saldoinicial")
    private BigDecimal saldoinicial;
    @Column(name = "saldo")
    private BigDecimal saldo;
    @Column(name = "io")
    private BigDecimal io;
    @Column(name = "idnc")
    private BigDecimal idnc;
    @Column(name = "ieco")
    private BigDecimal ieco;
    @Column(name = "im")
    private BigDecimal im;
    @Column(name = "iva")
    private BigDecimal iva;
    @Column(name = "fechaactivacion")
    @Temporal(TemporalType.DATE)
    private Date fechaactivacion;
    @Column(name = "fechaumi")
    @Temporal(TemporalType.DATE)
    private Date fechaumi;
    @Column(name = "idnotas")
    private String idnotas;
    @Column(name = "tipoprestamo")
    private short tipoprestamo;
    @Column(name = "cartera")
    private String cartera;
    @Column(name = "contaidnc")
    private BigDecimal contaidnc;
    @Column(name = "contaieco")
    private BigDecimal contaieco;
    @Column(name = "reservaidnc")
    private BigDecimal reservaidnc;
    @Column(name = "reservacapital")
    private BigDecimal reservacapital;
    @Column(name = "tipoamortizacion")
    private Short tipoamortizacion;
    @Column(name = "saldodiacum")
    private BigDecimal saldodiacum;
    @Column(name = "fechacartera")
    @Temporal(TemporalType.DATE)
    private Date fechacartera;
    @Column(name = "fechauma")
    @Temporal(TemporalType.DATE)
    private Date fechauma;
    @Column(name = "ivaidnccalc")
    private BigDecimal ivaidnccalc;
    @Column(name = "ivaidncpag")
    private BigDecimal ivaidncpag;
    @Column(name = "tiporeferencia")
    private Short tiporeferencia;
    @Column(name = "calificacion")
    private Integer calificacion;
    @Column(name = "pagodiafijo")
    private Short pagodiafijo;
    @Column(name = "iodif")
    private BigDecimal iodif;
    @Column(name = "garantia")
    private BigDecimal garantia;
    @Column(name = "saldodiacummi")
    private BigDecimal saldodiacummi;
    @Column(name = "comision")
    private BigDecimal comision;
    @Column(name = "fechasdiacum")
    @Temporal(TemporalType.DATE)
    private Date fechasdiacum;
    @Column(name = "prc_comision")
    private BigDecimal prcComision;
    @Column(name = "sobreprecio")
    private BigDecimal sobreprecio;
    @Column(name = "comision_np")
    private BigDecimal comisionNp;
    @Column(name = "pagos_dia_ultimo")
    private Boolean pagosDiaUltimo;
    @Column(name = "tipo_dv")
    private Integer tipoDv;
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    @Column(name = "fecha_autorizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAutorizacion;
    @Column(name = "idncm")
    private BigDecimal idncm;
    @Column(name = "iecom")
    private BigDecimal iecom;
    @Column(name = "reservaidncm")
    private BigDecimal reservaidncm;

	
}
