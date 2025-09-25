package com.fenoreste.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Amortizacion;

public interface AmortizacionesRepository extends JpaRepository<Amortizacion, Integer> {
     
	@Query(value = "SELECT * FROM amortizaciones WHERE idorigenp=? AND idproducto=? AND idauxiliar=? ORDER BY idamortizacion", nativeQuery = true)
	List<Amortizacion>findAll(Integer idorigenp, Integer idproducto, Integer idauxiliar);
	
	@Query(value = "SELECT * FROM amortizaciones WHERE idorigenp=? AND idproducto=? AND idauxiliar=? ORDER BY vence DESC LIMIT 1" , nativeQuery = true)
	Amortizacion ultimaAmortizacion(Integer idorigenp, Integer idproducto, Integer idauxiliar);
	
}
