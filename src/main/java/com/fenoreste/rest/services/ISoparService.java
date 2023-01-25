package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.PersonaPK;
import com.fenoreste.rest.entity.Sopar;

public interface ISoparService {

	public Sopar buscarPorId(PersonaPK pk);
	public Sopar buscarPorIdTipo(PersonaPK pk,String tipo);
}
