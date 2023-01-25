/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "municipios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idmunicipio")
    private Integer idmunicipio;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "idestado")
    private Integer idestado;
    @Column(name = "poblacion")
    private Integer poblacion;
    @Column(name = "localidad_siti")
    private BigInteger localidad_siti;
    @Column(name = "de_cp")
    private String de_cp;
    @Column(name = "a_cp")
    private String a_cp;

       
}
