/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author wilmer
 */
@Entity
@Table(name = "catalogo_menus")

public class CatalogoMenus implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String menu;
    private String opcion;
    private String descripcion;
    private String orden;
    private boolean activo;
    private String referencia;

    public CatalogoMenus() {
    }

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public String toString() {
		return "CatalogoMenus [menu=" + menu + ", opcion=" + opcion + ", descripcion="
				+ descripcion + ", orden=" + orden + ", activo=" + activo + ", referencia=" + referencia + "]";
	}

    
	
    
    
}
