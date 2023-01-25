/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wilmer
 */
@Entity
@Table(name = "socioeconomicos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socioeconomicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer idorigen;
    private Integer idgrupo;
    private Integer idsocio;
    private Integer estatusvivienda;
    private Double mensualidadvivienda;
    @Temporal(TemporalType.DATE)
    private Date fechahabitacion;
    private Integer dependientes;
    private Double ingresosordinarios;
    private Double ingresosextraordinarios;
    private Double gastosordinarios;
    private Double gastosextraordinarios;
    private String propietariovivienda;
    private Integer tipoingresos;
    private Double valorpropiedad;
    private Integer dependientes_menores;
    private Integer banco;
    private String cuenta;
    private String clabe;
    private Integer gastos_tipo1;
    private Integer gastos_tipo2;
    private Integer gastos_tipo3;
    private Integer gastos_tipo4;
    private Integer gastos_tipo5;
    private Integer gastos_tipo6;
    private String cuenta_int;
    private String clabe_int;
    private Integer bancodom;
    private String cuentadom;
    private Integer tipo_cuentadom;
    private Boolean bloqueo_info_bancaria;


}
