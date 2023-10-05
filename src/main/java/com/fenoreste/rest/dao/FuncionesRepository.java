package com.fenoreste.rest.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.modelos.PagoMitrasDTO;

public interface FuncionesRepository extends JpaRepository<Persona,Integer>{
	
	@Query(value="SELECT * FROM sai_prezzta_devuelve_condiciones_apertura(?1,?2,?3)",nativeQuery = true)
	public String monto_a_prestar(Integer idorigen,Integer idgrupo,Integer idsocio);
    
	@Query(value="SELECT sai_prezzta_crea_apertura('{'?1'}')",nativeQuery = true)
	public String apertura_opa(ArrayList<String>arr);
	
	@Query(value ="SELECT sai_bankingly_aplica_transaccion(?1,?2,?3,?4)",nativeQuery = true)
	public String sai_procesa_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);

	@Query(value="SELECT cheque_y_poliza_del_prestamo(?1,?2,?3, 1)",nativeQuery = true)
	public String poliza(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value="SELECT sai_bankingly_termina_transaccion(?1,?2,?3,?4)",nativeQuery = true)
	public String sai_bankingly_termina_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);
	
	@Query(value = "SELECT sai_prezzta_cancela_prestamo_rechazado(?1,?2,?3)",nativeQuery = true)
	public String eliminarAutorizado(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value = "select * from sai_lista_pagos_con_descuento(?1, ?2, ?3) WHERE idamortizacion = ?4",nativeQuery = true)
	public Object listaPagos(Integer idorigenp,Integer idproducto,Integer idauxiliar,Integer idamortizacion);
	
	@Query(value = "select prezzta_servicio_activo_inactivo()",nativeQuery = true)
	public boolean horaActividad();
	
	@Query(value = "select prezzta_servicio_activo_inactivo_backend()",nativeQuery = true)
	public boolean horaActividadBackend();
	
	
	
}
