package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.FolioTarjetaRepository;
import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.entity.FolioTarjeta;

@Service
public class FolioTarjetaServiceImpl implements IFolioTarjetaService {
     
	@Autowired
	FolioTarjetaRepository folioTarjetaDao;
	
	@Override
	public FolioTarjeta buscarPorId(AuxiliarPK pk) {
		return folioTarjetaDao.findById(pk).orElse(null);
	}
    
	

}
