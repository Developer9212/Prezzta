package com.fenoreste.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.PlazoRepository;
import com.fenoreste.rest.entity.Plazo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlazoServiceImpl implements IPlazoService{
    
	@Autowired
	private PlazoRepository plazoDao;
	
	@Override
	public Plazo buscarPorId(Integer Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plazo buscarPorMonto(Double monto) {
		List<Plazo>lista = plazoDao.listaPlazos(monto);
		log.info("La lista de plazos es:"+lista.size());
		Plazo plazo = new Plazo();
		for(int i = 0;i < lista.size(); i++) {
			if(monto >= lista.get(i).getMontominimo() && monto <= lista.get(i).getMontomaximo()) {
				plazo = lista.get(i);
			}
		}
		return plazo;
	}

}
