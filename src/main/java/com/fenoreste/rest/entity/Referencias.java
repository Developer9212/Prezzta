/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nahum
 */

@Entity
@Table(name = "referencias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Referencias implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "idorigen")
    private Integer idorigen;
    @Column(name = "idgrupo")
    private Integer idgrupo;
    @Column(name = "idsocio")
    private Integer idsocio;
    @Column(name = "tiporeferencia")
    private Integer tiporeferencia;
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "idorigenr")
    private Integer idorigenr;
    @Column(name = "idgrupor")
    private Integer idgrupor;
    @Column(name = "idsocior")
    private Integer idsocior;
    
}
