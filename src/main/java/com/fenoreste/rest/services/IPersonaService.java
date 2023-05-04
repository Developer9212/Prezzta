package com.fenoreste.rest.services;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.PersonaPK;

@Service
public interface IPersonaService {
	
	public Persona findPersonaByDocumento(String tipoDocumento,String documento);
	public Persona findByOgs(PersonaPK pk);
}
 