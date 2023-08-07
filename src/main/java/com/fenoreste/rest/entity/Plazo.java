package com.fenoreste.rest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "plazos")
@Data
public class Plazo {
   
	@Id
	private Integer id;
	private Double montominimo;
	private Double montomaximo;
	private String plazos;
	
}
