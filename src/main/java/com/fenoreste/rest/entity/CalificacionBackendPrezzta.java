package com.fenoreste.rest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="prezzta_calificacion_backend")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalificacionBackendPrezzta implements Serializable {
	
	@Id
	private Integer idauxiliar;
	private Integer idproducto;
	private Integer idorigenp;
	private String confirmacion_backend;
	
	private static final long serialVersionUID = 1L;

}
