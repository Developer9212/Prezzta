package com.fenoreste.rest.services;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Origenes;

@Service
public interface IOrigenesService {

	public Origenes findMatrizOrigen();
	
	public Origenes findOrigenById(Integer idorigen);
	
	public String fechaTrabajo();
}
