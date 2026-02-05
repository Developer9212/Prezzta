package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.FinalidadRepository;
import com.fenoreste.rest.entity.Finalidad;

@Service
public class FinalidadServiceImpl implements IFindalidaService{
	
	@Autowired
	private FinalidadRepository finalidadRepository;

	@Override
	public Finalidad buscarPorId(Integer idfinalidad) {
		System.out.println("IDFinalidad:"+idfinalidad);
		return finalidadRepository.findById(idfinalidad).orElse(null);
	}

}
