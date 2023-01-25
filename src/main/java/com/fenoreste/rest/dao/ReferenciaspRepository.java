package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.entity.Referenciasp;

public interface ReferenciaspRepository extends JpaRepository<Referenciasp,AuxiliarPK> {
	
	Referenciasp findBypkAndTiporeferencia(AuxiliarPK pk,Integer tipo);

}
