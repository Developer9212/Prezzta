package com.fenoreste.rest.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.FuncionesRepository;

@Service
public class FuncionesServiceImpl implements IFuncionesService{
	
	@Autowired
	FuncionesRepository funcionesRepository;
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public String validacion_monto_prestar(Integer idorigen, Integer idgrupo, Integer idsocio) {
		return funcionesRepository.monto_a_prestar(idorigen, idgrupo, idsocio);
	}

	@Override
	public String aperturar_opa(Integer idorigen, Integer idgrupo, Integer idsocio, Double monto, Integer plazos,Integer idproducto,String opa,Integer idorigenp) {
	  String query = "SELECT sai_prezzta_crea_apertura('{"+idorigen+","+idgrupo+","+idsocio+","+monto+","+plazos+","+idproducto+","+opa+","+idorigenp+"}')";
	  String resultado = "";
	  try {
		 Connection con = jdbc.getDataSource().getConnection();
		 Statement st = con.createStatement();
		 ResultSet rs=st.executeQuery(query);
		 while(rs.next()) {
		 	resultado = rs.getString(1);
		 }		
	   } catch (SQLException e) {
		 System.out.println("Error al ejecutar funcion aperturar prestamo:"+e.getMessage());
	 }	
	  return resultado;
	}

	@Override
	public String sai_aplica_transaccion(Date fecha, Integer idusuario, String sesion, String referencia) {
		// TODO Auto-generated method stub
		return funcionesRepository.sai_procesa_transaccion(fecha, idusuario, sesion, referencia);
	}

	@Override
	public String obtenerPoliza(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		return funcionesRepository.poliza(idorigenp,idproducto,idauxiliar);
	}

	@Override
	public String terminar_transaccion(Date fecha, Integer idusuario, String sesion, String referencia) {
		return funcionesRepository.sai_bankingly_termina_transaccion(fecha, idusuario, sesion, referencia);
	}

	@Override
	public String eliminarAutorizado(Integer idorigenp, Integer idproducto, Integer idauxiliar) {
		return funcionesRepository.eliminarAutorizado(idorigenp, idproducto, idauxiliar);
	}

	@Override
	public boolean servicioActivoInactivo() {
		return funcionesRepository.horaActividad();
	}

	@Override
	public boolean servicioActivoInactivoBackend() {
		return funcionesRepository.horaActividadBackend();
	}
}
