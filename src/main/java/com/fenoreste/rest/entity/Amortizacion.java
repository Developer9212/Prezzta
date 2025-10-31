/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Elliot
 */

@Entity
@Table(name = "amortizaciones")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Amortizacion implements Serializable {   
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idorigenp;
    private Integer idproducto;
    private Integer idauxiliar; 
    private Integer idamortizacion;
    private String vence;
    private BigDecimal abono;
    private BigDecimal io;
    private BigDecimal abonopag;
    private BigDecimal iopag;
    private Boolean bonificado;
    private Boolean pagovariable;
    private Boolean todopag;
    private Boolean atiempo;
    private BigDecimal bonificacion;
    private BigDecimal anualidad;   
    private Integer diasvencidos;
    
    private static final long serialVersionUID = 1L;
    
}
