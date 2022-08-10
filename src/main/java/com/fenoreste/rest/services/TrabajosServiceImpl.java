package com.fenoreste.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Trabajo;

@Service
public class TrabajosServiceImpl implements ITrabajoService{
	
	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public List<Trabajo> findTrabajosActivosByOgs(Integer idorigen, Integer idgrupo, Integer idsocio,Integer limit ) {
		String consulta = "SELECT * FROM trabajo WHERE idorigen = "+ idorigen
                + " AND idgrupo = " + idgrupo 
                + " AND idsocio = " + idsocio
                + " AND fechasalida IS NULL ORDER BY consecutivo DESC limit "+limit;
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Trabajo.class)).size();
		List<Trabajo>lista_Trabajos = null;
		if(size > 0) {
			lista_Trabajos = jdbc.query(consulta, new BeanPropertyRowMapper<>(Trabajo.class));				
		}
		return lista_Trabajos;
	}

	@Override
	public Trabajo findTrabajoByOgsAndConsecutivo(Integer idorigen, Integer idgrupo, Integer idsocio,Integer consecutivo) {
		String consulta = "SELECT * FROM trabajo WHERE idorigen = "+ idorigen
               			+ " AND idgrupo = " + idgrupo 
               			+ " AND idsocio = " + idsocio
               			+ " AND consecutivo = "+ consecutivo;
		
		int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(Trabajo.class)).size();
		Trabajo trabajo = null;
		if(size > 0) {
			trabajo = jdbc.query(consulta, new BeanPropertyRowMapper<>(Trabajo.class)).get(0);					
		}
		return trabajo;
	}

}
