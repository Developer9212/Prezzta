package com.fenoreste.rest.services;

public interface IGatService {

	public void insertRegistros(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	public Double calculoGAT(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	public void removeRegistros(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
}
