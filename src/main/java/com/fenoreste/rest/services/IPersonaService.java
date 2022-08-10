package com.fenoreste.rest.services;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Persona;

@Service
public interface IPersonaService {
	
	public Persona findPersonaByDocumento(String tipoDocumento,String documento);
	public Persona findByOgs(Integer idorigen,Integer idgrupo,Integer idsocio);
}
 