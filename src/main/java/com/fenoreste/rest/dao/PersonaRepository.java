package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.PersonaPK;

public interface PersonaRepository extends JpaRepository<Persona,PersonaPK> {
	
}
