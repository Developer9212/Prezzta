package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.VAuxiliarRepository;
import com.fenoreste.rest.entity.PersonaPK;
import com.fenoreste.rest.entity.V_Auxiliares;

@Service
public class VAuxiliarServiceImpl implements IVAuxiliaresService {

	@Autowired
	private VAuxiliarRepository auxiliarRepository;
	

	@Override
	public Integer totRenovadoPorOgsIdproducto(PersonaPK pk, Integer idproducto,String periodo) {
		return auxiliarRepository.totalRenovadoPorOgsIdProductoPeriodoPagados(pk.getIdorigen(), pk.getIdgrupo(), pk.getIdsocio(), idproducto, periodo);
	}

	@Override
	public V_Auxiliares buscarGerencialPorOgsPeriodo(PersonaPK pk, Integer idproducto, String periodo) {
		System.out.println("Personas pk:"+pk+",idproducto:"+idproducto+",periodo:"+periodo);
		return auxiliarRepository.bucarGenerencialPagadoPorPeriodo(pk.getIdorigen(),pk.getIdgrupo(),pk.getIdsocio(), idproducto, periodo);
	}

}
