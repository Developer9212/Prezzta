package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.CsnVacIngreso;
import com.fenoreste.rest.entity.PersonaPK;

public interface ICsnVacIngresoService {
   
	public CsnVacIngreso buscarPorIdActivo(PersonaPK pk);
}
