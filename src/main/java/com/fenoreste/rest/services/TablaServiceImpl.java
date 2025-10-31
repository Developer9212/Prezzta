package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.TablaRepository;
import com.fenoreste.rest.entity.Tabla;
import com.fenoreste.rest.entity.TablaPK;

@Service
public class TablaServiceImpl implements ITablaService {
    
	@Autowired
	TablaRepository tablaRepository;
	
	@Override
	public Tabla buscarPorId(TablaPK pk) {
		return tablaRepository.findById(pk).orElse(null);
	}
	
}
