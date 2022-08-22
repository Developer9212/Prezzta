package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.ReferenciaspRepository;
import com.fenoreste.rest.entity.Referenciasp;

@Service
public class ReferenciaspServiceImpl implements IReferenciaspService {
    
	@Autowired
	ReferenciaspRepository referenciasDao;
	
	@Override
	public Referenciasp referenciaspByOPA(Integer idorigenp, Integer idproducto, Integer idauxiliar) {		
		return referenciasDao.referenciaspByOPA(idorigenp, idproducto, idauxiliar);
	}
     
	
}
