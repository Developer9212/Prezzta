package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="finalidades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finalidad implements Serializable {

	@Id
	private Integer idfinalidad;
	private String descripcion;
	
	private static final long serialVersionUID = 1L;
	

}
