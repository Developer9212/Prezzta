package com.fenoreste.rest.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="csn_vac_ingresos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CsnVacIngreso implements Serializable {
     
	 @EmbeddedId
	 private PersonaPK pk; 
	 private Integer idempleado; 
	 private Integer idusuario; 
	 private Integer idpuesto; 
	 private String cuenta; 
	 private String nombre;
	 private String apellido_paterno; 
	 private String apellido_materno; 
	 private boolean activo; 

	 private static final long serialVersionUID = 1L;
	 
}
