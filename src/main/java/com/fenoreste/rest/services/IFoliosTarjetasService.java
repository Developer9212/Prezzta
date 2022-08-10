package com.fenoreste.rest.services;


import com.fenoreste.rest.entity.FoliosTarjetas;

public interface IFoliosTarjetasService {
   
	FoliosTarjetas findByOpa(Integer idorigenp,Integer idproducto,Integer idauxiliar);
}
