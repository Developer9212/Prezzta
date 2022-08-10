package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Negociopropio;

@Service
public class NegocioPopioServiceImpl implements INegocioPropioService {
	
	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public Negociopropio findByOgs(Integer idorigen, Integer idgrupo, Integer idsocio) {
		String consulta = "SELECT * FROM negociopropio WHERE idorigen="+idorigen+" AND idgrupo="+idgrupo+" AND idsocio="+idsocio;
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Negociopropio.class)).size();
		Negociopropio negocio = null;
		if(size > 0 ) {
			negocio = jdbc.query(consulta,new BeanPropertyRowMapper<>(Negociopropio.class)).get(0);
		}
		return negocio;
	}

}
