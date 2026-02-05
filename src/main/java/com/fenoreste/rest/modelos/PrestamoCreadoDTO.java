package com.fenoreste.rest.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
	private List<CuotaVO>cuotas;	
	private String nota;
	
	private Double monto_comision;
	private Double tasa_moratoria;
	@JsonInclude(value = Include.NON_NULL)
	private String abonos;
	@JsonInclude(value = Include.NON_NULL)
	private String legalario_sucursal_opa;
	
	//integrado el 06/01/2025 happy new year Nahu y programa mejor bro
	private String finalidad;
	
}
