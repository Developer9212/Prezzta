package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.SoparRepository;
import com.fenoreste.rest.entity.PersonaPK;
import com.fenoreste.rest.entity.Sopar;

@Service
public class SoparServiceImpl implements ISoparService {
    
	@Autowired
	private SoparRepository soparDao;
	
	@Override
	public Sopar buscarPorId(PersonaPK pk) {
		return soparDao.findById(pk).orElse(null);
	}

	@Override
	public Sopar buscarPorIdTipo(PersonaPK pk, String tipo) {
		return soparDao.findByPersonaPKAndTipo(pk, tipo);
	}

}
