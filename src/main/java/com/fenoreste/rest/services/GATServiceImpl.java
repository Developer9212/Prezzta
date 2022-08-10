package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.GATRepository;

@Service
public class GATServiceImpl implements IGatService{
    
	@Autowired
	GATRepository gatDao;
	
	@Override
	public void insertRegistros(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
        try {
        	gatDao.insertarRegistros(idorigenp, idproducto, idauxiliar);	
		} catch (Exception e) {
			System.out.println("Error al insertar registros CAT:"+e.getMessage());
		}
		
	}

	@Override
	public Double calculoGAT(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		return gatDao.cat(idorigenp, idproducto, idauxiliar);
	}

	@Override
	public void removeRegistros(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		try {
			gatDao.eliminarRegistros(idorigenp, idproducto, idauxiliar);	
		} catch (Exception e) {
			System.out.println("Error al eliminar registros CAT:"+e.getMessage());
		}
		
	}

	
}
