package com.fenoreste.rest.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.PersonaRepository;
import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.PersonaPK;

@Service
public class PersonaServiceImpl implements IPersonaService{
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Override
	public Persona findPersonaByDocumento(String tipoDocumento,String documento) {	
		String consulta = "SELECT * FROM personas WHERE "+tipoDocumento+"='"+documento+"' AND idgrupo=10";
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Persona.class)).size();
		Persona p = null;
		if(size >0) {
			p = jdbc.query(consulta, new BeanPropertyRowMapper <>(Persona.class)).get(0);
		}
		return p;
	}

	@Override
	public Persona findByOgs(PersonaPK pk) {		
		return personaRepository.findById(pk).orElse(null);
	}

	@Override
	public Persona buscarPorOgsGrupo(PersonaPK pk, Integer idgrupo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
