package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.PersonaPK;
import com.fenoreste.rest.entity.V_Auxiliares;

public interface IVAuxiliaresService {
	
	public Integer totRenovadoPorOgsIdproducto(PersonaPK pk,Integer idproducto,String periodo);
	public V_Auxiliares buscarGerencialPorOgsPeriodo(PersonaPK pk,Integer idproducto,String periodo);
   
}
