package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.tmp_aperturasRepository;
import com.fenoreste.rest.entity.tmp_aperturas;

@Service
public class TmpAperturasServiceImpl implements ITmpAperturasService {
     
	@Autowired
	tmp_aperturasRepository tmpRepo;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public tmp_aperturas buscar(Integer idorigen, Integer idgrupo, Integer idsocio) {		  
		return tmpRepo.buscar(idorigen, idgrupo, idsocio);
	}

	@Override
	public tmp_aperturas guardar(tmp_aperturas model) {
		return tmpRepo.save(model);
	}

	@Override
	public void eliminar(tmp_aperturas model) {
	   try {
		  tmpRepo.eliminarTmp(model.getIdorigen(),model.getIdgrupo(),model.getIdsocio());		     
	   } catch (Exception e) {
		  System.out.println("Error al eliminar registros temporales:"+e.getMessage());
       }				
	}

}
