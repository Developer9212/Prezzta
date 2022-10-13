package com.fenoreste.rest.services;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.ProcesaMovimientoRepository;
import com.fenoreste.rest.entity.RegistraMovimiento;


@Service
public class ProcesaMovimientoImpl implements IProcesaMovimientoService{
     
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	ProcesaMovimientoRepository procesarMovDao;
	
	@Override
	public boolean insertarMovimiento(RegistraMovimiento mov) {
	  procesarMovDao.saveAndFlush(mov);
	 
	  return true;
	}

	@Override
	public void eliminaMovimiento(Date fecha,int idusuario,String sesion,String referencia) {
		try {
			procesarMovDao.eliminarRegistros(fecha, idusuario, sesion, referencia);
		} catch (Exception e) {
			
		}
	     		
	}

	@Override
	public void save(Date fecha, Integer idusuario, String sesion, String referencia, Integer idorigen, Integer idgrupo,
			Integer idsocio, Integer idorigenp, Integer idproducto, Integer idauxiliar, Integer cargoabono,
			Double monto, Double iva, Integer tipo_amortizacion, String sai) {
	//procesarMovDao.saves(fecha, idusuario, sesion, referencia, idorigen, idgrupo, idsocio, idorigenp, idproducto, idauxiliar, cargoabono, monto, iva, tipo_amortizacion, sai);
		
	}

	@Override
	public List<RegistraMovimiento> buscar(Integer idorigen, Integer idgrupo, Integer idsocio) {		 
		return procesarMovDao.movimientosAll(idorigen, idgrupo, idsocio);
	}

	@Override
	public void eliminaMovimientoTodos(Integer idorigen, Integer idgrupo, Integer idsocio) {
		try {
			procesarMovDao.eliminarRegistrosTodos(idorigen, idgrupo, idsocio);
		} catch (Exception e) {
			System.out.println("Error al eliminar todos los movimientos:"+e.getMessage());
		}
		
	}

	

	

}
