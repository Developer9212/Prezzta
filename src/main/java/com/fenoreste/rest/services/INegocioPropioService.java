package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.Negociopropio;

public interface INegocioPropioService {
	
	public Negociopropio findByOgs(Integer idorigen, Integer idgrupo, Integer idsocio);

}
