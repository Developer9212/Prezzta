/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Elliot
 */

@Entity
@Table(name = "amortizaciones")
@Cacheable(false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Amortizacion implements Serializable {   

	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idorigenp")
    private Integer idorigenp;
    @Column(name="idproducto")
    private Integer idproducto;
    @Column(name="idauxiliar")
    private Integer idauxiliar; 
    @Column(name="idamortizacion")
    private Integer idamortizacion;
    @Column(name = "vence")
    private String vence;
    @Column(name = "abono")
    private BigDecimal abono;
    @Column(name = "io")
    private BigDecimal io;
    @JsonIgnore
    @Column(name = "abonopag")
    private BigDecimal abonopag;
    @JsonIgnore
    @Column(name = "iopag")
    private BigDecimal iopag;
    @JsonIgnore
    @Column(name = "bonificado")
    private Boolean bonificado;
    @JsonIgnore
    @Column(name = "pagovariable")
    private Boolean pagovariable;
    @JsonIgnore
    @Column(name = "todopag")
    private Boolean todopag;
    @JsonIgnore
    @Column(name = "atiempo")
    private Boolean atiempo;
    @JsonIgnore
    @Column(name = "bonificacion")
    private BigDecimal bonificacion;
    @JsonIgnore
    @Column(name = "anualidad")
    private BigDecimal anualidad;
    @JsonIgnore
    @Column(name = "diasvencidos")
    private Integer diasvencidos;

    
}
