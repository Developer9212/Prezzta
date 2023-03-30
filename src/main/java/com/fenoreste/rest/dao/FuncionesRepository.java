package com.fenoreste.rest.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fenoreste.rest.entity.Persona;

public interface FuncionesRepository extends JpaRepository<Persona,Integer>{
	
	@Query(value="SELECT * FROM sai_prezzta_devuelve_condiciones_apertura(?1,?2,?3)",nativeQuery = true)
	String monto_a_prestar(Integer idorigen,Integer idgrupo,Integer idsocio);
    
	@Query(value="SELECT sai_prezzta_crea_apertura('{'?1'}')",nativeQuery = true)
	String apertura_opa(ArrayList<String>arr);
	
	@Query(value ="SELECT sai_bankingly_aplica_transaccion(?1,?2,?3,?4)",nativeQuery = true)
	String sai_procesa_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);

	@Query(value="SELECT cheque_y_poliza_del_prestamo(?1,?2,?3, 1)",nativeQuery = true)
	String poliza(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	@Query(value="SELECT sai_bankingly_termina_transaccion(?1,?2,?3,?4)",nativeQuery = true)
	String sai_bankingly_termina_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);
	
	@Query(value = "SELECT sai_prezzta_cancela_prestamo_rechazado(?1,?2,?3)",nativeQuery = true)
	String eliminarAutorizado(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	
	
}
