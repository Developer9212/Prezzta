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
 * @author nahum
 */
@Entity
@Table(name = "trabajo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Trabajos_Socio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer idorigen;
    private Integer idgrupo;
    private Integer idsocio;
    private String nombre;
    private String calle;
    private String numero;
    private Integer idcolonia;
    private String telefono;
    private String telefono2;
    private String ocupacion;
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    private String puesto;
    private String entrecalles;
    private Integer tipo_empleo;
    private Integer sector_laboral;
    private Integer giro_empresa;
    private Integer forma_comprobar_ing;
    @Temporal(TemporalType.DATE)
    private Date fechasalida;
    private Integer consecutivo;
    private Integer ing_mensual_bruto;
    private Integer ing_mensual_neto;
    private Integer frecuencia_ingresos;
    private Integer tipo_ocupacion;
    private Integer num_empleados;
    private Integer num_taxis;
    private Integer deducciones_deudas;
    private Integer deducciones_otros;
    private Integer actividad_economica;
    private Integer ocupacion_numero;
    private String actividad_economica_pld;
    private String ext_tel_1;
    private String ext_tel_2;

}
