package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Colonias;

@Service
public class ColoniasServiceImpl implements IColoniaService {

	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public Colonias findById(Integer id) {
		String consulta = "SELECT * FROM colonias WHERE idcolonia="+id;
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Colonias.class)).size();
		Colonias colonia = null;
		if(size > 0) {
		   colonia = jdbc.query(consulta,new BeanPropertyRowMapper<>(Colonias.class)).get(0);
		}
		return colonia;
	}

}
