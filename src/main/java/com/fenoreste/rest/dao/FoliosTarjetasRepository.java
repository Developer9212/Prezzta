package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.FoliosTarjetas;

public interface FoliosTarjetasRepository extends JpaRepository<FoliosTarjetas, String> {

	@Query(value = "SELECT * FROM ws_siscoop_folios_tarjetas WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar =?3",nativeQuery = true)
	FoliosTarjetas folioTarjeta(Integer idorigenp,Integer idprodcuto,Integer idauxiliar);
	
	
}
