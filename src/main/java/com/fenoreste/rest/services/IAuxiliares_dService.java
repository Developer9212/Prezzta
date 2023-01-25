package com.fenoreste.rest.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.AuxiliaresD;


@Service
public interface IAuxiliares_dService {

	public List<AuxiliaresD> findAuxiliares_dByOpa(Integer idorigenp, Integer idproducto, Integer idauxiliar);
	public List<AuxiliaresD> findAuxiliares_dByOpaFecha(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date fechaI, Date fechaF);
	public Double findSaldoUltimas24Horas(Integer idorigenp, Integer idproducto, Integer idauxiliar, Date fecha);
	public Double findSaldoUltimas48Horas(Integer idorigenp, Integer idproducto, Integer idauxiliar, Date fecha);
	public Double findSaldoMasDe48Horas(Integer idorigenp, Integer idproducto, Integer idauxiliar, Date fecha);
	public AuxiliaresD findUltimoRegistro(Integer idorigenp, Integer idproducto, Integer idauxiliar);
	public List<Object[]> findUltimos5Movimientos(Integer idorigenp, Integer idproducto, Integer idauxiliar);	
	public List<Object[]> findMovimientosFechasDesc(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date FInicio,Date FFinal,Pageable pageable);
	public List<Object[]> findMovimientosFechasAsc(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date FInicio,Date FFinal,Pageable pageable);
	
	public  Double montoPesos(Integer idorigen, Integer idgrupo,Integer idsocio,Date fecha);
	public  Double montoUdis(Integer idorigen,Integer idgrupo,Integer idsocio,String periodo);	
	public AuxiliaresD findByTransaccion(Integer transaccion);
	public List<AuxiliaresD> findAuxiliares_dByOpaAndFechaAndPoliza(Integer idorigenp, Integer idproducto, Integer idauxiliar,Date fecha,String poliza);
	
		
}
