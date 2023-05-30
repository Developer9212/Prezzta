package com.fenoreste.rest.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.PersonaRepository;
import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.PersonaPK;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonaServiceImpl implements IPersonaService{
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Override
	public Persona findPersonaByDocumento(String tipoDocumento,String documento) {	
		String consulta = "SELECT * FROM personas WHERE "+tipoDocumento+"='"+documento+"' AND idgrupo=10 AND estatus=true";
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Persona.class)).size();
		Persona p = null;
		if(size >0) {
			log.info("Size:::::::::::::"+size);
			p = jdbc.query(consulta, new BeanPropertyRowMapper <>(Persona.class)).get(0);
		}
		log.info(""+p);
		//Ultima modificacion 03/05/2023 Wilmer se dejo de usar template porque no agarraba PK
		return personaRepository.buscarPorCurp(documento);
	}

	@Override
	public Persona findByOgs(PersonaPK pk) {		
		return personaRepository.findById(pk).orElse(null);
	}

	
	
	

}
