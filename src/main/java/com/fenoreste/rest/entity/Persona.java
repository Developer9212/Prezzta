/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Elliot
 */

@Entity
@Table(name = "personas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Persona implements Serializable {

	@EmbeddedId
	private PersonaPK pk;
	@Column(name = "calle",updatable = false)
	private String calle;
	@Column(name = "numeroext")
	private String numeroext;
	@Column(name = "numeroint")
	private String numeroint;
	@Column(name = "entrecalles",updatable = false)
	private String entrecalles;
	@Column(name = "fechanacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;
	@Column(name = "lugarnacimiento",updatable = false)
	private String lugarnacimiento;
	@Column(name = "efnacimiento")
	private Integer efnacimiento;
	@Column(name = "sexo")
	private Short sexo;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "telefonorecados")
	private String telefonorecados;
	@Column(name = "listanegra")
	private Boolean listanegra;
	@Column(name = "estadocivil")
	private Short estadocivil;
	@Column(name = "idcoop")
	private String idcoop;
	@Column(name = "idsector")
	private Integer idsector;
	@Column(name = "estatus")
	private Boolean estatus;
	@Column(name = "aceptado")
	private Boolean aceptado;
	@Column(name = "fechaingreso")
	@Temporal(TemporalType.DATE)
	private Date fechaingreso;
	@Column(name = "fecharetiro")
	@Temporal(TemporalType.DATE)
	private Date fecharetiro;
	@Column(name = "fechaciudad")
	@Temporal(TemporalType.DATE)
	private Date fechaciudad;
	@Column(name = "regimen_mat")
	private Short regimenMat;
	@Column(name = "nombre",updatable = false)
	private String nombre;
	@Column(name = "medio_inf")
	private Short medioInf;
	@Column(name = "requisitos")
	private Integer requisitos;
	@Column(name = "appaterno",updatable = false)	
	private String appaterno;
	@Column(name = "apmaterno",updatable = false)
	private String apmaterno;
	@Column(name = "nacionalidad")
	private Short nacionalidad;
	@Column(name = "grado_estudios")
	private Short gradoEstudios;
	@Column(name = "categoria")
	private Short categoria;
	@Column(name = "rfc")
	private String rfc;
	@Column(name = "curp")
	private String curp;
	@Column(name = "email",updatable = false)
	private String email;
	@Column(name = "razon_social")
	private String razonSocial;
	@Column(name = "causa_baja")
	private Integer causaBaja;
	@Column(name = "nivel_riesgo")
	private Short nivelRiesgo;
	@Column(name = "celular")
	private String celular;
	@Column(name = "rfc_valido")
	private Boolean rfcValido;
	@Column(name = "curp_valido")
	private Boolean curpValido;
	@Column(name = "idcolonia")
	private Integer idcolonia;

	private static final long serialVersionUID = 1L;

}
