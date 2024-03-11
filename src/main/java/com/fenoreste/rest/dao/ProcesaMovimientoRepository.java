package com.fenoreste.rest.dao;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.fenoreste.rest.entity.MovimientosPK;
import com.fenoreste.rest.entity.RegistraMovimiento;

public interface ProcesaMovimientoRepository extends JpaRepository<RegistraMovimiento , MovimientosPK> {
     
	@Modifying	
	@Transactional
	@Query(value ="DELETE FROM bankingly_movimientos_ca WHERE sesion=?1 AND referencia=?2",nativeQuery = true)
	void eliminarRegistros(String sesion,String referencia);
	
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM bankingly_movimientos_ca WHERE idorigen=?1 AND idgrupo=?2 AND idsocio=?3 AND idusuario=(SELECT dato1 FROM tablas WHERE idtabla='prezzta' AND idelemento='usuario')::::INTEGER",nativeQuery = true)
	void eliminarRegistrosTodos(Integer idorigen,Integer idgrupo,Integer idsocio); 
	
	@Query(value = "SELECT * FROM bankingly_movimientos_ca WHERE idorigen=?1 AND idgrupo=?2 AND idsocio=?3",nativeQuery = true)
	List<RegistraMovimiento>movimientosAll(Integer idorigen,Integer idgrupo,Integer idsocio);
	
}
