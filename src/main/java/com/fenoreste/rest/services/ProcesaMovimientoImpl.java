package com.fenoreste.rest.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenoreste.rest.dao.ProcesaMovimientoRepository;
import com.fenoreste.rest.entity.RegistraMovimiento;


@Service
public class ProcesaMovimientoImpl implements IProcesaMovimientoService{
     
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	ProcesaMovimientoRepository procesarMovDao;
	
	@Override
	@Transactional
	public boolean insertarMovimiento(RegistraMovimiento mov) {
	  procesarMovDao.saveAndFlush(mov);
	  return true;
	}

	@Override
	@Transactional
	public void eliminaMovimiento(Date fecha,int idusuario,String sesion,String referencia) {		
			procesarMovDao.eliminarRegistros(fecha, idusuario, sesion, referencia);		
	     		
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
	@Transactional
	public void eliminaMovimientoTodos(Integer idorigen, Integer idgrupo, Integer idsocio) {		
			procesarMovDao.eliminarRegistrosTodos(idorigen, idgrupo, idsocio);		
	}

	

	

}
