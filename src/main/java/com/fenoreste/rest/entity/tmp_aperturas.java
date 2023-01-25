package com.fenoreste.rest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tmp_prestamos_apertura_prezzta")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class tmp_aperturas implements Serializable{
	
	@Id
	private Integer idorigen;
	private Integer idgrupo;
	private Integer idsocio;
	private Double montoalcanzado;
	private String tipoapertura;
	private Double montorenovar;
	private Double adispersar;
	private Double atrasado;
	private Integer idproducto;
	private String opaactivo;
	private Integer idorigenp;
	private Double  gastos_pagar; 
	
	private static final long serialVersionUID = 1L;

}
