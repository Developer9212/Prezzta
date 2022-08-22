package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Referenciasp;

public interface ReferenciaspRepository extends JpaRepository<Referenciasp,Integer> {
	
	@Query(value = "SELECT * FROM referenciasp WHERE idorigenp=?1 AND idproducto=?2 and idauxiliar=?3 and tiporeferencia = 2",nativeQuery = true)
	Referenciasp referenciaspByOPA(Integer idorigenp,Integer idproducto,Integer idauxiliar);

}
