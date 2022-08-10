package com.fenoreste.rest.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.FoliosTarjetasRepository;
import com.fenoreste.rest.entity.FoliosTarjetas;

@Service
public class FoliosTarjetasImpl implements IFoliosTarjetasService {
    
	@Autowired
	FoliosTarjetasRepository foliosTarjetasDao;
	
	@Override
	public FoliosTarjetas findByOpa(Integer idorigenp,Integer idproducto,Integer idauxiliar) {
		return foliosTarjetasDao.folioTarjeta(idorigenp,idproducto,idauxiliar);
	}

}
