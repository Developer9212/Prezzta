package com.fenoreste.rest.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Persona;

public interface GATRepository extends JpaRepository<Persona,Long> {

	@Transactional
	@Modifying
	@Query(value="INSERT INTO paso_amortizaciones(idorigenp,idproducto,idauxiliar,idamortizacion,vence,abono,io)"
			+ "  SELECT idorigenp,idproducto,idauxiliar,idamortizacion,vence,abono,io"
			+ "  FROM amortizaciones"
			+ "  WHERE idorigenp = ?1 AND idproducto = ?2 AND idauxiliar = ?3 ORDER BY idamortizacion",nativeQuery=true)
	void insertarRegistros(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value="select calculo_cat_contratotxt(?1, ?2, ?3, FALSE)",nativeQuery = true)
	Double cat(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM paso_amortizaciones WHERE idorigenp = ?1 AND idproducto = ?2 AND idauxiliar = ?3",nativeQuery=true)
	void eliminarRegistros(Integer idorigenp,Integer idproducto,Integer idauxiliar);
}
