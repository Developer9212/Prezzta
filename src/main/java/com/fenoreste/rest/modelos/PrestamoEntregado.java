package com.fenoreste.rest.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrestamoEntregado {
	
	private String opa;
	private String estatus;
	private String monto_entregado;
	private String numero_folio_poliza;
	private String numero_pagare;
	private String proteccion_ahorro_prestamo;
	private String garantia_liquida;
	private String tipo_garantia;
	private String deposito_garantia_letras;
	private String nota;

}
