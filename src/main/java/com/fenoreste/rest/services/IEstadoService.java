package com.fenoreste.rest.services;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Estados;

@Service
public interface IEstadoService {

	public Estados findById(Integer id);
}
