package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.fenoreste.rest.entity.Socioeconomicos;

@Service
public class SocioeconomicosServiceImpl implements ISocioeconomicosService {
	
	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public Socioeconomicos findByOgs(Integer idorigen, Integer idgrupo, Integer idsocio) {
		String consulta = "SELECT * FROM socioeconomicos WHERE idorigen="+idorigen+" AND idgrupo="+idgrupo+" AND idsocio="+idsocio;
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Socioeconomicos.class)).size();
		Socioeconomicos sc=null;
		if(size > 0 ) {
			sc = jdbc.query(consulta,new BeanPropertyRowMapper<>(Socioeconomicos.class)).get(0);
		}
		return sc;
	}

}
