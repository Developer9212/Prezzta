package com.fenoreste.rest.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenoreste.rest.dao.ProcesaMovimientoRepository;
import com.fenoreste.rest.entity.RegistraMovimiento;


@Service
public class ProcesaMovimientoServiceImpl implements IProcesaMovimientoService{
     
	
	@Autowired
	private ProcesaMovimientoRepository procesarMovDao;
	
	@Override
	@Transactional
	public boolean insertarMovimiento(RegistraMovimiento mov) {
	  procesarMovDao.saveAndFlush(mov);
	  return true;
	}

	@Override
	public void eliminaMovimiento(String sesion,String referencia) {			
		 try {
			 procesarMovDao.eliminarRegistros(sesion, referencia);
	        } catch (EmptyResultDataAccessException ex) {
	            // Manejar el caso en el que el registro no fue encontrado
	            System.err.println("El registro con la sesion: " + sesion + " no existe.");
	        } catch (Exception ex) {
	            // Manejar otros errores inesperados
	            System.err.println("Error al eliminar el registro con la sesion:" + sesion + ": " + ex.getMessage());
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
	@Transactional
	public void eliminaMovimientoTodos(Integer idorigen, Integer idgrupo, Integer idsocio) {		
			procesarMovDao.eliminarRegistrosTodos(idorigen, idgrupo, idsocio);		
	}

	

	

}
