package com.fenoreste.rest.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "referenciasp")
@Data
public class Referenciasp implements Serializable {
    
	@Id	
	private Integer idorigenp;
	private Integer idproducto;
	private Integer idauxiliar; 
	private String referencia;
	private Integer idorigenpr;
	private Integer idproductor;
	private Integer idauxiliarr;
	
    private static final long serialVersionUID = 1L;
}
