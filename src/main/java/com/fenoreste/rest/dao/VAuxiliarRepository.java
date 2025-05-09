package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.entity.V_Auxiliares;

public interface VAuxiliarRepository extends JpaRepository<V_Auxiliares,AuxiliarPK> {
	
   @Query(value = " SELECT count(*) FROM v_auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND idproducto = ?4 and estatus=3 and to_char(fechaactivacion,'YYYYMM') = ?5"
   		+ " AND autorizo = (SELECT dato1 FROM tablas WHERE idtabla ='prezzta' AND idelemento='usuario')::::numeric" , nativeQuery = true)	
   public Integer totalRenovadoPorOgsIdProductoPeriodoPagados(Integer Idorigen,Integer idgrupo,Integer idsocio,Integer idproducto,String periodo);
   
   @Query(value = "SELECT * FROM v_auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND idproducto = ?4 AND to_char(fechaactivacion,'YYYYMM') = ?5 AND estatus = 3 "
   		+ " AND autorizo = (SELECT dato1 FROM tablas WHERE idtabla ='prezzta' AND idelemento='usuario')::::numeric LIMIT 1",nativeQuery = true)
   public V_Auxiliares bucarGenerencialPagadoPorPeriodo(Integer idorigen,Integer idgrupo,Integer idsocio,Integer idproducto, String periodo);

}
