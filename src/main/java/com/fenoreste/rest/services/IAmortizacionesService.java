package com.fenoreste.rest.services;

import java.util.List;

import com.fenoreste.rest.entity.Amortizacion;



public interface IAmortizacionesService {
	
	public List<Amortizacion>findAll(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	public Amortizacion findUltimaAmortizacion(Integer idorigenp,Integer idproducto,Integer idauxiliar);
		
			
}
