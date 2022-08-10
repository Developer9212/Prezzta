package com.fenoreste.rest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.Trabajo;

@Service
public interface ITrabajoService {
	
	public List<Trabajo> findTrabajosActivosByOgs(Integer idorigen,Integer idgrupo,Integer idsocio,Integer limit);
	
	public Trabajo findTrabajoByOgsAndConsecutivo(Integer idorigen,Integer idgrupo,Integer idsocio,Integer consecutivo);
}