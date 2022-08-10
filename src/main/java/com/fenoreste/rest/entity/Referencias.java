/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author nahum
 */

@Entity
@Table(name = "referencias")
public class Referencias implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "idorigen")
    private Integer idorigen;
    @Column(name = "idgrupo")
    private Integer idgrupo;
    @Column(name = "idsocio")
    private Integer idsocio;
    @Column(name = "tiporeferencia")
    private Integer tiporeferencia;
    @Column(name = "referencia")
    private String referencia;
    @Column(name = "idorigenr")
    private Integer idorigenr;
    @Column(name = "idgrupor")
    private Integer idgrupor;
    @Column(name = "idsocior")
    private Integer idsocior;
    
    public Referencias() {
    }

	public Integer getIdorigen() {
		return idorigen;
	}

	public void setIdorigen(Integer idorigen) {
		this.idorigen = idorigen;
	}

	public Integer getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(Integer idgrupo) {
		this.idgrupo = idgrupo;
	}

	public Integer getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(Integer idsocio) {
		this.idsocio = idsocio;
	}

	public Integer getTiporeferencia() {
		return tiporeferencia;
	}

	public void setTiporeferencia(Integer tiporeferencia) {
		this.tiporeferencia = tiporeferencia;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getIdorigenr() {
		return idorigenr;
	}

	public void setIdorigenr(Integer idorigenr) {
		this.idorigenr = idorigenr;
	}

	public Integer getIdgrupor() {
		return idgrupor;
	}

	public void setIdgrupor(Integer idgrupor) {
		this.idgrupor = idgrupor;
	}

	public Integer getIdsocior() {
		return idsocior;
	}

	public void setIdsocior(Integer idsocior) {
		this.idsocior = idsocior;
	}

	@Override
	public String toString() {
		return "Referencias [idorigen=" + idorigen + ", idgrupo=" + idgrupo + ", idsocio=" + idsocio
				+ ", tiporeferencia=" + tiporeferencia + ", referencia=" + referencia + ", idorigenr=" + idorigenr
				+ ", idgrupor=" + idgrupor + ", idsocior=" + idsocior + "]";
	}

    
}
