package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.entity.FolioTarjeta;

public interface IFolioTarjetaService {

	public FolioTarjeta buscarPorId(AuxiliarPK pk);
}
