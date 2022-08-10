package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.CalificacionBackendPrezztaRepository;
import com.fenoreste.rest.entity.CalificacionBackendPrezzta;

@Service
public class CalificacionBackendPrezztaServiceImpl implements ICalificacionBackendPrezztaService{

	@Autowired
	CalificacionBackendPrezztaRepository calificacionBackendRepository;
	@Override
	public void insertarRegistrosCalificacion(Integer idorigenp, Integer idproducto, Integer idauxiliar,Double monto,String confirmacion) {
		
		try {
			calificacionBackendRepository.insertarCalificacionBackendPrezzta(idorigenp, idproducto, idauxiliar, monto, confirmacion);
			
		} catch (Exception e) {
			System.out.println("Error al insertar calificacion de backend:"+e.getMessage());
			
		}
	}

	@Override
	public CalificacionBackendPrezzta findCalificacionByOpa(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		
		return calificacionBackendRepository.findByOPA(idorigenp, idproducto, idauxiliar);
	}

	@Override
	public void updateCalificacion(Integer idorigenp, Integer idproducto, Integer idauxiliar,String confirmacion) {
		try {
			calificacionBackendRepository.updateRegistros(idorigenp, idproducto, idauxiliar,confirmacion);
			
		} catch (Exception e) {
			System.out.println("Error al insertar calificacion de backend:"+e.getMessage());
			
		}
		
	}

}
