package com.fenoreste.rest.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import com.fenoreste.rest.entity.Tablas;

@Service
public class TablasServiceImpl implements ITablasService {
	
	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public Tablas findIdtablaAndIdelemento(String idtabla, String idelemento) {
		//System.out.println("IdTabla:"+idtabla+",Idelemento:"+idelemento);
		String consulta="SELECT * FROM tablas WHERE idtabla='"+idtabla+"' and idelemento='"+idelemento+"'";
		//System.out.println(consulta);
		int size=  jdbc.query(consulta,new BeanPropertyRowMapper<>(Tablas.class)).size();
		Tablas tb=null;
		if(size > 0) {
			tb = jdbc.query(consulta,new BeanPropertyRowMapper<>(Tablas.class)).get(0);
		}
		return tb;
	}

}
