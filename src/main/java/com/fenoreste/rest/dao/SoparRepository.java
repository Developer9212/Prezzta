package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.rest.entity.PersonaPK;
import com.fenoreste.rest.entity.Sopar;

public interface SoparRepository extends JpaRepository<Sopar,PersonaPK>{

	Sopar findByPersonaPKAndTipo(PersonaPK pk,String tipo);
}
