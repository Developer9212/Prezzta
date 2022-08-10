package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Estados;

@Service
public class EstadoServiceImpl implements IEstadoService{
	
	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public Estados findById(Integer id) {
		String consulta = "SELECT * FROM estados WHERE idestado="+id;
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Estados.class)).size();
		Estados estado = null;
		if(size > 0) {
			estado = jdbc.query(consulta,new BeanPropertyRowMapper<>(Estados.class)).get(0);
		}
		return estado;
	}

}
