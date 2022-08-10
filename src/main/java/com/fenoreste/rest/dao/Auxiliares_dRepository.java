package com.fenoreste.rest.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fenoreste.rest.entity.Auxiliares_d;


public interface Auxiliares_dRepository extends CrudRepository<Auxiliares_d, Integer> {

	@Query(value = "SELECT * FROM auxiliares_d WHERE idorigenp=?1  AND idproducto=?2 AND idauxiliar=?3", nativeQuery = true)
	List<Auxiliares_d> findAuxiliares_dByOpa(Integer idorigenp, Integer idproducto, Integer idauxiliar);

	@Query(value = "SELECT * FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 AND date(fecha) between ?4 AND ?5", nativeQuery = true)
	List<Auxiliares_d> findAuxiliares_dByOpaFecha(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date fechaI, Date fechaF);

	//Saldo para un opa ultimas 24 horass
	@Query(value = "SELECT saldoec FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3"
			+ " AND date(fecha) <= ?4 ORDER BY fecha DESC limit 1  ", nativeQuery = true)
	Double findSaldoUltimas24Horas(Integer idorigenp, Integer idproducto, Integer idauxiliar, Date fecha);

	// Saldo para un opa ultimas 48 horass
	@Query(value = "SELECT saldoec  FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3"
			+ " AND date(fecha) <= ?4 ORDER BY fecha DESC limit 1 ", nativeQuery = true)
	Double findSaldoUltimas48Horas(Integer idorigenp, Integer idproducto, Integer idauxiliar, Date fecha);

	// Saldo para un opa ultimas 48 horass
	@Query(value = "SELECT saldoec FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3"	
			+ " AND date(fecha) < ?4 ORDER BY fecha DESC limit 1 ", nativeQuery = true)
	Double findSaldoMasDe48Horas(Integer idorigenp, Integer idproducto, Integer idauxiliar, Date fecha);

	// ultimo movimiento
	@Query(value = "SELECT * FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 ORDER BY fecha DESC limit 1 ", nativeQuery = true)
	Auxiliares_d findUltimoRegistro(Integer idorigenp, Integer idproducto, Integer idauxiliar);

	//Ultimos 5 movimientos
	@Query(value = "SELECT date(fecha),cargoabono,monto,montoio,montoiva,montoim,montoivaim,transaccion,saldoec FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 ORDER BY fecha DESC limit 5 ", nativeQuery = true)
	List<Object[]> findUltimos5Movimientos(Integer idorigenp, Integer idproducto, Integer idauxiliar);	
	
	//moviminentos por rango de fechas y ordenacion por fecha ASC 
	@Query(value = "SELECT date(fecha),cargoabono,monto,montoio,montoiva,montoim,montoivaim,transaccion,saldoec FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 AND date(fecha) BETWEEN ?4 AND ?5 ORDER BY fecha ASC", nativeQuery =  true)
	List<Object[]> findMovimientosFechasAsc(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date FInicio,Date FFinal,Pageable pageable);
	
	//moviminentos por rango de fechas y ordenacion por fecha DESC bin
	@Query(value = "SELECT date(fecha),cargoabono,monto,montoio,montoiva,montoim,montoivaim,transaccion,saldoec FROM auxiliares_d WHERE idorigenp=?1 AND idproducto=?2 AND idauxiliar=?3 AND date(fecha) BETWEEN ?4 AND ?5 ORDER BY fecha DESC", nativeQuery =  true)
	List<Object[]> findMovimientosFechasDesc(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date FInicio,Date FFinal,Pageable pageable);
		
	@Query(value = "SELECT * FROM auxiliares_d WHERE idorigenp=?1  AND idproducto=?2 AND idauxiliar=?3 AND date(fecha) = ?4 AND replace(to_char(idorigenc,'099999')||to_char(idtipo,'09')||to_char(idpoliza,'09999'),' ','')= ?5", nativeQuery = true)
	List<Auxiliares_d> findAuxiliares_dByOpaAndFechaAndPoliza(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date fecha,String poliza);
	
	@Query(value = "SELECT * FROM auxiliares_d WHERE transaccion=?1", nativeQuery = true)
	Auxiliares_d findMovimientosByIdTransaccion(Integer transaccion);
	
	
	
	
	
	//Para transacciones
	@Query(value ="SELECT sum(monto) FROM auxiliares_d ad"
    + " INNER JOIN auxiliares a USING(idorigenp,idproducto,idauxiliar)"
    + " INNER JOIN productos pr USING(idproducto)"
    + " WHERE estatus=2"
    + " AND a.idorigen=?1"
    + " AND a.idgrupo=?2"
    + " AND a.idsocio=?3"
    + " AND pr.tipoproducto=0"
    + " AND ad.cargoabono=1"
    + " AND ad.date(fecha)=?4",nativeQuery = true )
    Double montoPesos(Integer idorigen, Integer idgrupo,Integer idsocio,Date fecha);
	
	@Query(value = "SELECT sum(monto) FROM auxiliares_d ad"
    + " INNER JOIN auxiliares a USING(idorigenp,idproducto,idauxiliar)"
    + " INNER JOIN productos pr USING(idproducto)"
    + " WHERE a.estatus=2"
    + " AND a.idorigen=?1"
    + " AND a.idgrupo=?2"
    + " AND a.idsocio=?3"
    + " AND pr.tipoproducto=0"
    + " AND ad.periodo=?4"
    + " AND ad.cargoabono=1", nativeQuery = true)
	Double montoUdis(Integer idorigen,Integer idgrupo,Integer idsocio,String periodo);  
}
