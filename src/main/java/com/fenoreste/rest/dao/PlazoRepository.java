package com.fenoreste.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Plazo;

public interface PlazoRepository extends JpaRepository<Plazo,Integer>{
   
	@Query(value = "SELECT * FROM plazos WHERE ?1 >= montominimo AND ?1 <= montomaximo",nativeQuery = true)
	public List<Plazo>listaPlazos(Double monto);
}
