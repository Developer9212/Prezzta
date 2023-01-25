package com.fenoreste.rest.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.fenoreste.rest.entity.Auxiliar;

@Service
public class AuxiliaresServiceImpl implements IAuxiliaresService {

	@Autowired
	private JdbcTemplate jdbc ;

	@Override
	public Auxiliar AuxiliarByOgsIdproducto(Integer idorigen,Integer idgrupo, Integer idsocio,Integer idproducto) {
		String consulta = "SELECT * FROM auxiliares a WHERE idorigen="+idorigen+" AND idgrupo="+idgrupo+" AND idsocio="+idsocio+" AND idproducto = "+idproducto;
		//System.out.println("Procesando consulta auxiliar :"+consulta);
		int size=  jdbc.query(consulta,new BeanPropertyRowMapper<>(Auxiliar.class)).size();
		Auxiliar a=null;		
		if(size > 0) {
			a=jdbc.query(consulta,new BeanPropertyRowMapper<>(Auxiliar.class)).get(0);
	    }		
		return a;
	}

	@Override
	public Auxiliar AuxiliarByOpa(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		String consulta = "SELECT * FROM auxiliares a WHERE idorigenp="+idorigenp+" AND idproducto="+idproducto+" AND idauxiliar="+idauxiliar;
		int size=  jdbc.query(consulta,new BeanPropertyRowMapper<>(Auxiliar.class)).size();
		Auxiliar a=null;		
		if(size > 0) {
			a=jdbc.query(consulta,new BeanPropertyRowMapper<>(Auxiliar.class)).get(0);
	    }		
		return a;
	}
	

}
