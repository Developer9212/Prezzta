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
@Table(name = "negociopropio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Negociopropio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer idorigen;
    private Integer idgrupo;
    private Integer idsocio;
    private String nombre;
    private String calle;
    private String numeroext;
    private Integer idcolonia;
    private String telefono;
    private String telefono2;
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    private Integer num_empleados;
    private String entrecalles;
    private String numeroint;
    private String rfc;
    private Integer giro_empresa;
    private Double utilidad_mens;
    private String act_empresarial;
  
    
    
}
