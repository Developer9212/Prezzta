package com.fenoreste.rest.services;

import com.fenoreste.rest.entity.CalificacionBackendPrezzta;

public interface ICalificacionBackendPrezztaService {

	public void insertarRegistrosCalificacion(Integer idorigenp,Integer idproducto,Integer idauxiliar,Double monto,String confirmacion);
	public void updateCalificacion(Integer idorigenp,Integer idproducto,Integer idauxiliar,String confirmacion);
	public CalificacionBackendPrezzta findCalificacionByOpa(Integer idorigenp,Integer idproducto,Integer idauxiliar);
}
