package com.fenoreste.rest.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.FuncionesRepository;
import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.modelos.PagoMitrasDTO;

@Service
public class FuncionesServiceImpl implements IFuncionesService{
	
	@Autowired
	private FuncionesRepository funcionesRepository;
	
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public String validacion_monto_prestar(Integer idorigen, Integer idgrupo, Integer idsocio) {
		return funcionesRepository.monto_a_prestar(idorigen, idgrupo, idsocio);
	}
	
	@Override
	public String validacion_monto_prestar_2(Integer idorigen, Integer idgrupo, Integer idsocio) {
		return funcionesRepository.monto_a_prestar_2(idorigen, idgrupo, idsocio);
	}

	@Override
	public String aperturar_opa(Integer idorigen, Integer idgrupo, Integer idsocio, Double monto, Integer plazos,Integer idproducto,String opa,Integer idorigenp) {
		String query = "SELECT sai_prezzta_crea_apertura('{"+idorigen+","+idgrupo+","+idsocio+","+monto+","+plazos+","+idproducto+","+opa+","+idorigenp+"}')";
		System.out.println("Query: " + query);
		String resultado = "";
		try {
			Connection con = jdbc.getDataSource().getConnection();
			Statement st = con.createStatement();
			ResultSet rs=st.executeQuery(query);
			while(rs.next()) {
				resultado = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar funcion aperturar prestamo: " + e.getMessage());
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

	@Override
	public PagoMitrasDTO pagoMitras(AuxiliarPK pk,int idamortizacion) {
		Object objeto = funcionesRepository.listaPagos(pk.getIdorigenp(),pk.getIdproducto(),pk.getIdauxiliar(),idamortizacion);
		
		PagoMitrasDTO pago = null;
		if(objeto !=null) {
			Object[] arrayObjetos = (Object[]) objeto;
			
			for(int x = 0;x<arrayObjetos.length;x++) {
				pago = new PagoMitrasDTO();
				pago.setIdorigenp(Integer.parseInt(arrayObjetos[0].toString()));
				pago.setIdproducto(Integer.parseInt(arrayObjetos[1].toString()));
				pago.setIdauxiliar(Integer.parseInt(arrayObjetos[2].toString()));
				pago.setIdamortizacion(Integer.parseInt(arrayObjetos[3].toString()));
				pago.setVence(arrayObjetos[4].toString());
				pago.setSaldo(Double.parseDouble(arrayObjetos[5].toString()));
				pago.setAbono(Double.parseDouble(arrayObjetos[6].toString()));
				pago.setIod(Double.parseDouble(arrayObjetos[7].toString()));
				pago.setIva_iod(Double.parseDouble(arrayObjetos[8].toString()));
				pago.setTotal_iod(Double.parseDouble(arrayObjetos[9].toString()));
				pago.setIo(Double.parseDouble(arrayObjetos[10].toString()));
				pago.setIva_io(Double.parseDouble(arrayObjetos[11].toString()));
				pago.setTotal_io(Double.parseDouble(arrayObjetos[12].toString()));
				pago.setDescuento(Double.parseDouble(arrayObjetos[13].toString()));
				
			}
		}
		return pago;
	}
	
	@Override
	public Double lineaCredito_Monto(Integer idorigen, Integer idgrupo, Integer idsocio) {
		return funcionesRepository.lineaCreditoMonto(idorigen, idgrupo, idsocio);
	}
	
	@Override
	public String monto_a_letras(String monto) {
		return funcionesRepository.sai_importe_en_letras(monto);
	}
	
}
