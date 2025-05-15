package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Auxiliar;
import com.fenoreste.rest.entity.AuxiliarPK;

public interface AuxiliarRepository extends JpaRepository<Auxiliar, AuxiliarPK> {
   
	@Query(value = "SELECT * FROM auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND idproducto = ?4 AND estatus = ?5",nativeQuery = true)
	public Auxiliar buscarPorOpaIdProducto(Integer idorigen,Integer idgrupo,Integer idsocio,Integer idproducto,Integer estatus);
	
	@Query(value = "SELECT count(*) FROM auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND estatus = 1",nativeQuery = true)
	public Integer totalAutorizados(Integer idorigen,Integer idgrupo,Integer idsocio);
	
	@Query(value = "SELECT * FROM auxiliares WHERE idorigen = ?1 AND idgrupo = ?2 AND idsocio = ?3 AND"
			+ " idproducto = (SELECT dato1 FROM tablas WHERE idtabla='prezzta' AND idelemento='producto_para_dispersion')::::numeric AND estatus IN (0,2) LIMIT 1",nativeQuery = true)
	public Auxiliar buscarCuentaCorrienteMitras(Integer idorigen,Integer idgrupo,Integer idsocio);
	
}
