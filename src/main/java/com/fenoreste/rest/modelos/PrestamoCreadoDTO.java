package com.fenoreste.rest.modelos;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fenoreste.rest.entity.Amortizacion;

import lombok.Data;
@Data
@JsonPropertyOrder
@JsonIgnoreProperties("nota")
public class PrestamoCreadoDTO {	 
	private String opa;
	private String numero_producto;
	private String idauxiliar;	
	private String idorigenp;
	private String clasificacion_cartera;
	private String folio_prestamo;
	private String reca_completo;
	private String reca_recortado;
	private String cat;
	private avalDTO aval;
	private codeudorDTO codeudor;
	private String fecha_vencimiento_pagare;	
	private String tarjetaDebito;
	private String id_solicitud_siscore;
	private DetallesSiscore resumen_calificacion_siscore;
	@JsonInclude(value = Include.NON_NULL)
	private String tasa_anual;
	private List<Amortizacion>cuotas;	
	private String nota;
	
}
