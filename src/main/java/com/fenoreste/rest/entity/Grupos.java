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
 * @author wilmer
 */
@Entity
@Table(name="grupos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupos implements Serializable{
     
    @Id
    @Column(name="idgrupo")
    private int idgrupo;
    @Column(name="nombre")
    private String nombre;
    @Column(name="tipogrupo")
    private String tipogrupo;
    
    private static final long serialVersionUID = 1L;
    
    
}
