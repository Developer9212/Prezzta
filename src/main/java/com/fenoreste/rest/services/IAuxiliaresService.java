package com.fenoreste.rest.services;


import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Auxiliar;
import com.fenoreste.rest.entity.AuxiliarPK;


@Service
public interface IAuxiliaresService { 
	
	public Auxiliar AuxiliarByOgsIdproducto(Integer idorigen,Integer idgrupo,Integer idsocio,Integer idproducto,Integer estatus);
	public Auxiliar AuxiliarByOpa(AuxiliarPK pk);
	public Integer totAutorizados(Integer idorigen,Integer idgrupo,Integer idsocio);
	public Auxiliar buscarCuentaCorrienteMitras(Integer idorigen,Integer idgrupo,Integer idsocio);
	
}
