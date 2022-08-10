package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.tmp_aperturas;

public interface ITmpAperturasService {
 
	public tmp_aperturas buscar(Integer idorigen,Integer idgrupo,Integer idsocio);
	public tmp_aperturas guardar(tmp_aperturas model);
	public void eliminar(tmp_aperturas model);
	
}
