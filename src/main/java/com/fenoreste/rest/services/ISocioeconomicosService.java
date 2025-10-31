package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.Socioeconomicos;

public interface ISocioeconomicosService {
	
	public Socioeconomicos findByOgs(Integer idorigen, Integer idgrupo, Integer idsocio);

}
