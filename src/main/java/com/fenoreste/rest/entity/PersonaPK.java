package com.fenoreste.rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PersonaPK implements Serializable{
    
	@Column(name="idorigen")
	private Integer idorigen;
	@Column(name="idgrupo")
	private Integer idgrupo;
	@Column(name="idsocio")
	private Integer idsocio;
	
	private static final long serialVersionUID = 1L;
}
