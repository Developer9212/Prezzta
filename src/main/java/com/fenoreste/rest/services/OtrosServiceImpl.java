package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.OtrosRepository;


@Service
public class OtrosServiceImpl implements IOtrosService {

	@Autowired
	OtrosRepository otrosDao;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	ITablasService tablasService;

	@Override
	public String sesion() {
		return otrosDao.sesion();
	}

	@Override
	public boolean servicios_activos() {
		return otrosDao.servicios_activos();
	}




	
	
	

}
