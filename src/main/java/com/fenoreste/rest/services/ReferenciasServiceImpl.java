package com.fenoreste.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.fenoreste.rest.entity.Referencias;

@Service
public class ReferenciasServiceImpl implements IReferenciaService {
	
	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public Referencias finByOgsAndTipoReferencia(Integer idorigen, Integer idgrupo, Integer idsocio,Integer tiporeferencia) {
		String consulta = "SELECT * FROM referencias WHERE idorigen = " + idorigen + " AND idgrupo = " + idgrupo + " AND idsocio = " + idsocio + " AND tiporeferencia = " + tiporeferencia;
		//System.out.println("Buscando referencia:"+consulta);
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Referencias.class)).size();
		Referencias referencia = null;
		if(size > 0) {
			referencia = jdbc.query(consulta,new BeanPropertyRowMapper<>(Referencias.class)).get(0);
		}
		return referencia;
	}

	@Override
	public List<Referencias> findAll(Integer idorigen, Integer idgrupo, Integer idsocio) {
		String consulta = "SELECT * FROM referencias WHERE idorigen = " + idorigen + " AND idgrupo = " + idgrupo + " AND idsocio = " + idsocio;		
		List<Referencias> referencias = jdbc.query(consulta,new BeanPropertyRowMapper<>(Referencias.class));
		return referencias;
	}

}
