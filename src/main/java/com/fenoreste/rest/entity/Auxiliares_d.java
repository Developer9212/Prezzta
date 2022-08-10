/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Elliot
 */

@Entity
@Table(name = "auxiliares_d")
@Cacheable(false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Auxiliares_d{

    @Id
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name="idorigenp")
    private Integer idorigenp;
    @Column(name="idproducto")
    private Integer idproducto;
    @Column(name="idauxiliar")
    private Integer idauxiliar;
    @Column(name = "cargoabono")
    private Short cargoabono;
    @Column(name = "monto")
    private BigDecimal monto;
    @Column(name = "montoio")
    private BigDecimal montoio;
    @Column(name = "montoim")
    private BigDecimal montoim;
    @Column(name = "montoiva")
    private BigDecimal montoiva;
    @Column(name = "idorigenc")
    private Integer idorigenc;
    @Column(name = "periodo")
    private String periodo;
    @Column(name = "idtipo")
    private Short idtipo;
    @Column(name = "idpoliza")
    private Integer idpoliza;
    @Column(name = "tipomov")
    private Short tipomov;
    @Column(name = "saldoec")
    private BigDecimal saldoec;
    @Column(name = "transaccion")
    private Integer transaccion;
    @Column(name = "montoivaim")
    private BigDecimal montoivaim;
    @Column(name = "efectivo")
    private BigDecimal efectivo;
    @Column(name = "diasvencidos")
    private int diasvencidos;
    @Column(name = "montovencido")
    private BigDecimal montovencido;
    @Column(name = "ticket")
    private Integer ticket;
    @Column(name = "montoidnc")
    private BigDecimal montoidnc;
    @Column(name = "montoieco")
    private BigDecimal montoieco;
    @Column(name = "montoidncm")
    private BigDecimal montoidncm;
    @Column(name = "montoiecom")
    private BigDecimal montoiecom;
  
    
    

}

