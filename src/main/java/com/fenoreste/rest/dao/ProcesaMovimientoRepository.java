package com.fenoreste.rest.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fenoreste.rest.entity.RegistraMovimiento;

public interface ProcesaMovimientoRepository extends JpaRepository<RegistraMovimiento , Long> {

	@Transactional
	@Modifying
	@CacheEvict
	@Query(value = " INSERT INTO bankingly_movimientos_ca"
			+ "(fecha,idusuario,sesion,referencia,idorigen,idgrupo,idsocio,idorigenp,idproducto,idauxiliar,cargoabono,monto,iva,tipo_amort,sai_aux) "
			+ "VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15)", nativeQuery = true)
	void saves(Date fecha,
			  Integer idusuario,
			  String sesion,
			  String referencia,
			  Integer idorigen,
			  Integer idgrupo,
			  Integer idsocio,			  
			  Integer idorigenp,
			  Integer idproducto,
			  Integer idauxiliar,			  
			  Integer cargoabono,
			  Double monto,
			  Double iva,
			  Integer tipo_amortizacion,
			  String sai);
	
	@Query(value ="DELETE FROM bankingly_movimientos_ca WHERE date(fecha)=?1 AND idusuario=?2 AND sesion=?3 AND referencia=?4",nativeQuery = true)
	void eliminarRegistros(Date fecha,int idusuario,String sesion,String referencia);
	
	@Query(value ="DELETE FROM bankingly_movimientos_ca WHERE idorigen=?1 AND idgrupo=?2 AND idsocio=?3 AND idusuario=(SELECT dato1 FROM tablas WHERE idtabla='prezzta' AND idelemento='usuario')::::INTEGER",nativeQuery = true)
	void eliminarRegistrosTodos(Integer idorigen,Integer idgrupo,Integer idsocio); 
	
	@Query(value = "SELECT * FROM bankingly_movimientos_ca WHERE idorigen=?1 AND idgrupo=?2 AND idsocio=?3",nativeQuery = true)
	List<RegistraMovimiento>movimientosAll(Integer idorigen,Integer idgrupo,Integer idsocio);
	
}
