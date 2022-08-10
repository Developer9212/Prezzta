package com.fenoreste.rest.modelos;

public class BanderasObjeto {

	String id;
	boolean SeCumple;
	String nombre;
	
	public BanderasObjeto() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "BanderasObjeto [id=" + id + ", SeCumple=" + SeCumple + ", nombre=" + nombre + "]";
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public boolean isSeCumple() {
		return SeCumple;
	}

	public void setSeCumple(boolean seCumple) {
		SeCumple = seCumple;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
