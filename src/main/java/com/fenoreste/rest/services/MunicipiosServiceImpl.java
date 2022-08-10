package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.fenoreste.rest.entity.Municipios;

@Service
public class MunicipiosServiceImpl implements IMunicipioService {

	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public Municipios findById(Integer id) {
		String consulta = "SELECT * FROM municipios WHERE idmunicipio="+id;
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Municipios.class)).size();
		Municipios municipio = null;
		if(size > 0) {
			municipio = jdbc.query(consulta,new BeanPropertyRowMapper<>(Municipios.class)).get(0);
		}
		return municipio;
	}

}
