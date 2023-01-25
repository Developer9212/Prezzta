package com.fenoreste.rest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Productos implements Serializable{

	@Id
	private Integer idproducto;
	private String nombre;
	private Integer idorigen;
	private String cuentaaplica;
	private String cuentavencida;
	private String cuentaidncres;
	private String cuentaiva;
	private String cuentarc;
	private String cuentari;
	private Integer tipoproducto;
	private Integer tipofinalidad;
	private String activo;
	
	private static final long serialVersionUID = 1L;
		
}
