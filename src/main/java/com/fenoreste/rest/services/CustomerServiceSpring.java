package com.fenoreste.rest.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.Util.ConversionMonedaLetras;
import com.fenoreste.rest.Util.FicheroConexion;
import com.fenoreste.rest.Util.HerramientasUtil;
import com.fenoreste.rest.consumoExterno.Alestra_Tarjetas_Debito;
import com.fenoreste.rest.consumoExterno.SMS_Csn;
import com.fenoreste.rest.consumoExterno.Siscore_Calificacion_Score;
import com.fenoreste.rest.entity.Amortizacion;
import com.fenoreste.rest.entity.Auxiliar;
import com.fenoreste.rest.entity.Auxiliares_d;
import com.fenoreste.rest.entity.CalificacionBackendPrezzta;
import com.fenoreste.rest.entity.CatalogoMenus;
import com.fenoreste.rest.entity.Colonias;
import com.fenoreste.rest.entity.Estados;
import com.fenoreste.rest.entity.FoliosTarjetas;
import com.fenoreste.rest.entity.Municipios;
import com.fenoreste.rest.entity.Negociopropio;
import com.fenoreste.rest.entity.Origenes;
import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.Referencias;
import com.fenoreste.rest.entity.Referenciasp;
import com.fenoreste.rest.entity.RegistraMovimiento;
import com.fenoreste.rest.entity.Socioeconomicos;
import com.fenoreste.rest.entity.Tablas;
import com.fenoreste.rest.entity.Trabajo;
import com.fenoreste.rest.entity.tmp_aperturas;
import com.fenoreste.rest.modelos.BanderasObjeto;
import com.fenoreste.rest.modelos.BanderasSiscore;
import com.fenoreste.rest.modelos.DetallesScore;
import com.fenoreste.rest.modelos.DetallesSiscore;
import com.fenoreste.rest.modelos.InfoDetallesSiscoreDTO;
import com.fenoreste.rest.modelos.PrestamoCreadoDTO;
import com.fenoreste.rest.modelos.PrestamoEntregado;
import com.fenoreste.rest.modelos.PuntosScore;
import com.fenoreste.rest.modelos.avalDTO;
import com.fenoreste.rest.modelos.codeudorDTO;
import com.fenoreste.rest.modelos.comercioDTO;
import com.fenoreste.rest.modelos.conyugueDTO;
import com.fenoreste.rest.modelos.dataDTO;
import com.fenoreste.rest.modelos.gastosDTO;
import com.fenoreste.rest.modelos.negocioDTO;
import com.fenoreste.rest.modelos.ogsDTO;
import com.fenoreste.rest.modelos.opaDTO;
import com.fenoreste.rest.modelos.primerEmpleoDTO;
import com.fenoreste.rest.modelos.propiedadesDTO;
import com.fenoreste.rest.modelos.referenciasDTO;
import com.fenoreste.rest.modelos.referenciasLaboralesDTO;
import com.fenoreste.rest.modelos.referenciasPersonalesDTO;
import com.fenoreste.rest.modelos.relacionesSociosDTO;
import com.fenoreste.rest.modelos.segundoEmpleoDTO;
import com.fenoreste.rest.modelos.validacionDTO;
import com.github.cliftonlabs.json_simple.JsonObject;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class CustomerServiceSpring {
	
	@Autowired
	IPersonaService personaService;
	
	@Autowired
	ITablasService tablasService;
	
	@Autowired
	IAuxiliaresService auxiliaresService;
	
	@Autowired
	ISocioeconomicosService socioeconomicosService;
	
	@Autowired
	ITrabajoService trabajosService;
	
	@Autowired
	ICatalogoMenusService catalogoMenuService;
	
	@Autowired
	IColoniaService coloniaService;
	
	@Autowired
	IMunicipioService municipioService;
	
	@Autowired 
	IEstadoService estadoService;
	
	@Autowired
	IReferenciaService referenciaService;
	
	@Autowired
	INegocioPropioService negocioService;
	
	@Autowired
	IFuncionesService funcionesService;
	
	@Autowired
	IAmortizacionesService amortizacionesService;
	
	@Autowired
	Siscore_Calificacion_Score siscoreCsn;
	
	@Autowired
	IGatService gatService;
	
	@Autowired
	ICalificacionBackendPrezztaService calificacionBackendService;
	
	@Autowired
	IOrigenesService origenesService;
	
	@Autowired
    IOtrosService otrosService;
	
	@Autowired
    IProcesaMovimientoService procesaMovimientoService;	
	
	@Autowired
	ITmpAperturasService tmpService;

	@Autowired
	IAuxiliares_dService auxiliares_dService;
	
	@Autowired
	private Alestra_Tarjetas_Debito consumoTddService; 
	
	@Autowired
    IFoliosTarjetasService foliosTarjetasService;	
	
	@Autowired
	private SMS_Csn smsCsn;
	
	
	@Autowired
	IReferenciaspService referenciaspService;
	
	
	public dataDTO informacionPersona(String tipoDocumento,String numeroDocumento) {
		dataDTO response = new dataDTO();
		Origenes origen = origenesService.findMatrizOrigen();
		try {
			List rangos = new ArrayList<>();
		//Buscamos a la persona con los datos que llegaron en el metodo
		Persona persona = personaService.findPersonaByDocumento(tipoDocumento, numeroDocumento);
		if(persona != null) {
			//Para saber si es socio
			Tablas tb_producto_parte_social = tablasService.findIdtablaAndIdelemento("prezzta", "parte_social");
			if(tb_producto_parte_social != null) {
				Auxiliar folio_parte_social = auxiliaresService.AuxiliarByOgsIdproducto(persona.getIdorigen(), persona.getIdgrupo(),persona.getIdsocio(),Integer.parseInt(tb_producto_parte_social.getDato1()));
				if(folio_parte_social.getSaldo().doubleValue() >= Double.parseDouble(tb_producto_parte_social.getDato2())) {
					response.setEs_socio("1");//atributo es socio
					response.setOgs(String.format("%06d",persona.getIdorigen())+String.format("%02d",persona.getIdgrupo())+String.format("%06d",persona.getIdsocio()));//campo ogs
					response.setPrimer_nombre(persona.getNombre());//atributo primer nombre
                    response.setApellidos(persona.getAppaterno()+" "+persona.getApmaterno());//atributo apellidos
                    response.setNumero_documento(persona.getCurp());//atributo numero de documento
                    response.setTelefono_Celular(persona.getCelular());//atributo telefono_celular
                    response.setFecha_nacimiento(persona.getFechanacimiento().toString());//atributo fecha_nacimiento
                    response.setLugar_nacimiento(persona.getLugarnacimiento());//atributo lugar_nacimiento
                    response.setRfc(persona.getRfc());
                    Socioeconomicos sc = socioeconomicosService.findByOgs(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
                    response.setPersonas_viven_en_casa(sc.getDependientes().toString());//Personas que viven en casa
                    primerEmpleoDTO primerempleo = new primerEmpleoDTO();
                    segundoEmpleoDTO segundoempleo = new segundoEmpleoDTO();
                    List<Trabajo>listaTrabajos = trabajosService.findTrabajosActivosByOgs(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio(),2);
                    int cons_tr_ac = 0; //consecutivo trabajo actual
                    for(int i = 0;i<listaTrabajos.size();i++) {
                    	Trabajo trabajo = listaTrabajos.get(i); 
                		CatalogoMenus menu = catalogoMenuService.findByMenuOpcion("giro_empresa", trabajo.getGiro_empresa());
                		Colonias colonia = coloniaService.findById(trabajo.getIdcolonia());
                		Municipios municipio = municipioService.findById(colonia.getIdmunicipio());
                		Estados estado = estadoService.findById(municipio.getIdestado());
                    	if(i == 0) {    
                    		cons_tr_ac = trabajo.getConsecutivo();
                    		 if (trabajo.getOcupacion_numero() != null) {
                                 	 primerempleo.setOcupacion(trabajo.getOcupacion()); 
                                 }
                    		     else {
                    		    	 primerempleo.setOcupacion(null); 
                    		     }
                                 primerempleo.setPuesto(trabajo.getPuesto());
                                 primerempleo.setTelefono(trabajo.getTelefono());
                                 primerempleo.setGiro(menu.getDescripcion());
                                 primerempleo.setCodigo_postal(colonia.getCodigopostal());
                                 primerempleo.setCalle(trabajo.getCalle());
                                 primerempleo.setNumero_exterior(trabajo.getNumero());
                                 primerempleo.setNumero_interior(null);
                                 primerempleo.setColonia(colonia.getNombre());
                                 primerempleo.setMunicipio(municipio.getNombre());
                                 primerempleo.setEstado(estado.getNombre());                             
                    	}else if(i == 1) {
                    		 	 segundoempleo.setOcupacion(trabajo.getOcupacion());
                    		 	 segundoempleo.setPuesto(trabajo.getPuesto());
                    		 	 segundoempleo.setTelefono(trabajo.getTelefono());
                             	 segundoempleo.setNombre_empresa(trabajo.getNombre());
                             	 segundoempleo.setAntiguedad(String.valueOf(cal_edad((Date) trabajo.getFechaingreso())));
                             	 segundoempleo.setGiro(menu.getDescripcion());
                             	 segundoempleo.setCodigo_postal(colonia.getCodigopostal());
                             	 segundoempleo.setCalle(trabajo.getCalle());
                             	 segundoempleo.setNumero_exterior(trabajo.getNumero());
                             	 segundoempleo.setNumero_interior(null);
                             	 segundoempleo.setColonia(colonia.getNombre());
                             	 segundoempleo.setMunicipio(municipio.getNombre());
                             	 segundoempleo.setEstado(estado.getNombre());
                    	}
                    }//Fin del bucle para trabajos
                    //Buscamos el trabajo actual para saber si esta jubilado
                    
                    Trabajo trabajo = trabajosService.findTrabajoByOgsAndConsecutivo(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio(),cons_tr_ac);
                    if(trabajo.getPuesto().toUpperCase().contains("JUBILADO")){
                        response.setJubilado("1");//atributo jubilado (obtenido de sus trabajos)
                    }else{
                        response.setJubilado("0");
                    }
                    if(trabajo.getFechaingreso() == null) {
                        response.setFecha_ingreso_laboral("");
                    } else {
                        response.setFecha_ingreso_laboral(dateToString(trabajo.getFechaingreso()));//atributo fecha ingreso laboral 
                    }
                    response.setIngresos_mensuales(trabajo.getIng_mensual_neto().toString());//atributo ingresos mensuales
                    Colonias colonia = coloniaService.findById(persona.getIdcolonia());
                    if(colonia != null) {
                    	Municipios municipio = municipioService.findById(colonia.getIdmunicipio());
               			Estados estado = estadoService.findById(municipio.getIdestado());
               			response.setEstado(estado.getNombre());//atributo estado
                        response.setCiudad(municipio.getNombre());//atributo ciudad
                        response.setDireccion(persona.getCalle() + " " + persona.getNumeroext());//atributo direccion
                        response.setCodigo_postal(colonia.getCodigopostal());//atributo codigo postal
                    }           			
                    if (sc.getPropietariovivienda() != null){
                        response.setPropiedad_vivienda(sc.getPropietariovivienda());//atributo propiedad vivienda
                    } else {
                    	response.setPropiedad_vivienda(null);
                    }
                    response.setEmail(persona.getEmail());//atributo email
                    CatalogoMenus menu_estudio = catalogoMenuService.findByMenuOpcion("grado_estudios",persona.getGradoEstudios().intValue());
                    response.setUltimo_estudio_cursado(menu_estudio.getDescripcion());//atributo ultimo estudio cursado
                    response.setTelefono_auxiliar(persona.getTelefono());//atributo telefono auxiliar
                    if(sc.getFechahabitacion() == null) {
                    response.setAntiguedad_domicilio("");
                    } else {
                    response.setAntiguedad_domicilio(String.valueOf(restaFechas(sc.getFechahabitacion())));//atributo antiguedad en domicilio 
                    Referencias referencia = referenciaService.finByOgsAndTipoReferencia(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio(),1);
                    if(referencia != null) {
                    response.setNum_socio_conyugue(String.format("%06d",referencia.getIdorigenr())+String.format("%02d",referencia.getIdgrupor())+String.format("%06d",referencia.getIdsocior()));//atributo num_socio_conyuge
                    } else {
                    response.setNum_socio_conyugue(null);
                    }
                    response.setCant_hijos(String.valueOf(sc.getDependientes_menores()));//atributo cantidad hijos
                    CatalogoMenus catalogo_giro_empresa = catalogoMenuService.findByMenuOpcion("giro_empresa",trabajo.getGiro_empresa());
                    response.setRubro_laboral(catalogo_giro_empresa.getDescripcion());//atributo rubro laboral
                    response.setTelefono_trabajo(trabajo.getTelefono());//atributo telefono trabajo
                    
                                       
                    //validamos cuanto alcanza en credito
				    validacionDTO validacion = new validacionDTO();
		            /*Se deberia consumir la funcion que nos da la validacion 
		            -rango de precios
		            -rango de plazos
		            -tipo_de apertura
		            -monto_a_renovar
		            */
				    String monto_maximo_prestar = funcionesService.validacion_monto_prestar(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
					String[] montos_array = monto_maximo_prestar.split("\\|");                    
		 		    rangos = Arrays.asList(montos_array);
		 			String plazoMin = "0",plazoMax="0",montoMin="0",montoMax="0";
		 			if(!monto_maximo_prestar.toUpperCase().contains("ERROR")) {
		 			   //Buscamos las tablas de mensajes 
		 			   Tablas tb_comision_servicio = null;		 			    	
		 			   Tablas tb_nota_comision = null; 
		 			   Tablas tb_nota_dispersion = tablasService.findIdtablaAndIdelemento("prezzta","nota_dispersion");
		 			   Tablas tb_nota_renovacion = tablasService.findIdtablaAndIdelemento("prezzta","nota_renovacion");
		 			   Tablas tb_nota_monto_atraso = tablasService.findIdtablaAndIdelemento("prezzta","nota_montos_atrasado");
		 			   
		 			   String mensajeApertura = "";		 			   	 			 
		 			   plazoMin = rangos.get(0).toString();
		 			   plazoMax = rangos.get(1).toString();
		 			   montoMin = rangos.get(2).toString();
		 			   montoMax = rangos.get(3).toString();
		 			   String tipoApertura = rangos.get(4).toString();
		 			   String totalRenovar = rangos.get(5).toString();
		 			   String totalAtraso = rangos.get(7).toString();
		 			   String idorigenp = rangos.get(10).toString();
		 			   
		 			  if(origen.getMatriz() == 30200) {
		 				  tb_comision_servicio = tablasService.findIdtablaAndIdelemento("prezzta","comision");
		 				  tb_nota_comision = tablasService.findIdtablaAndIdelemento("prezzta","nota_comision");
		 				 if(tipoApertura.toUpperCase().contains("R")) {
		 					mensajeApertura= tb_nota_comision.getDato2().replace("@comision@",String.valueOf(Double.parseDouble(tb_comision_servicio.getDato1()) + Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16))+","; 
		 				   }else {
		 					   mensajeApertura = tb_nota_comision.getDato2().replace("@comision@", tb_comision_servicio.getDato1())+",";
		 				   }			 			  
			 			}	
		 			   
		 			  validacion.setRangoMontos(montoMin+"-"+montoMax);
			          validacion.setRangoPlazos(plazoMin+"-"+plazoMax);
			          tmp_aperturas tmp_saver = new tmp_aperturas();
			          tmp_saver.setIdorigen(persona.getIdorigen());
       			   	  tmp_saver.setIdgrupo(persona.getIdgrupo());
       			   	  tmp_saver.setIdsocio(persona.getIdsocio());
       			   	  tmp_saver.setMontoalcanzado(Double.parseDouble(rangos.get(3).toString()));
       			   	  tmp_saver.setIdorigenp(Integer.parseInt(idorigenp));
       			   	  
       			   	  if(tipoApertura.toUpperCase().contains("R")) {
			        	  String[]cadena = rangos.get(9).toString().split("\\-");
			        	  String opaAnterior = String.format("%06d",Integer.parseInt(cadena[0]))+String.format("%05d",Integer.parseInt(cadena[1]))+String.format("%08d",Integer.parseInt(cadena[2]));
			        	  validacion.setTipo_apertura("Renovacion");
			        	  validacion.setMonto_renovar(Double.parseDouble(totalRenovar));
			        	  String mmensajeAtraseo = "",imprt = "";
			        	  if(Double.parseDouble(totalAtraso)>0) {
			        		  mmensajeAtraseo = tb_nota_monto_atraso.getDato2().replace("@atraso@",totalAtraso)+" al folio:"+opaAnterior+",";
			        		  imprt =", IMPORTANTE(Primero debe liquidar monto atrasado para poder continuar con su renovacion)";
			        	  }
			        	  validacion.setNota(mensajeApertura +
			        	                     tb_nota_renovacion.getDato2().replace("@renovacion@",totalRenovar)+","+mmensajeAtraseo+ 		
			        			             tb_nota_dispersion.getDato2().replace("@dispersion@", "montoSolicitado - "+totalRenovar+" - "+String.valueOf(Double.parseDouble(tb_comision_servicio.getDato1()) + Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16))+imprt);
	        			   
	        			
	        			  tmp_saver.setTipoapertura("Renovacion");
	        			  tmp_saver.setMontorenovar(Double.parseDouble(rangos.get(5).toString()));
	        			  tmp_saver.setAtrasado(Double.parseDouble(rangos.get(7).toString()));	
	        			  tmp_saver.setIdproducto(Integer.parseInt(rangos.get(8).toString()));
	        			  tmp_saver.setOpaactivo(rangos.get(9).toString());
	        			  double totallibre = tmp_saver.getMontorenovar()+Double.parseDouble(tb_comision_servicio.getDato1())+(Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16);
	        			  tmp_saver.setGastos_pagar(totallibre);
			          }else {
			        	  tmp_saver.setTipoapertura("Activacion");
			        	  tmp_saver.setIdproducto(Integer.parseInt(rangos.get(8).toString()));
			        	  tmp_saver.setOpaactivo("0-0-0");		
			        	  validacion.setTipo_apertura("Activacion");
			        	  validacion.setNota(mensajeApertura+
	        	                            tb_nota_dispersion.getDato2().replace("@dispersion@", "montoSolicitado - "+tb_comision_servicio.getDato1()));
			        	  validacion.setMonto_renovar(0.0);
			        	  double totallibre = Double.parseDouble(tb_comision_servicio.getDato1())+(Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16);
	        			  tmp_saver.setGastos_pagar(totallibre);			        	  
			          }		
       			   	  
			          tmp_saver = tmpService.guardar(tmp_saver);
			          
			         
			          //validacion.setNota(tb_nota_comision.getDato2().replace("@comision@", tb_comision_servicio.getDato1())+","+tb_nota_dispersion.getDato2().replace("@dispersion@", "montoSolicitado - "+tb_comision_servicio.getDato1()));
		 			}else {
		 			  validacion.setRangoMontos(montoMin+"-"+montoMax);
			          validacion.setRangoPlazos(plazoMin+"-"+plazoMax);
			          validacion.setTipo_apertura("");
			          validacion.setMonto_renovar(0);
			          validacion.setNota("NO PUEDE CONTINUAR CON SU SOLICITUD");
		 			}
		 			
		           
		           response.setMonto_maximo_a_prestar(validacion);//objeto monto maximo a prestar		            
                    
                    Negociopropio negocio_prop = negocioService.findByOgs(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
                    comercioDTO comercio = new comercioDTO();
                    if(negocio_prop != null) {
                    	response.setEs_socio_comercial("1");                    	
                        //Para obtener el giro del negocio hay que buscar en catalogo menus
                        CatalogoMenus catalogo_empresa_negocio = catalogoMenuService.findByMenuOpcion("giro_empresa",negocio_prop.getGiro_empresa());
                        
                        comercio.setGiro(catalogo_empresa_negocio.getDescripcion());//atributo giro comercio
                        comercio.setFecha_comienzo(String.valueOf(negocio_prop.getFechainicio()));
                        comercio.setDomicilio(negocio_prop.getCalle()+" "+negocio_prop.getNumeroext()+" "+negocio_prop.getNumeroint());
                        comercio.setTelefono(negocio_prop.getTelefono());
                        comercio.setIngreso_mensual(null);//Se manda vacio porque no se tiene contemplado en la base de datos un registro de ingresos
                        comercio.setOtros_ingresos(null);//Se manda vacio porque no se tiene contemplado en la base de datos un registro de ingresos
                        comercio.setFuente_otros_ingresos(null);//Se manda vacio porque no se tiene contemplado en la base de datos un registro de ingresos
                        
                    }else {
                    	response.setEs_socio_comercial("0");
                    	comercio.setGiro(null);//atributo giro comercio
                        comercio.setFecha_comienzo(null);
                        comercio.setDomicilio(null);
                        comercio.setTelefono(null);
                        comercio.setIngreso_mensual(null);//Se manda vacio porque no se tiene contemplado en la base de datos un registro de ingresos
                        comercio.setOtros_ingresos(null);//Se manda vacio porque no se tiene contemplado en la base de datos un registro de ingresos
                        comercio.setFuente_otros_ingresos(null);//Se manda vacio porque no se tiene contemplado en la base de datos un registro de ingresos
                        
                    }   
                    response.setComercio(comercio);//Objeto comercio
                    response.setFuente_ingresos_fijos("ingresos ordinarios");//Se define estatico(atributo ingresos fijos)
                    response.setMonto_ingresos_fijos(String.valueOf(sc.getIngresosordinarios()));//atributo monto ingresos fijos
                    response.setFuente_ingresos_variables("ingresos extraordinarios");//Se define estatico(atributo ingresos variables)
                    response.setMonto_ingresos_variables(String.valueOf(sc.getIngresosextraordinarios()));//atributo ingresos variables
                    //para los gastos si se obtiene de socioeconomicos pero se parametriza en tablas 
                    Tablas tb_gastos_alimentacion = tablasService.findIdtablaAndIdelemento("prezzta","gastos_alimentacion");
                    
                    if(tb_gastos_alimentacion != null) {
                      switch(tb_gastos_alimentacion.getDato1()) {
                        case "gastos_tipo1":
                    	    response.setGastos_alimentacion(String.valueOf(sc.getGastos_tipo1())); 
                    	   break;
                        case "gastos_tipo2":
                    	    response.setGastos_alimentacion(String.valueOf(sc.getGastos_tipo2())); 
                    	   break;
                        case "gastos_tipo3":
                    	    response.setGastos_alimentacion(String.valueOf(sc.getGastos_tipo3())); 
                    	   break;
                        case "gastos_tipo4":
                    	    response.setGastos_alimentacion(String.valueOf(sc.getGastos_tipo4())); 
                    	   break;
                        case "gastos_tipo5":
                    	     response.setGastos_alimentacion(String.valueOf(sc.getGastos_tipo5())); 
                        	break;
                        case "gastos_tipo6":
                    	     response.setGastos_alimentacion(String.valueOf(sc.getGastos_tipo6())); 
                    	    break;                    
                        }                    
                    }else{
                             response.setGastos_alimentacion("");
                    }
                  //para los gastos si se obtiene de socioeconomicos pero se parametriza en tablas 
                    Tablas tb_gastos_servicio = tablasService.findIdtablaAndIdelemento("prezzta","gastos_servicio");
                    
                    if(tb_gastos_servicio != null) {
                      switch(tb_gastos_servicio.getDato1()) {
                        case "gastos_tipo1":
                    	    response.setGastos_pagos_servicios(String.valueOf(sc.getGastos_tipo1())); 
                    	   break;
                        case "gastos_tipo2":
                    	    response.setGastos_pagos_servicios(String.valueOf(sc.getGastos_tipo2())); 
                    	   break;
                        case "gastos_tipo3":
                        	response.setGastos_pagos_servicios(String.valueOf(sc.getGastos_tipo3()));  
                    	   break;
                        case "gastos_tipo4":
                        	response.setGastos_pagos_servicios(String.valueOf(sc.getGastos_tipo4()));  
                    	   break;
                        case "gastos_tipo5":
                        	response.setGastos_pagos_servicios(String.valueOf(sc.getGastos_tipo5()));  
                        	break;
                        case "gastos_tipo6":
                        	response.setGastos_pagos_servicios(String.valueOf(sc.getGastos_tipo6()));  
                    	    break;                    
                        }                    
                    }else{
                             response.setGastos_pagos_servicios("");
                    }
                                        
                    referenciasDTO referencias = new referenciasDTO();
                    Referencias referencia_personal = referenciaService.finByOgsAndTipoReferencia(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio(),3);//Se busca la referencia 3 porque es referencia persona se definio estatico
                    //Buscamos la persona referenciada referencia tipo 3 personal
                    if(referencia_personal != null) {//Solo me aseguro que traiga datos la busqueda
                    	Persona persona_referencia = personaService.findByOgs(referencia_personal.getIdorigenr(),referencia_personal.getIdgrupor(),referencia_personal.getIdsocior());
                    	referencias.setNombre(persona_referencia.getNombre()+" "+persona_referencia.getAppaterno()+" "+persona_referencia.getApmaterno());
                        referencias.setDireccion(persona_referencia.getCalle()+" "+persona_referencia.getNumeroext()+" "+persona_referencia.getNumeroint());
                        referencias.setParentesco("Referencia personal");//Estatico definido por Lic.Eliseo
                        referencias.setTelefono(persona_referencia.getTelefono());
                    }else{
                    	referencias.setNombre(null);
                        referencias.setDireccion(null);
                        referencias.setParentesco(null);
                        referencias.setTelefono(null);
                    }
                    
                    
                    response.setReferencias(referencias);//objeto referencias                 
                    response.setPrimer_empleo(primerempleo);//objeto primer empleo se busco arriba
                    response.setSegundo_empleo(segundoempleo);//objeto segundo empleo se busco arriba                    
                    response.setFecha_ingreso_caja(String.valueOf(persona.getFechaingreso()));//atributo fecha ingreso a la caja
                    
                    negocioDTO negocio = new negocioDTO();
                    if(negocio_prop != null){
                    	           negocio.setIngresos(String.valueOf(negocio_prop.getUtilidad_mens()));
                                   negocio.setCompras(null);
                                   negocio.setPago_sueldos(null);
                                   negocio.setPago_renta(null);
                                   negocio.setPago_creditos(null);
                                   negocio.setOtros(null);
                                   negocio.setTotal_negocio(null);
                                   negocio.setHorario_dias_laborables(null);
                                   negocio.setDomicilio(negocio_prop.getCalle() + " " + negocio_prop.getNumeroext()+" "+negocio_prop.getNumeroint());
                                   negocio.setFuentes_otros_negocios(null);
                                   
                                   /*NOTA:para negocio se obtiene de negocio propio pero solo los campos que pueden recuperarse*/
                     }
                    response.setNegocio(negocio);//objeto negocio
                    response.setTotal_ingresos(String.valueOf(sc.getIngresosordinarios()));//atributo total ingresos
                    response.setOtros_ingresos(String.valueOf(sc.getIngresosextraordinarios()));//atributo otros ingresos
                    response.setFuentes_otros_ingresos("ingresos extraordinarios");//atributo fuente otros ingresos
                    
                    gastosDTO gastos = new gastosDTO();
                    //Obtenemos los gastos que estan parametrizados en tablas
                    Tablas tb_gastos = tablasService.findIdtablaAndIdelemento("prezzta","gastos");
                    double total_gastos = 0.0;
               
                    if(tb_gastos != null) {
                    String[] gastos_array = tb_gastos.getDato2().split("\\,");                    
        			List lista_gastos = Arrays.asList(gastos_array);
        			for(int i=0;i<lista_gastos.size();i++) {
        				switch(lista_gastos.get(i).toString()) {
        				case "gastos_tipo1":
        					gastos.setAlimentacion(String.valueOf(sc.getGastos_tipo1()));
        					if(sc.getGastos_tipo1() != null) total_gastos = total_gastos + sc.getGastos_tipo1();        					
        					break;
        				case "gastos_tipo2":
        					 gastos.setServicios_vivienda(String.valueOf(sc.getGastos_tipo2()));
        					 if(sc.getGastos_tipo2() != null) total_gastos = total_gastos + sc.getGastos_tipo2();
        					break;
        				case "gastos_tipo3":
        					gastos.setVestido_calzado(String.valueOf(sc.getGastos_tipo3()));
        					if(sc.getGastos_tipo3() != null) total_gastos = total_gastos + sc.getGastos_tipo3();
        					break;
        				case "gastos_tipo4":
        					gastos.setTransporte(String.valueOf(sc.getGastos_tipo4()));
        					if(sc.getGastos_tipo4() != null ) total_gastos = total_gastos + sc.getGastos_tipo4();
        					break;
        				case "gastos_tipo5":
        					gastos.setEscuela(String.valueOf(sc.getGastos_tipo5()));
        					if(sc.getGastos_tipo5() != null) total_gastos = total_gastos + sc.getGastos_tipo5();
        					break;
        				case "gastos_tipo6":
        					gastos.setDeudas(String.valueOf(sc.getGastos_tipo6()));
        					if(sc.getGastos_tipo6() != null) total_gastos = total_gastos + sc.getGastos_tipo6();
        					break;
        				}
        			  }
        			   gastos.setTotal_gastos(String.valueOf(total_gastos));//Atributo de objetos gastos(Total de gastos)
        			   
                    }else {
                    	gastos.setAlimentacion(null);
                        gastos.setVestido_calzado(null);
                        gastos.setServicios_vivienda(null);
                        gastos.setTransporte(null);
                        gastos.setEscuela(null);
                        gastos.setDeudas(null);
                    }                   
                    response.setGastos(gastos);//Objeto gastos
                  
                    propiedadesDTO propiedades = new propiedadesDTO();
                    List<propiedadesDTO> lista_prop = new ArrayList<>();                    
                    propiedades.setValor(String.valueOf(sc.getValorpropiedad()));
                    propiedades.setAdeudo(null);
                    propiedades.setSaldo(null);
                    propiedades.setUbicacion(null);
                    lista_prop.add(propiedades);
                    response.setPropiedades(lista_prop);//Objeto lista propiedades
                    
                    response.setTotal_valor_propiedades(String.valueOf(sc.getValorpropiedad()));//atributo valor propiedades
                    
                    conyugueDTO conyugue = new conyugueDTO();
                    int cons_tr_con = 0;
                    if(response.getNum_socio_conyugue() != ""){
                    	if(referencia != null) {
                        Persona persona_conyuge = personaService.findByOgs(referencia.getIdorigenr(), referencia.getIdgrupor(), referencia.getIdsocior());
                        List<Trabajo> lista_trabajo_conyuge = trabajosService.findTrabajosActivosByOgs(referencia.getIdorigenr(), referencia.getIdgrupor(), referencia.getIdsocior(),1);
                        if(lista_trabajo_conyuge.size() >0 ) {
                        	cons_tr_con= trabajo.getConsecutivo();                        	
                        }
                        Trabajo trabajo_consecutivo_conyuge = trabajosService.findTrabajoByOgsAndConsecutivo(referencia.getIdorigenr(), referencia.getIdgrupor(), referencia.getIdsocior(),cons_tr_con);
                       	Colonias colonia_conyuge = coloniaService.findById(persona_conyuge.getIdcolonia());
                        Municipios municipio_conyuge = municipioService.findById(colonia_conyuge.getIdmunicipio());
                        Estados estado_conyuge = estadoService.findById(municipio_conyuge.getIdestado());
                        conyugue.setEdad(String.valueOf(cal_edad(persona_conyuge.getFechanacimiento())));
                        conyugue.setDireccion(persona_conyuge.getCalle() + " " + persona_conyuge.getNumeroext());
                        if (trabajo_consecutivo_conyuge.getOcupacion() != null) { 
                        conyugue.setOcupacion(trabajo_consecutivo_conyuge.getOcupacion()); 
                        } else {
                        conyugue.setOcupacion(null);
                        }
                        conyugue.setCp(colonia_conyuge.getCodigopostal());
                        conyugue.setCiudad(municipio_conyuge.getNombre());
                        conyugue.setEstado(estado_conyuge.getNombre());
                        conyugue.setLugar_trabajo(trabajo_consecutivo_conyuge.getNombre());
                        if (trabajo_consecutivo_conyuge.getFechaingreso() == null) {
                        conyugue.setAntiguedad(null);
                        } else {
                        conyugue.setAntiguedad(String.valueOf(restaFechas(trabajo_consecutivo_conyuge.getFechaingreso())));
                        }
                        if (trabajo_consecutivo_conyuge.getCalle() != null) { 
                        conyugue.setDomicilio_empleo(trabajo_consecutivo_conyuge.getCalle() + " " + trabajo_consecutivo_conyuge.getNumero());
                        }else {
                        conyugue.setDomicilio_empleo(null); 
                        }
                        if (trabajo_consecutivo_conyuge.getTelefono() != null) {
                        conyugue.setTelefono_empleo(trabajo_consecutivo_conyuge.getTelefono());
                        } 
                        else { 
                        	conyugue.setTelefono_empleo(null); 
                        }
                        conyugue.setHistorial(null);
                      }
                    }
                    response.setConyugue(conyugue);//Atributo objeto conyugue
                    
                    List<referenciasPersonalesDTO> lista_referencias_response = new ArrayList<>();
                    List<Referencias> referencias_lista = referenciaService.findAll(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
                    for(int x=0;x<referencias_lista.size();x++) {
                        referenciasPersonalesDTO referenciasp = new referenciasPersonalesDTO();
                        Referencias referencia_entity = referencias_lista.get(x);
                       
                        Persona persona_referencia = personaService.findByOgs(referencia_entity.getIdorigenr(),referencia_entity.getIdgrupor(),referencia_entity.getIdsocior());
                        referenciasp.setNombre(persona_referencia.getNombre() + " " + persona_referencia.getAppaterno() + " " + persona_referencia.getApmaterno());
                        referenciasp.setDireccion(persona_referencia.getCalle() + " " + persona_referencia.getNumeroext());
                        CatalogoMenus catalogo_referencia = catalogoMenuService.findByMenuOpcion("referenciap",referencia_entity.getTiporeferencia());
                        referenciasp.setParentesco(catalogo_referencia.getDescripcion());
                        referenciasp.setTelefono(persona_referencia.getTelefono());                        
                        lista_referencias_response.add(referenciasp);
                    }
                   response.setReferencias_personales(lista_referencias_response);//Lista referencias
                   
                   referenciasLaboralesDTO referencias_laborales = new referenciasLaboralesDTO();
                   /*referencias_laborales.setNombre("NO DATO");
                   referencias_laborales.setDireccion("NO DATO");
                   referencias_laborales.setTiempo_conocerlo("NO DATO");
                   referencias_laborales.setTelefono("NO DATO");*/
                   response.setReferencias_laborales(referencias_laborales);//Lista referencias laborales vacia no se tiene datos
                   
                   relacionesSociosDTO relaciones_socios = new relacionesSociosDTO();
                   List<relacionesSociosDTO> listaRS = new ArrayList<>();
                   listaRS.add(relaciones_socios);                        
                   /*relaciones_socios.setNombre("NO DATO");
                   relaciones_socios.setNumero_socio("NO DATO");
                   relaciones_socios.setParentesco("NO DATO");*/
                   response.setRelaciones_socios(listaRS);//Relaciones socios vacio porque no se tiene dato a nivel saicoop
                   CatalogoMenus menu_regimen_mat = catalogoMenuService.findByMenuOpcion("regimen_mat",persona.getRegimenMat().intValue());
                   response.setRegimen_patrimonial(menu_regimen_mat.getDescripcion());
                   response.setNumero_dependientes(String.valueOf(sc.getDependientes() + sc.getDependientes_menores()));//Atributo numero dependientes
                   response.setTelefono_recados(persona.getTelefonorecados());//telefono recados
                   Auxiliar folio_ahorro = auxiliaresService.AuxiliarByOgsIdproducto(persona.getIdorigen(), persona.getIdgrupo(), persona.getIdsocio(),110);
                   response.setMonto_ahorro(String.valueOf(folio_ahorro.getSaldo()));//atributo monto ahorro
                   
                   response.setAntiguedad_socio(String.valueOf(cal_edad(persona.getFechaingreso())));//atributo antiguedad socio
                   response.setParte_social(String.valueOf(folio_parte_social.getSaldo()));
                   response.setFideicomiso(null);
                   response.setHistorial_cooperativa(null);
                   response.setLinea_credito(null);
                   response.setLinea_credito_letras(null);
                   response.setAseguradora(null);
                   response.setClausulas(null);
                   response.setModalidad_pago(null);
                   response.setTipo_operacion(null);
                   response.setDatos_del_poder(null);
                   response.setDisponibilidad(null);
                   response.setPorcentaje_capacidad(null);
                   
                   CatalogoMenus cmsex = catalogoMenuService.findByMenuOpcion("sexo",persona.getSexo().intValue());
                   response.setSexo(cmsex.getDescripcion());       
                   System.out.println("Exitoso");
				   }                    
			    }			      
		     }			
		  }
	   }catch(Exception e) {
			System.out.println("Error al llenar data result:"+e.getMessage());
		}
		
		return response;
	}
	
	
	public PrestamoCreadoDTO apeturaFolio(String num_socio,Double monto,int plazos) {
		boolean bandera = false;
		PrestamoCreadoDTO prestamo = null;		
		String montoCubrir = "",opaAnterior ="";
		try {			 		    
			prestamo = new PrestamoCreadoDTO();
			ogsDTO ogs = new HerramientasUtil().ogs(num_socio);
			//Obtenemos informacion que ya se valido
			tmp_aperturas tmp_validacion = tmpService.buscar(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio());
		    if(tmp_validacion != null) {
		    if((monto - tmp_validacion.getGastos_pagar())>0) {
		    	if(tmp_validacion.getTipoapertura().toUpperCase().contains("R")) {
					//Validacion que no halla monto atrasado				 
					String monto_maximo_prestar = funcionesService.validacion_monto_prestar(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio());
					System.out.println("Condiciones apertura:"+monto_maximo_prestar);
					String[] montos_array = monto_maximo_prestar.split("\\|");                    
		 		    List<String>rangos = Arrays.asList(montos_array);	
		 		    montoCubrir = rangos.get(7).toString();
	 			    String[]cadena = rangos.get(9).toString().split("\\-");
			        opaAnterior = String.format("%06d",Integer.parseInt(cadena[0]))+String.format("%05d",Integer.parseInt(cadena[1]))+String.format("%08d",Integer.parseInt(cadena[2]));
			        if(rangos.get(7).equals(".00")) {
			        	if(monto > tmp_validacion.getMontorenovar()) {
			        		bandera = true;	
			        	}	 		    	
		 		    }
				  }else {
					bandera = true;
				  }
		      }else {
		    	  prestamo.setNota("No se puede dispersar "+String.valueOf(monto-tmp_validacion.getGastos_pagar()));
		      }
		    }
			
			if(bandera) {
			if(tmp_validacion != null) {
				//Configuracion de apertura para obtener el origen
				if(monto <= tmp_validacion.getMontoalcanzado()) {
					String aperturar_opa = funcionesService.aperturar_opa(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio(), monto, plazos,tmp_validacion.getIdproducto(),tmp_validacion.getOpaactivo(),tmp_validacion.getIdorigenp());								
					System.out.println("Este es el idorigenp que esta buscando:"+tmp_validacion.getIdorigenp()+", el idproductoi:"+tmp_validacion.getIdproducto()+" , idauxiliar:"+Integer.parseInt(aperturar_opa.replace("|","").toString()));
					Auxiliar creado_aux = auxiliaresService.AuxiliarByOpa(tmp_validacion.getIdorigenp(),tmp_validacion.getIdproducto(),Integer.parseInt(aperturar_opa.replace("|","").toString()));
					prestamo.setOpa(String.format("%06d",creado_aux.getIdorigenp())+String.format("%05d",creado_aux.getIdproducto())+String.format("%08d",creado_aux.getIdauxiliar()));  
				    prestamo.setIdorigenp(String.valueOf(creado_aux.getIdorigenp()));
				    prestamo.setNumero_producto(String.valueOf(creado_aux.getIdproducto()));
				    prestamo.setIdauxiliar(String.valueOf(creado_aux.getIdauxiliar()));
					prestamo.setClasificacion_cartera(creado_aux.getCartera());
					prestamo.setFolio_prestamo(String.format("%06d",creado_aux.getIdorigenp())+String.format("%05d",creado_aux.getIdproducto())+String.format("%08d",creado_aux.getIdauxiliar()));
					Tablas tb_reca_por_producto = tablasService.findIdtablaAndIdelemento("numero_reca_por_producto",String.valueOf(tmp_validacion.getIdproducto()));
					prestamo.setReca_completo(tb_reca_por_producto.getDato2());
					String[]tb_reca_array_recortado = tb_reca_por_producto.getDato2().split("\\/");
					prestamo.setReca_recortado(tb_reca_array_recortado[0]);
					gatService.insertRegistros(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar());
					double cat = gatService.calculoGAT(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar());
					gatService.removeRegistros(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar());
					prestamo.setCat(String.valueOf(cat));			
					avalDTO aval = new avalDTO();
					prestamo.setAval(aval);
					codeudorDTO codeudor = new codeudorDTO();
					prestamo.setCodeudor(codeudor);
					//Buscamos el producto para tarjeta de debito
					Auxiliar aux_tdd = auxiliaresService.AuxiliarByOgsIdproducto(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio(),110);					
					if(aux_tdd.getEstatus() == 2) {
						prestamo.setTarjetaDebito(String.format("%06d",aux_tdd.getIdorigenp().intValue())+String.format("%05d",aux_tdd.getIdproducto().intValue())+String.format("%08d",aux_tdd.getIdauxiliar().intValue()));
					}
					Amortizacion amortizacion_final = amortizacionesService.findUltimaAmortizacion(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar());
					prestamo.setFecha_vencimiento_pagare(String.valueOf(amortizacion_final.getVence()));
					Origenes origen = origenesService.findMatrizOrigen();
					DetallesSiscore detallesSiscore = null;
					if(origen.getMatriz() == 30200) {
						detallesSiscore = ResumenSiscoreCSN(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar()); 
						prestamo.setId_solicitud_siscore(String.valueOf(detallesSiscore.getIdsolicitud()));
						prestamo.setResumen_calificacion_siscore(detallesSiscore);
					}					
					
					List<Amortizacion>cuotas = amortizacionesService.findAll(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar());
					prestamo.setCuotas(cuotas);	
					tmpService.eliminar(tmp_validacion);
				   }else {
					   prestamo.setNota("Monto solicitado excede el permitido en el core");
				   }
		     	}
			  }else {
				  if(tmp_validacion == null) {
					  prestamo.setNota("No se ha realizado una validacion para el socio:"+num_socio);  
				  }else {
					  int monto_renovar = tmp_validacion.getAtrasado().intValue();
					  if(monto_renovar > 0) {
						  prestamo.setNota("Asegurese de haber cubierto:"+montoCubrir+", al folio:"+opaAnterior);  
					  }else if(tmp_validacion.getMontorenovar() >= monto){
						  prestamo.setNota("No es posible renovar prestamo porque monto solicitado:"+monto+" es menor a monto a renovar:"+tmp_validacion.getMontorenovar());
					  }else {
						  prestamo.setNota(prestamo.getNota());
					  }
				  }				  
			  }
			} catch (Exception e) {
			System.out.println("Error al procesar el registro:"+e.getMessage());
		}		
		return prestamo;		
	}
	 
	
	public String calificacionPrezztaBackend(String opaReq,String confirmacion) {
		String mensaje = "";
		try {
			opaDTO opa = new HerramientasUtil().opa(opaReq);
			Auxiliar auxiliar = auxiliaresService.AuxiliarByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			CalificacionBackendPrezzta calificacion_buscar = calificacionBackendService.findCalificacionByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			if(calificacion_buscar != null) {
				 calificacionBackendService.updateCalificacion(auxiliar.getIdorigenp(),auxiliar.getIdproducto(),auxiliar.getIdauxiliar(),confirmacion);
			}else {
				calificacionBackendService.insertarRegistrosCalificacion(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar(),auxiliar.getMontoautorizado().doubleValue(),confirmacion);	
			}
			
			CalificacionBackendPrezzta calificacion = calificacionBackendService.findCalificacionByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			if(calificacion != null) {
				mensaje = "recibido";
			}
		} catch (Exception e) {
		   System.out.println("Error al registrar calificacion para el folio:"+opaReq); 
		}
		return mensaje;
	}
	
	
	public PrestamoEntregado entregarPrestamo(String opaReq,String confirmar) {
		PrestamoEntregado entregado = new PrestamoEntregado();
		
		try {
			Origenes matriz=origenesService.findMatrizOrigen();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			opaDTO opa = new HerramientasUtil().opa(opaReq);
			Auxiliar auxiliar = auxiliaresService.AuxiliarByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			LocalDateTime localDate = LocalDateTime.parse(matriz.getFechatrabajo().toString().substring(0,19), dtf);
			Timestamp fecha_transferencia = Timestamp.valueOf(localDate);	
			String sesion = otrosService.sesion();
			int rn = (int) (Math.random() * 999999 + 1);
			String referencia = String.valueOf(rn) + "" + String.valueOf(opaReq);
			// if(auxiliar.getMontosolicitado().doubleValue() <=60000) {
			RegistraMovimiento registrar_movimiento = new RegistraMovimiento();	
			double total_depositar = 0.0;
			Tablas tb_url_sms = tablasService.findIdtablaAndIdelemento("bankingly_banca_movil", "liga_envio_mensajes");
	        Tablas tb_sms_activo = tablasService.findIdtablaAndIdelemento("bankingly_banca_movil", "smsactivo");
	        Tablas tb_minimo_sms = tablasService.findIdtablaAndIdelemento("bankingly_banca_movil", "monto_minimo_sms");
			Tablas tb_texto_sms_dispersion = tablasService.findIdtablaAndIdelemento("prezzta","texto_sms_dispersion");
			Tablas tb_texto_sms_declinacion = tablasService.findIdtablaAndIdelemento("prezzta", "texto_sms_declinacion");
			Persona persona = personaService.findByOgs(auxiliar.getIdorigen(),auxiliar.getIdgrupo(),auxiliar.getIdsocio());
			//Tabla para obtener el monto de comision
			Tablas tb_monto_comision = tablasService.findIdtablaAndIdelemento("prezzta","comision");	
			Tablas tb_usuario = tablasService.findIdtablaAndIdelemento("prezzta","usuario");
			
			//Si se confirma entregamos el prestamo
			if(confirmar.equalsIgnoreCase("si")) {	
				//Busco si hay registros para el socio que esta intentando solicitar credito en linea
				List<RegistraMovimiento> movimientos = procesaMovimientoService.buscar(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
	            if(movimientos.size() > 0) {
	                System.out.println("Eliminando todos los registros");
	            	procesaMovimientoService.eliminaMovimientoTodos(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
	            }
	          //Busco el producto para dispersion
			  Tablas tb_config_dispersion  = tablasService.findIdtablaAndIdelemento("prezzta","producto_para_dispersion");
			  Auxiliar auxiliar_tdd = auxiliaresService.AuxiliarByOgsIdproducto(auxiliar.getIdorigen(),auxiliar.getIdgrupo(),auxiliar.getIdsocio(),Integer.parseInt(tb_config_dispersion.getDato1()));
    	 	 if(auxiliar_tdd != null) {
		    	  //Cargo al producto que va a ser la renovacion(Nuevo)
		    	  registrar_movimiento.setIdorigenp(auxiliar.getIdorigenp());
				  registrar_movimiento.setIdproducto(auxiliar.getIdproducto());
				  registrar_movimiento.setIdauxiliar(auxiliar.getIdauxiliar());
				  registrar_movimiento.setFecha(fecha_transferencia);
				  registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
				  registrar_movimiento.setSesion(sesion);
				  registrar_movimiento.setReferencia(referencia);
				  registrar_movimiento.setIdorigen(auxiliar.getIdorigen());
				  registrar_movimiento.setIdgrupo(auxiliar.getIdgrupo());
				  registrar_movimiento.setIdsocio(auxiliar.getIdsocio());
				  registrar_movimiento.setCargoabono(0);
				  registrar_movimiento.setMonto(auxiliar.getMontoautorizado().doubleValue());
				  registrar_movimiento.setIva(Double.parseDouble(auxiliar.getIva().toString()));
				  registrar_movimiento.setTipo_amort(Integer.parseInt(String.valueOf(auxiliar.getTipoamortizacion())));
				  registrar_movimiento.setSai_aux("");
				  
				  boolean bandera_renovacion = false;
				  boolean procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
				  		  
				  //Buscamos si lo que se va a aplicar es renovacion
				  Referenciasp referenciasp =  referenciaspService.referenciaspByOPA(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
				  if(referenciasp != null) {
					  //Aplicamos movimiento para realizar pago del prestamo a renovar(Viejito)
					  registrar_movimiento.setIdorigenp(referenciasp.getIdorigenpr());
					  registrar_movimiento.setIdproducto(referenciasp.getIdproductor());
					  registrar_movimiento.setIdauxiliar(referenciasp.getIdauxiliarr());
					  registrar_movimiento.setFecha(fecha_transferencia);
					  registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
					  registrar_movimiento.setSesion(sesion);
					  registrar_movimiento.setReferencia(referencia);
					  registrar_movimiento.setIdorigen(auxiliar.getIdorigen());
					  registrar_movimiento.setIdgrupo(auxiliar.getIdgrupo());
					  registrar_movimiento.setIdsocio(auxiliar.getIdsocio());
					  registrar_movimiento.setCargoabono(1);
					  //Si se debia capital y ya se hizo el pago corremos nuevamente la funcion para ver con cuanto se liquida el prestamo
					  
					  String monto_maximo_prestar = funcionesService.validacion_monto_prestar(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
					  String[] montos_array = monto_maximo_prestar.split("\\|");                    
					  List rangos = new ArrayList<>();
					  rangos = Arrays.asList(montos_array);	 			   
			 			     
			 		  String totalRenovar = rangos.get(5).toString();
			 		  registrar_movimiento.setMonto(Double.parseDouble(totalRenovar));
					  registrar_movimiento.setIva(0.0);
					  //Busco el auxiliar Solo para obtener el tipo de amorizacion
					  Auxiliar auxiliar_Activo_original = auxiliaresService.AuxiliarByOpa(referenciasp.getIdorigenpr(),referenciasp.getIdproductor(),referenciasp.getIdauxiliarr());
					  Auxiliar nuevo = auxiliaresService.AuxiliarByOpa(referenciasp.getIdorigenp(),referenciasp.getIdproducto(),referenciasp.getIdauxiliar());
					  registrar_movimiento.setTipo_amort(auxiliar_Activo_original.getTipoamortizacion().intValue());
					  registrar_movimiento.setSai_aux("");					 
					  total_depositar = (auxiliar.getMontoautorizado().doubleValue()-Double.parseDouble(tb_monto_comision.getDato1()) - (Double.parseDouble(tb_monto_comision.getDato1())* 0.16)) - Double.parseDouble(totalRenovar);
					  System.out.println("Total a depositar en tdd es:"+total_depositar);
					  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
					  bandera_renovacion = true;
					  					  
				  }
				  	  //Registrando movimiento abono a dispersion
					  registrar_movimiento.setIdorigenp(auxiliar_tdd.getIdorigenp());
					  registrar_movimiento.setIdproducto(auxiliar_tdd.getIdproducto());
					  registrar_movimiento.setIdauxiliar(auxiliar_tdd.getIdauxiliar());
					  registrar_movimiento.setFecha(fecha_transferencia);
					  registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
					  registrar_movimiento.setSesion(sesion);
					  registrar_movimiento.setReferencia(referencia);
					  registrar_movimiento.setIdorigen(auxiliar_tdd.getIdorigen());
					  registrar_movimiento.setIdgrupo(auxiliar_tdd.getIdgrupo());
					  registrar_movimiento.setIdsocio(auxiliar_tdd.getIdsocio());
					  registrar_movimiento.setCargoabono(1);
					  if(total_depositar>0 && bandera_renovacion) {
						  registrar_movimiento.setMonto(total_depositar);
					  }else {
						  registrar_movimiento.setMonto(auxiliar.getMontoautorizado().doubleValue()-Double.parseDouble(tb_monto_comision.getDato1()) - (Double.parseDouble(tb_monto_comision.getDato1())* 0.16));
						  total_depositar = registrar_movimiento.getMonto();
					  }
					  registrar_movimiento.setIva(Double.parseDouble(auxiliar_tdd.getIva().toString()));
					  registrar_movimiento.setTipo_amort(auxiliar_tdd.getTipoamortizacion().intValue());
					  registrar_movimiento.setSai_aux("");					  					 
					  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
				  
				  
				  
				  //Preparamos el movimiento a donde mandaremos la comision 			 
				  //Tabla para obtener el idproducto a donde se enviara la comision
				 
				  Tablas tb_producto_comision = tablasService.findIdtablaAndIdelemento("prezzta","producto_comision");
				  registrar_movimiento.setIdorigenp(0);
				  registrar_movimiento.setIdproducto(Integer.parseInt(tb_producto_comision.getDato1()));
				  registrar_movimiento.setIdauxiliar(0);
				  registrar_movimiento.setFecha(fecha_transferencia);
				  registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
				  registrar_movimiento.setSesion(sesion);
				  registrar_movimiento.setReferencia(referencia);
				  registrar_movimiento.setIdorigen(auxiliar_tdd.getIdorigen());
				  registrar_movimiento.setIdgrupo(auxiliar_tdd.getIdgrupo());
				  registrar_movimiento.setIdsocio(auxiliar_tdd.getIdsocio());
				  registrar_movimiento.setCargoabono(1);
				  registrar_movimiento.setMonto(Double.parseDouble(tb_monto_comision.getDato1()));
				  registrar_movimiento.setIva(Double.parseDouble(tb_monto_comision.getDato1())*0.16);
				  registrar_movimiento.setTipo_amort(0);
				 
				  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
				  
				  
				  
				  //Guardamos el movimiento
				  System.out.println("Producto comision registrado con exito"); 
				  
				  String datos_procesar =funcionesService.sai_aplica_transaccion(matriz.getFechatrabajo(),
					         registrar_movimiento.getIdusuario(),
					         registrar_movimiento.getSesion(),
					         registrar_movimiento.getReferencia());
			      int total_procesados = Integer.parseInt(String.valueOf(datos_procesar));
			                 
			      //busco nuevamente el auxiliar
			      auxiliar = auxiliaresService.AuxiliarByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			      String estado_aux = "";
			      if(auxiliar.getEstatus() == 2) {
			    	   estado_aux = "Activo";
			      }else {
			    	   estado_aux = "Autorizado";
			      }
			      
				  entregado.setOpa(opaReq);
				  entregado.setEstatus(estado_aux);
				  entregado.setMonto_entregado(String.valueOf(total_depositar));
				  //Buscamos la poliza
				  String poliza = funcionesService.obtenerPoliza(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());  
				  entregado.setNumero_folio_poliza(poliza);
				  entregado.setNumero_pagare("OPA-"+opaReq);
				  entregado.setProteccion_ahorro_prestamo(String.valueOf(auxiliar.getGarantia()));
				  entregado.setGarantia_liquida(String.valueOf(auxiliar.getGarantia()));
				  entregado.setTipo_garantia("garantia_liquida");
				  entregado.setDeposito_garantia_letras(new ConversionMonedaLetras().Convertir(String.valueOf(auxiliar.getGarantia()), true));	
				  
				  //Busco la validacion si se puede usar tdd en el proyecto prezzta
				  Tablas tb_uso_tdd = tablasService.findIdtablaAndIdelemento("prezzta","activa_tdd");
				  if(auxiliar_tdd.getIdproducto() == 133 && auxiliar.getEstatus()==2 && tb_uso_tdd.getDato1().equals("1")) {
					 //Buscamos la tarjeta de debito
					 FoliosTarjetas folioTdd = foliosTarjetasService.findByOpa(auxiliar_tdd.getIdorigenp(),auxiliar_tdd.getIdproducto(),auxiliar_tdd.getIdauxiliar());
					 Tablas tb_url_tdd = tablasService.findIdtablaAndIdelemento("prezzta", "url_tdd");
					 String respuestaDeposito = consumoTddService.depositarSaldo(tb_url_tdd.getDato2(),folioTdd.getIdtarjeta(), total_depositar);
					 System.out.println("Respuesta alestra:"+respuestaDeposito);
					 JSONObject respuestaAlestra = new JSONObject(respuestaDeposito);
				     if(!respuestaAlestra.getString("Deposito").toUpperCase().equals("FALLIDO")) {
					     entregado.setNota("La dispersion se realizo exitosamente,Total entregado:"+total_depositar);  
				        }else {
					     entregado.setNota("Falla al dispersar credito,contacte proveedor...");
				        }
				     //Verificamos si el envio de sms esta activo
				     	if(Integer.parseInt(tb_sms_activo.getDato1()) == 1) {
				     		//si el servicio de sms esta activo,validamos que el monto entregado sea mayor o igual para enviar sms
				     		if(Double.parseDouble(entregado.getMonto_entregado()) >= Double.parseDouble(tb_minimo_sms.getDato1())) {
				     			//llamamos el servicio para enviar sms
				     			String sms_enviar = smsCsn.enviarSMS(tb_url_sms.getDato2(),persona.getTelefono(),
				     					tb_texto_sms_dispersion.getDato2().replace("@monto@",entregado.getMonto_entregado()));
				     			
				     		}
				     	}
				   }			  
				   
				  if(total_procesados > 0) {
					String termina_transaccion = funcionesService.terminar_transaccion(matriz.getFechatrabajo(),
						         registrar_movimiento.getIdusuario(),
						         registrar_movimiento.getSesion(),
						         registrar_movimiento.getReferencia());
				    System.out.println("Mensaje termina transaccion:"+termina_transaccion);
				    entregado.setNota("La dispersion se realizo exitosamente,Total entregado:"+total_depositar);
				  } else {					
						entregado.setNota("No se completo la activacion del producto...");
						entregado.setNumero_pagare("");
						entregado.setProteccion_ahorro_prestamo("");
						entregado.setGarantia_liquida("");
						entregado.setTipo_garantia("");
						entregado.setDeposito_garantia_letras("");
				  }
				  
		      }else {		    	
		    	  entregado.setNota("Producto para dispersion no configurado");
		      }
			  
			  			   
			}//si no aplicamos descuento por uso de servicio
			else {
			      //Si se declina la operacion se hace un cargo al ahorro
				  Auxiliar ahorro = auxiliaresService.AuxiliarByOgsIdproducto(auxiliar.getIdorigen(),auxiliar.getIdgrupo(),auxiliar.getIdsocio(),110);

				  //Tabla para obtener el monto de comision
				  tb_monto_comision = tablasService.findIdtablaAndIdelemento("prezzta","comision");
				  registrar_movimiento.setIdorigenp(ahorro.getIdorigenp());
				  registrar_movimiento.setIdproducto(ahorro.getIdproducto());
				  registrar_movimiento.setIdauxiliar(ahorro.getIdauxiliar());
				  registrar_movimiento.setFecha(fecha_transferencia);
				  registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
				  registrar_movimiento.setSesion(sesion);
				  registrar_movimiento.setReferencia(referencia);
				  registrar_movimiento.setIdorigen(ahorro.getIdorigen());
				  registrar_movimiento.setIdgrupo(ahorro.getIdgrupo());
				  registrar_movimiento.setIdsocio(ahorro.getIdsocio());
				  registrar_movimiento.setCargoabono(0);
				  registrar_movimiento.setMonto(Double.parseDouble(tb_monto_comision.getDato1()) + Double.parseDouble(tb_monto_comision.getDato1())* 0.16);
				  registrar_movimiento.setIva(0.0);
				  registrar_movimiento.setTipo_amort(0);
				  registrar_movimiento.setSai_aux("");
				 
				  boolean procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
				  
				  //Preparamos el movimiento a donde mandaremos la comision 			 
				  //Tabla para obtener el idproducto a donde se enviara la comision
				  Tablas tb_producto_comision = tablasService.findIdtablaAndIdelemento("prezzta","producto_comision");
				  		  
				  registrar_movimiento.setIdorigenp(0);
				  registrar_movimiento.setIdproducto(Integer.parseInt(tb_producto_comision.getDato1()));
				  registrar_movimiento.setIdauxiliar(0);
				  registrar_movimiento.setFecha(fecha_transferencia);
				  registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
				  registrar_movimiento.setSesion(sesion);
				  registrar_movimiento.setReferencia(referencia);
				  registrar_movimiento.setIdorigen(ahorro.getIdorigen());
				  registrar_movimiento.setIdgrupo(ahorro.getIdgrupo());
				  registrar_movimiento.setIdsocio(ahorro.getIdsocio());
				  registrar_movimiento.setCargoabono(1);
				  registrar_movimiento.setMonto(Double.parseDouble(tb_monto_comision.getDato1()));
				  registrar_movimiento.setIva(Double.parseDouble(tb_monto_comision.getDato1())*0.16);
				  registrar_movimiento.setTipo_amort(0);
				 
				  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
				  //Guardamos el movimiento
				  
				  
				  String datos_procesar = funcionesService.sai_aplica_transaccion(matriz.getFechatrabajo(),
					         registrar_movimiento.getIdusuario(),
					         registrar_movimiento.getSesion(),
					         registrar_movimiento.getReferencia());
			      int total_procesados = Integer.parseInt(String.valueOf(datos_procesar));
			       
				  entregado.setOpa(opaReq);
				  entregado.setEstatus("Declinado");
				  entregado.setMonto_entregado("0.0");
				  entregado.setNota("Se aplicaron cargos por servicio, total:"+(Double.parseDouble(tb_monto_comision.getDato1())+(Double.parseDouble(tb_monto_comision.getDato1())*0.16))+" al producto Ahorro");
				
				if(total_procesados > 0) {
				String termina_transaccion = funcionesService.terminar_transaccion(matriz.getFechatrabajo(),
				         registrar_movimiento.getIdusuario(),
				         registrar_movimiento.getSesion(),
				         registrar_movimiento.getReferencia());
				
				//Verificamos si el envio de sms esta activo
		     	if(Integer.parseInt(tb_sms_activo.getDato1()) == 1) {
		     		//si el servicio de sms esta activo,validamos que el monto entregado sea mayor o igual para enviar sms
		     		if(Double.parseDouble(entregado.getMonto_entregado()) >= Double.parseDouble(tb_minimo_sms.getDato1())) {
		     			//llamamos el servicio para enviar sms
		     			String sms_enviar = smsCsn.enviarSMS(tb_url_sms.getDato2(),persona.getTelefono(),
		     					tb_texto_sms_declinacion.getDato2().replace("@monto@", String.valueOf(registrar_movimiento.getMonto()+registrar_movimiento.getIva())));		     			
		     		}
		     	}
		        System.out.println("Mensaje termina transaccion:"+termina_transaccion);
		        
				}else {					
					entregado.setNota("No se completo la activacion del producto...");
				}
			}
		} catch (Exception e) {
			System.out.println("Error al realizar la dispersion del prestamo:"+e.getMessage());
			entregado.setNota(e.getMessage());
		}
		return entregado;
	}
	
	public DetallesSiscore ResumenSiscoreCSN(Integer idorigenp,Integer idproducto,Integer idauxiliar) {
		DetallesSiscore consumoWsSiscore = new DetallesSiscore();
    	try {		
			
		List<PuntosScore> Listapuntos = new ArrayList<PuntosScore>();
		PuntosScore puntos = new PuntosScore();		
		//String siscore = siscoreCsn.requisitionImport(idorigenp+"-"+idproducto+"-"+idauxiliar);
		String siscore = siscoreCsn.requisitionImport(idorigenp+"-"+idproducto+"-"+idauxiliar);
			
		JSONObject responseSiscore = new JSONObject(siscore);	
		if(responseSiscore.getInt("responseCode") == 0 || responseSiscore.getString("responseMessage").toUpperCase().contains("SUCCES")){
			JSONObject obj = responseSiscore.getJSONObject("obj");
			JSONArray resumen = obj.getJSONArray("Resumen");
			JSONArray banderas = obj.getJSONArray("Banderas");
			JSONArray detalles = obj.getJSONArray("Detalle");
			
			for(int i = 0; i<resumen.length();i++) {
			   	PuntosScore puntosScore = new PuntosScore();		  
				JSONObject puntosScoreJson = resumen.getJSONObject(i);
					puntosScore.setId(puntosScoreJson.getString("$id"));
					puntosScore.setCampo(puntosScoreJson.getString("Campo"));
					puntosScore.setValor(puntosScoreJson.getString("Valor"));
					Listapuntos.add(puntosScore);		   	
				}				
			
			List<BanderasSiscore>listaDeBanderas = new ArrayList<>();
			for(int x=0;x<banderas.length();x++) {
				BanderasSiscore banderasGeneral = new BanderasSiscore();
				JSONObject banderaObjetoJson = banderas.getJSONObject(x);
				banderasGeneral.setId(banderaObjetoJson.getString("$id"));
				banderasGeneral.setTipoBandera(banderaObjetoJson.getString("TipoBandera"));	
				List<BanderasObjeto>banderasListaObjetos = new ArrayList<>();
				JSONArray banderaListaJson  = banderaObjetoJson.getJSONArray("Banderas");
				for(int y = 0; y < banderaListaJson.length();y++) {
					JSONObject objetoBanderaJson  = banderaListaJson.getJSONObject(y);	
					BanderasObjeto banderaSiscore = new BanderasObjeto();
					banderaSiscore.setId(objetoBanderaJson.getString("$id"));
					banderaSiscore.setNombre(objetoBanderaJson.getString("Nombre"));
					banderaSiscore.setSeCumple(objetoBanderaJson.getBoolean("SeCumple"));	
					banderasListaObjetos.add(banderaSiscore);
				}
				
				banderasGeneral.setBandera(banderasListaObjetos);			
				listaDeBanderas.add(banderasGeneral);
			}
			
			List<DetallesScore>detallesScore = new ArrayList<>();
			for(int i=0;i<detalles.length();i++){
				
			}
			
			consumoWsSiscore.setResumen(Listapuntos);
			consumoWsSiscore.setBanderas(listaDeBanderas);	
			consumoWsSiscore.setIdsolicitud(obj.getInt("IdSolicitud"));			
		   }		
		} catch (JSONException e) {
			System.out.println("El error es:"+e.getMessage());
		}
		
		return consumoWsSiscore;
	}
	
	
	
	public String pruebaPagoIntereses(String opaReq,Double monto) {
        String mensaje="";
		try {
			Origenes matriz=origenesService.findMatrizOrigen();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			opaDTO opa = new HerramientasUtil().opa(opaReq);
			Auxiliar auxiliar = auxiliaresService.AuxiliarByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			LocalDateTime localDate = LocalDateTime.parse(matriz.getFechatrabajo().toString().substring(0,19), dtf);
			Timestamp fecha_transferencia = Timestamp.valueOf(localDate);	
			String sesion = otrosService.sesion();
			int rn = (int) (Math.random() * 999999 + 1);
			String referencia = String.valueOf(rn) + "" + String.valueOf(opaReq);
			 // if(auxiliar.getMontosolicitado().doubleValue() <=60000) {
			 RegistraMovimiento registrar_movimiento = new RegistraMovimiento();	
			 
			  // se cubre el adeudo del prestamo						  
			  registrar_movimiento.setIdorigenp(auxiliar.getIdorigenp());
			  registrar_movimiento.setIdproducto(auxiliar.getIdproducto());
			  registrar_movimiento.setIdauxiliar(auxiliar.getIdauxiliar());
			  registrar_movimiento.setFecha(fecha_transferencia);
			  registrar_movimiento.setIdusuario(999);
			  registrar_movimiento.setSesion(sesion);
			  registrar_movimiento.setReferencia(referencia);
			  registrar_movimiento.setIdorigen(auxiliar.getIdorigen());
			  registrar_movimiento.setIdgrupo(auxiliar.getIdgrupo());
			  registrar_movimiento.setIdsocio(auxiliar.getIdsocio());
			  registrar_movimiento.setCargoabono(1);
			  registrar_movimiento.setMonto(monto);
			  registrar_movimiento.setIva(Double.parseDouble(auxiliar.getIva().toString()));
			  registrar_movimiento.setTipo_amort(Integer.parseInt(String.valueOf(auxiliar.getTipoamortizacion())));
			  registrar_movimiento.setSai_aux("");
			  
			  boolean procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
			  
			  //Busco la tdd
			  
			  Auxiliar auxiliar_tdd = auxiliaresService.AuxiliarByOgsIdproducto(auxiliar.getIdorigen(),auxiliar.getIdgrupo(),auxiliar.getIdsocio(),110);
			  registrar_movimiento.setIdorigenp(auxiliar_tdd.getIdorigenp());
			  registrar_movimiento.setIdproducto(auxiliar_tdd.getIdproducto());
			  registrar_movimiento.setIdauxiliar(auxiliar_tdd.getIdauxiliar());
			  registrar_movimiento.setFecha(fecha_transferencia);
			  registrar_movimiento.setIdusuario(999);
			  registrar_movimiento.setSesion(sesion);
			  registrar_movimiento.setReferencia(referencia);
			  registrar_movimiento.setIdorigen(auxiliar_tdd.getIdorigen());
			  registrar_movimiento.setIdgrupo(auxiliar_tdd.getIdgrupo());
			  registrar_movimiento.setIdsocio(auxiliar_tdd.getIdsocio());
			  registrar_movimiento.setCargoabono(0);
			  registrar_movimiento.setMonto(monto);
			  registrar_movimiento.setIva(Double.parseDouble(auxiliar_tdd.getIva().toString()));
			  registrar_movimiento.setTipo_amort(0);
			  registrar_movimiento.setSai_aux("");
			 
			  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);			 
			  
			  String datos_procesar = funcionesService.sai_aplica_transaccion(matriz.getFechatrabajo(),
				         registrar_movimiento.getIdusuario(),
				         registrar_movimiento.getSesion(),
				         registrar_movimiento.getReferencia());
		      int total_procesados = Integer.parseInt(String.valueOf(datos_procesar));
		      
		      
		      /*procesaMovimientoService.eliminaMovimiento(matriz.getFechatrabajo(),
				         registrar_movimiento.getIdusuario(),
				         registrar_movimiento.getSesion(),
				         registrar_movimiento.getReferencia());*/
		     
		      if(total_procesados > 0) {
		    	  mensaje = "Se realizo el pago correctamente";
		      }
		      
		}catch(Exception e) {
			 System.out.println("Error al realizar el pago:"+e.getMessage());
		}
		
		return mensaje;
			
	}
	
	
	 //Metodo para calcular cuantos meses hay entre 2 fechas
    public int restaFechas(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date hoy = new Date();
        String fecha_hoy = sdf.format(hoy);
        String fechaBase = sdf.format(fecha);

        String añoH = fecha_hoy.substring(0,4);
        String añoB = fechaBase.substring(0,4);
        String mesH = fecha_hoy.substring(5,7);
        String mesB = fechaBase.substring(5,7);

        int Años = Integer.parseInt(añoH) - Integer.parseInt(añoB);
        int Meses = Años * 12 + Integer.parseInt(mesH) - Integer.parseInt(mesB);
        return Meses;
    }
	 public String dateToString(Date fechac){
	        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
	        String fechaDate = null;
	        try {
	            fechaDate = formato.format(fechac);
	        } catch (Exception ex) {
	            System.out.println("Error fecha:" + ex.getMessage());
	        }
	        System.out.println("fechaDate:" + fechaDate);
	        return fechaDate.replace("/","-");
	    }
	
	public int cal_edad(Date fecha_nac){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaNac = sdf.format(fecha_nac);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNac, formatter);
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        System.out.println("AÑOS CUMPLIDOS: " + edad.getYears() + " Años " + edad.getMonths() + " Meses " + edad.getDays() + " Dias");

        return edad.getYears();
    }


}
