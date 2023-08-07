package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Auxiliar;
import com.fenoreste.rest.entity.AuxiliarPK;

public interface AuxiliarRepository extends JpaRepository<Auxiliar, AuxiliarPK> {
   
	@Query(value = "SELECT * FROM auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND idproducto = ?4 AND estatus = 2",nativeQuery = true)
	public Auxiliar buscarPorOpaIdProducto(Integer idorigen,Integer idgrupo,Integer idsocio,Integer idproducto);
	
	
	@Query(value = "SELECT count(*) FROM auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND estatus=1",nativeQuery = true)
	public Integer totalAutorizados(Integer idorigen,Integer idgrupo,Integer idsocio);
	
}
