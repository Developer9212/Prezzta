package com.fenoreste.rest.modelos;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class PagoMitrasDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idorigenp;
	private Integer idproducto;
	private Integer idauxiliar;
	private Integer idamortizacion;
	private String vence;
	private Double saldo;
	private Double abono;
	private Double iod;
	private Double iva_iod;
	private Double total_iod;
	private Double io;
	private Double iva_io;
	private Double total_io;
	private Double descuento;
	
	

}
