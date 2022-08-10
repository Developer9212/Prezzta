package com.fenoreste.rest.services;


import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Auxiliar;


@Service
public interface IAuxiliaresService { 
	
	public Auxiliar AuxiliarByOgsIdproducto(Integer idorigen,Integer idgrupo,Integer idsocio,Integer idproducto);
	public Auxiliar AuxiliarByOpa(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	
}
