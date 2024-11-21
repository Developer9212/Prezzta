/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wilmer
 */
@NoArgsConstructor
@Data
public class dataDTO {

    private String es_socio;
    private String ogs;
    private String primer_nombre;
    private String apellidos;
    private String numero_documento;
    private String telefono_Celular;
    private String fecha_nacimiento;
    private String lugar_nacimiento;
    private String personas_viven_en_casa;
    private String jubilado;
    private String fecha_ingreso_laboral;
    private String ingresos_mensuales;
    private String estado;
    private String ciudad;
    private String direccion;
    private String codigo_postal;
    private String colonia;
    private String propiedad_vivienda;
    private String email;
    private String ultimo_estudio_cursado;
    private String telefono_auxiliar;
    private String antiguedad_domicilio;
    private String num_socio_conyugue;
    private String cant_hijos;
    private String rubro_laboral;
    private String telefono_trabajo;
    private validacionDTO monto_maximo_a_prestar;
    private String es_socio_comercial;
    private String fuente_ingresos_fijos;
    private String monto_ingresos_fijos;
    private String fuente_ingresos_variables;
    private String monto_ingresos_variables;
    private String gastos_alimentacion;
    private String gastos_pagos_servicios;
    private String fecha_ingreso_caja;
    private String total_ingresos;
    private String otros_ingresos;
    private String fuentes_otros_ingresos;
    private String total_valor_propiedades;
    private String regimen_patrimonial;
    private String numero_dependientes;
    private String telefono_recados;
    private String monto_ahorro;
    private String antiguedad_socio;
    private String parte_social;
    private String fideicomiso;
    private String historial_cooperativa;
    private String linea_credito;
    private String linea_credito_letras;
    private String aseguradora;
    private String clausulas;
    private String modalidad_pago;
    private String tipo_operacion;
    private String datos_del_poder;
    private String disponibilidad;
    private String porcentaje_capacidad;
    private String sexo;
    private String rfc;
    
    private comercioDTO comercio;
    private referenciasDTO referencias;
    private primerEmpleoDTO primer_empleo;
    private segundoEmpleoDTO segundo_empleo;
    private negocioDTO negocio;
    private gastosDTO gastos;
    private List<propiedadesDTO> propiedades;
    private conyugueDTO conyugue;
    private List<referenciasPersonalesDTO> referencias_personales;
    private referenciasLaboralesDTO referencias_laborales;
    private List<relacionesSociosDTO> relaciones_socios;
    @JsonProperty("lista_control")
    private Integer Lista_Control;
    @JsonInclude(value = Include.NON_EMPTY)
    private String nota;
    
    @JsonInclude(value = Include.NON_NULL)
    private String estado_civil;
    private String nombre_conyuge;
    private String nombre_empresa;
    private double otros_gastos;
    
}
