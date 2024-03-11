package com.fenoreste.rest.services;

import java.util.Date;
import java.util.List;

import com.fenoreste.rest.entity.MovimientosPK;
import com.fenoreste.rest.entity.RegistraMovimiento;


public interface IProcesaMovimientoService {
    
	public boolean insertarMovimiento(RegistraMovimiento mov);
	public void eliminaMovimiento(String sesion,String referencia);
	public List<RegistraMovimiento> buscar(Integer idorigen,Integer idgrupo,Integer idsocio);
	public void eliminaMovimientoTodos(Integer idorigen,Integer idgrupo,Integer idsocio);
	
	public void save(Date fecha,
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
	
}
