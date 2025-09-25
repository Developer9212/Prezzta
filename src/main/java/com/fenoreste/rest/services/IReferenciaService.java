package com.fenoreste.rest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Referencias;

@Service
public interface IReferenciaService {
	
	public Referencias finByOgsAndTipoReferencia(Integer idorigen, Integer idgrupo, Integer idsocio, Integer tiporeferencia);
	public List<Referencias> findAll(Integer idorigen, Integer idgrupo, Integer idsocio);

}
