package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.TarjetaRepository;
import com.fenoreste.rest.entity.Tarjeta;

@Service
public class TarjetaServiceImpl implements ITarjetaService {
	
	@Autowired
	private TarjetaRepository tarjetaDao;

	@Override
	public Tarjeta buscarPorId(String idtarjeta) {
		return tarjetaDao.findById(idtarjeta).orElse(null);
	}

}
