package com.fenoreste.rest.services;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Municipios;

@Service
public interface IMunicipioService {

	public Municipios findById(Integer id);
}
