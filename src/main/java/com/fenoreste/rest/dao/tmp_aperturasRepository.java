package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.fenoreste.rest.entity.tmp_aperturas;

public interface tmp_aperturasRepository extends JpaRepository<tmp_aperturas,Integer> {

	@Query(value="SELECT * FROM tmp_prestamos_apertura_prezzta WHERE idorigen=?1 AND idgrupo=?2 AND idsocio=?3 AND fecha IS NOT NULL ORDER BY fecha DESC LIMIT 1", nativeQuery = true)
	tmp_aperturas buscar(Integer idorigen,Integer idgrupo,Integer idosocio);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM tmp_prestamos_apertura_prezzta WHERE idorigen=?1 AND idgrupo=?2 AND idsocio=?3", nativeQuery = true)
	void eliminarTmp(Integer idorigen,Integer idgrupo,Integer idosocio);
	
}
