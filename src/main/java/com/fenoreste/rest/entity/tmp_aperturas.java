package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="tmp_prestamos_apertura_prezzta")
@Data
public class tmp_aperturas implements Serializable{
	
	@EmbeddedId
	private PersonaPK pk;
	private Double montoalcanzado;
	private String tipoapertura;
	private Double montorenovar;
	private Double adispersar;
	private Double atrasado;
	private Integer idproducto;
	private String opaactivo;
	private Integer idorigenp;
	private Double  gastos_pagar; 
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	private static final long serialVersionUID = 1L;

}
