package com.fenoreste.rest.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Cacheable(false)
@Entity
@Table(name = "comision_prezzta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComisionPrezzta implements Serializable{
	
	@Id
	private Integer idproducto;
	private String descripcion;
	private Double porcentaje_comision;
	
	private static final long serialVersionUID = 1L;

}
