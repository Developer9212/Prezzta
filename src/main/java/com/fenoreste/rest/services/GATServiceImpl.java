package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenoreste.rest.dao.GATRepository;

@Service
public class GATServiceImpl implements IGatService{
    
	@Autowired
	GATRepository gatDao;
	
	@Override
	@Transactional
	@Modifying
	public void insertRegistros(Integer idorigenp, Integer idproducto, Integer idauxiliar) { 
		gatDao.insertarRegistros(idorigenp, idproducto, idauxiliar);
	}

	@Override
	public Double calculoGAT(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		return gatDao.cat(idorigenp, idproducto, idauxiliar);
	}

	@Override
	@Transactional
	@Modifying
	public void removeRegistros(Integer idorigenp, Integer idproducto, Integer idauxiliar) {		
			gatDao.eliminarRegistros(idorigenp, idproducto, idauxiliar);			
	}

	
}
