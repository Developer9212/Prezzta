package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.OrigenesRepository;
import com.fenoreste.rest.entity.Origenes;

@Service
public class OrigenesServiceImpl implements IOrigenesService {

	@Autowired
	OrigenesRepository origenesDao;

	@Override
	public Origenes findMatrizOrigen() {
		// TODO Auto-generated method stub
		return origenesDao.findMatrizOrigen();
	}

	@Override
	public Origenes findOrigenById(Integer idorigen) {
		// TODO Auto-generated method stub
		return origenesDao.findOrigenById(idorigen);
	}

	@Override
	public String fechaTrabajo() {
		// TODO Auto-generated method stub
		return origenesDao.fechaTrabajo();
	}

}
