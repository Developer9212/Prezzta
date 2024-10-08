/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import javax.persistence.Column;
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
 * @author wilmer
 */
@Entity
@Table(name = "tipos_cuenta_bankingly")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuentasBankingly implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    private Integer idproducto;    
    @Column(name="producttypename")    
    private String productTypeName;
    @Column(name="descripcion")    
    private String descripcion;
    @Column(name="producttypeid")
    private Integer productTypeId;
   
    private static final long serialVersionUID = 1L;
    
}