package com.fenoreste.rest.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Persona;

@Service
public class PersonaServiceImpl implements IPersonaService{
	
	@Autowired
	private JdbcTemplate jdbc;
	
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
	public Persona findByOgs(Integer idorigen, Integer idgrupo, Integer idsocio) {
		String consulta = "SELECT * FROM personas WHERE idorigen="+idorigen+" AND idgrupo="+idgrupo+" AND idsocio="+idsocio;
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Persona.class)).size();
		Persona p = null;
		if(size >0) {
			p = jdbc.query(consulta, new BeanPropertyRowMapper <>(Persona.class)).get(0);
		}
		return p;
	}
	
	

}
