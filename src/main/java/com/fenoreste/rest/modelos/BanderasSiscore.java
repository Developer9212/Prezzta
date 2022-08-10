package com.fenoreste.rest.modelos;

import java.util.List;

public class BanderasSiscore {

	private String id;
	private String tipoBandera;
	List<BanderasObjeto>bandera;
	
	
	public BanderasSiscore() {
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTipoBandera() {
		return tipoBandera;
	}


	public void setTipoBandera(String tipoBandera) {
		this.tipoBandera = tipoBandera;
	}


	public List<BanderasObjeto> getBandera() {
		return bandera;
	}


	public void setBandera(List<BanderasObjeto> bandera) {
		this.bandera = bandera;
	}


	@Override
	public String toString() {
		return "BanderasSiscore [id=" + id + ", tipoBandera=" + tipoBandera + ", bandera=" + bandera + "]";
	}


	
	
		
}
