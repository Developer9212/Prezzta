package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.CsnVacIngresoRepository;
import com.fenoreste.rest.entity.CsnVacIngreso;
import com.fenoreste.rest.entity.PersonaPK;

@Service
public class CsnVacIngresoServiceImpl implements ICsnVacIngresoService{

	@Autowired
	private CsnVacIngresoRepository csnVacIngresoDao;
	
	@Override
	public CsnVacIngreso buscarPorIdActivo(PersonaPK pk) {
		return csnVacIngresoDao.findBypkAndActivo(pk, true);
	}

}
