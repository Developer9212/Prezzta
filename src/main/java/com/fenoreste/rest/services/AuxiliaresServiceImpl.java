package com.fenoreste.rest.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.AuxiliarRepository;
import com.fenoreste.rest.entity.Auxiliar;
import com.fenoreste.rest.entity.AuxiliarPK;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuxiliaresServiceImpl implements IAuxiliaresService {

	@Autowired
	private AuxiliarRepository auxiliarRepository;

	@Override
	public Auxiliar AuxiliarByOgsIdproducto(Integer idorigen,Integer idgrupo, Integer idsocio,Integer idproducto,Integer estatus) {
	    log.info("Buscando datos para:"+idorigen+",idgrupo:"+idgrupo+",idsocio:"+idsocio);
		return auxiliarRepository.buscarPorOpaIdProducto(idorigen,idgrupo, idsocio, idproducto,estatus);
	}

	@Override
	public Auxiliar AuxiliarByOpa(AuxiliarPK pk) {
		return auxiliarRepository.findById(pk).orElse(null);
	}

	@Override
	public Integer totAutorizados(Integer idorigen, Integer idgrupo, Integer idsocio) {
		return auxiliarRepository.totalAutorizados(idorigen, idgrupo, idsocio);
	}
	

}
