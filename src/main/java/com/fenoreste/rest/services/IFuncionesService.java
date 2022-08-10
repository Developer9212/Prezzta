package com.fenoreste.rest.services;

import java.util.Date;

public interface IFuncionesService {
   
	public String sai_aplica_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);
	public String validacion_monto_prestar(Integer idorigen,Integer idgrupo,Integer idsocio);
	public String aperturar_opa(Integer idorigen,Integer idgrupo,Integer idsocio,Double monto,Integer plazos,Integer idproducto,String opa,Integer idorigenp);
	public String obtenerPoliza(Integer idorigenp,Integer idproducto,Integer idauxiliar);
	public String terminar_transaccion(Date fecha,Integer idusuario,String sesion,String referencia);
	
	
}
