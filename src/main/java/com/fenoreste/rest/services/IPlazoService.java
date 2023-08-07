package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.Plazo;

public interface IPlazoService {

	public Plazo buscarPorId(Integer Id);
	public Plazo buscarPorMonto(Double monto);
}
