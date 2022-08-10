package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.CalificacionBackendPrezzta;

public interface CalificacionBackendPrezztaRepository extends JpaRepository<CalificacionBackendPrezzta, Integer> {

	
	@Query(value="INSERT INTO prezzta_calificacion_backend VALUES(?1,?2,?3,?4,?5)",nativeQuery = true)
	void insertarCalificacionBackendPrezzta(Integer idorigenp,Integer idproducto,Integer idauxiliar,Double monto,String confirmacion);
	
	@Query(value="SELECT * FROM prezzta_calificacion_backend WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3",nativeQuery=true)
	CalificacionBackendPrezzta findByOPA(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value="UPDATE prezzta_calificacion_backend set confirmacion_backend =?4 WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3",nativeQuery=true)
	void updateRegistros(Integer idorigenp,Integer idproducto,Integer idauxiliar,String calificacion);
	
}
