package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.rest.entity.CsnVacIngreso;
import com.fenoreste.rest.entity.PersonaPK;

public interface CsnVacIngresoRepository extends JpaRepository<CsnVacIngreso,PersonaPK>{
   
	CsnVacIngreso findBypkAndActivo(PersonaPK pk,boolean estatus);
}
