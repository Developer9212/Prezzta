package com.fenoreste.rest.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.AmortizacionesRepository;
import com.fenoreste.rest.entity.Amortizacion;


@Service
public class AmortizacionesServiceImpl implements IAmortizacionesService {

	@Autowired
	AmortizacionesRepository amortizacionesDao;

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public List<Amortizacion> findAll(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
	     String query = "SELECT * FROM amortizaciones WHERE idorigenp="+idorigenp+" AND idproducto="+idproducto+" AND idauxiliar="+idauxiliar;
		 List<Amortizacion>lista = jdbc.query(query,new BeanPropertyRowMapper<>(Amortizacion.class));
		
		 return lista;
	}

	@Override
	public Amortizacion findUltimaAmortizacion(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		return amortizacionesDao.ultimaAmortizacion(idorigenp, idproducto, idauxiliar);
	}


	
	

}
