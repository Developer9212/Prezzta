package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.ReferenciaspRepository;
import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.entity.Referenciasp;

@Service
public class ReferenciaspServiceImpl implements IReferenciaspService {
    
	@Autowired
	ReferenciaspRepository referenciaspDao;

	@Override
	public Referenciasp buscarPorIdTipoReferencia(AuxiliarPK auxPk) {
		return referenciaspDao.findBypkAndTiporeferencia(auxPk, 2);
	}
	
	
	
}
