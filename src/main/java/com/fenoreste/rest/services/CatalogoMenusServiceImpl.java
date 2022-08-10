package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.fenoreste.rest.entity.CatalogoMenus;
import com.fenoreste.rest.services.ICatalogoMenusService;

@Service
public class CatalogoMenusServiceImpl implements ICatalogoMenusService{
	
	@Autowired
	private JdbcTemplate jdbc ;
	
	@Override
	public CatalogoMenus findByMenuOpcion(String menu, Integer opcion) {
		CatalogoMenus catalogo = new CatalogoMenus();		
		try {
			String consulta = "SELECT * FROM catalogo_menus WHERE menu='"+menu+"' AND opcion="+ opcion;
			int size = jdbc.query(consulta,new BeanPropertyRowMapper<>(CatalogoMenus.class)).size();
			if(size > 0 ) {
				 catalogo = jdbc.query(consulta, new BeanPropertyRowMapper<>(CatalogoMenus.class)).get(0);
			}			
		} catch (Exception e) {
			catalogo.setDescripcion("Sin dato");
			System.out.println("Error al buscar el catalogo:"+e.getMessage());	
		}		
		return catalogo;
	}

}