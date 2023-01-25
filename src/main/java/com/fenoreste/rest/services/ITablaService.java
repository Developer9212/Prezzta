package com.fenoreste.rest.services;


import com.fenoreste.rest.entity.TablaPK;
import com.fenoreste.rest.entity.Tabla;

public interface ITablaService {

	
	public Tabla buscarPorId(TablaPK pk);

}
