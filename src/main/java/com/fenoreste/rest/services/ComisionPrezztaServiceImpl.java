package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.ComisionPrezztaRepository;
import com.fenoreste.rest.entity.ComisionPrezzta;

@Service
public class ComisionPrezztaServiceImpl implements IComisionPrezztaService{
	
	@Autowired
	private ComisionPrezztaRepository comisionPrezztaRepository;

	@Override
	public ComisionPrezzta buscarPorId(Integer idproducto) {
		return comisionPrezztaRepository.findById(idproducto).orElse(null);
	}

}
