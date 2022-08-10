package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Persona;

public interface GATRepository extends JpaRepository<Persona,Long> {

	@Query(value="INSERT INTO paso_amortizaciones(idorigenp,idproducto,idauxiliar,idamortizacion,vence,abono,io)"
			+ "  SELECT idorigenp,idproducto,idauxiliar,idamortizacion,vence,abono,io"
			+ "  FROM amortizaciones"
			+ "  WHERE idorigenp = ?1 AND idproducto = ?2 AND idauxiliar = ?3 ORDER BY idamortizacion",nativeQuery=true)
	void insertarRegistros(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value="SELECT calculo_cat2(idorigenp, idproducto, idauxiliar, montosolicitado,0,tasaio,tasaiod,"
			+ " periodoabonos::::integer,fecha_solicitud,pagodiafijo,0.0,TRUE,0,0,0,0.0,0.0)"
			+ " FROM auxiliares"
			+ " WHERE idorigenp = ?1 AND idproducto = ?2 AND idauxiliar = ?3",nativeQuery = true)
	Double cat(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	
	@Query(value="DELETE FROM paso_amortizaciones WHERE idorigenp = ?1 AND idproducto = ?2 AND idauxiliar = ?3",nativeQuery=true)
	void eliminarRegistros(Integer idorigenp,Integer idproducto,Integer idauxiliar);
}
