package com.fenoreste.rest.services;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Colonias;

@Service
public interface IColoniaService {

	public Colonias findById(Integer id);
}
