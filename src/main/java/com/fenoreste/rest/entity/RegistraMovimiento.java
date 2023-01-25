package com.fenoreste.rest.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wilmer
 */
@Entity
@Table(name = "bankingly_movimientos_ca")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistraMovimiento implements Serializable {
    @Id
    @Column(name = "idauxiliar")
    private Integer idauxiliar;
    @Column(name="idproducto")
    private Integer idproducto;
    @Column(name="idorigenp")
    private Integer idorigenp;        
    @Column(name = "fecha")
    private Timestamp fecha;
    @Column(name = "idusuario")
    private Integer idusuario;
    @Column(name = "sesion")
    private String sesion;
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "idorigen")
    private Integer idorigen;
    @Column(name = "idgrupo")
    private Integer idgrupo;
    @Column(name = "idsocio")
    private Integer idsocio;
    @Column(name = "cargoabono")
    private Integer cargoabono;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "idcuenta")
    private String idcuenta;
    @Column(name = "iva")
    private Double iva;
    @Column(name = "tipo_amort")
    private Integer tipo_amort;   
    @Column(name = "sai_aux")
    private String sai_aux;

	private static final long serialVersionUID = 1L;

    
}
