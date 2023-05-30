package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.PersonaPK;

public interface PersonaRepository extends JpaRepository<Persona,PersonaPK> {
      @Query(value="SELECT * FROM personas WHERE curp=?1 AND idgrupo=10 AND estatus=true",nativeQuery = true)
      public Persona buscarPorCurp(String curp);
}
