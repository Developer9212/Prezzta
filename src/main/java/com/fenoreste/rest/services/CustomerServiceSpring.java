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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.Util.ConversionMonedaLetras;
import com.fenoreste.rest.Util.HerramientasUtil;
import com.fenoreste.rest.consumoExterno.Alestra_Tarjetas_Debito;
import com.fenoreste.rest.consumoExterno.SMS_Csn;
import com.fenoreste.rest.consumoExterno.Siscore_Calificacion_Score;
import com.fenoreste.rest.entity.Amortizacion;
import com.fenoreste.rest.entity.Auxiliar;
import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.entity.CatalogoMenus;
import com.fenoreste.rest.entity.Colonias;
import com.fenoreste.rest.entity.CsnVacIngreso;
import com.fenoreste.rest.entity.Estados;
import com.fenoreste.rest.entity.FolioTarjeta;
import com.fenoreste.rest.entity.MovimientosPK;
import com.fenoreste.rest.entity.Municipios;
import com.fenoreste.rest.entity.Negociopropio;
import com.fenoreste.rest.entity.Origenes;
import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.PersonaPK;
import com.fenoreste.rest.entity.Producto;
import com.fenoreste.rest.entity.Referencias;
import com.fenoreste.rest.entity.Referenciasp;
import com.fenoreste.rest.entity.RegistraMovimiento;
import com.fenoreste.rest.entity.Socioeconomicos;
import com.fenoreste.rest.entity.Sopar;
import com.fenoreste.rest.entity.Tabla;
import com.fenoreste.rest.entity.TablaPK;
import com.fenoreste.rest.entity.Tarjeta;
import com.fenoreste.rest.entity.Trabajo;
import com.fenoreste.rest.entity.tmp_aperturas;
import com.fenoreste.rest.modelos.BanderasObjeto;
import com.fenoreste.rest.modelos.BanderasSiscore;
import com.fenoreste.rest.modelos.DetallesScore;
import com.fenoreste.rest.modelos.DetallesSiscore;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceSpring {
	
	@Autowired
	private IPersonaService personaService;
	
	@Autowired
	private ITablaService tablasService;
	
	@Autowired
	private IAuxiliaresService auxiliaresService;
	
	@Autowired
	private ISocioeconomicosService socioeconomicosService;
	
	@Autowired
	private ITrabajoService trabajosService;
	
	@Autowired
	private ICatalogoMenusService catalogoMenuService;
	
	@Autowired
	private IColoniaService coloniaService;
	
	@Autowired
	private IMunicipioService municipioService;
	
	@Autowired 
	private IEstadoService estadoService;
	
	@Autowired
	private IReferenciaService referenciaService;
	
	@Autowired
	private INegocioPropioService negocioService;
	
	@Autowired
	private IFuncionesService funcionesService;
	
	@Autowired
	private IAmortizacionesService amortizacionesService;
	
	@Autowired
	private Siscore_Calificacion_Score siscoreCsn;
	
	@Autowired
	private IGatService gatService;
	
	@Autowired
	private IOrigenesService origenesService;
	
	@Autowired
    private IOtrosService otrosService;
	
	@Autowired
    private IProcesaMovimientoService procesaMovimientoService;	
	
	@Autowired
	private ITmpAperturasService tmpService;

	@Autowired
	private Alestra_Tarjetas_Debito consumoTddService; 
	
	@Autowired
    private IFolioTarjetaService foliosTarjetaService;	
	
	@Autowired
	private SMS_Csn smsCsn;	
	
	@Autowired
	private IReferenciaspService referenciaspService;
	
	@Autowired
	private ISoparService soparService;
	
	@Autowired
	private ITarjetaService tarjetaService;
	
	@Autowired
	private ICsnVacIngresoService csnVacIngresoService;
	
	
	String idtabla="prezzta";

	public dataDTO informacionPersona(String tipoDocumento,String numeroDocumento) {
		dataDTO response = new dataDTO();
		Origenes origen = origenesService.findMatrizOrigen();
		try {
		TablaPK conf_minimo_solicitud_pk = new TablaPK(idtabla,"minimo_solicitud");
		Tabla config_minimo_solicitud = tablasService.buscarPorId(conf_minimo_solicitud_pk);		
		boolean bandera_so = false;
		List<String>rangos = new ArrayList<>();
		//Buscamos a la persona con los datos que llegaron en el metodo
		Persona persona = personaService.findPersonaByDocumento(tipoDocumento, numeroDocumento.trim());		
		//Validaciones solo para mitras
		if(origen.getIdorigen().intValue() == 30300) {
		   //Buscamos producto para banca movil activo
		   Auxiliar mitras_movil = auxiliaresService.AuxiliarByOgsIdproducto(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio(), 206);
              if(mitras_movil != null) {
            	 //Buscamos producto para dispersion
            	 TablaPK tb_pk_cdispersion = new TablaPK(idtabla,"producto_para_dispersion");
				 Tabla tb_config_dispersion  = tablasService.buscarPorId(tb_pk_cdispersion);
            	 Auxiliar cuenta_corriente = auxiliaresService.AuxiliarByOgsIdproducto(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio(), new Integer(tb_config_dispersion.getDato1()));
            	 if(cuenta_corriente == null) {
            		 return null;
            	 }
              }else {
            	  return null;
              }
            }
		
		//Buscamos el producto ahorro para saber si tiene el minimo configurado
		Auxiliar ahorro_disponible = auxiliaresService.AuxiliarByOgsIdproducto(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio(), 110);
		conf_minimo_solicitud_pk = new TablaPK(idtabla,"minimo_solicitud");
		config_minimo_solicitud = tablasService.buscarPorId(conf_minimo_solicitud_pk);
		if(ahorro_disponible.getSaldo().doubleValue() >= Double.parseDouble(config_minimo_solicitud.getDato1())) {
			if(origen.getIdorigen() == 30200) {
				//Vamos a validar estatus de la tdd
				Auxiliar auxiliar_tdd = auxiliaresService.AuxiliarByOgsIdproducto(persona.getIdorigen(), persona.getIdgrupo(),persona.getIdsocio(),133);
				//Buscamos el folio de tarjeta
				AuxiliarPK pk_tdd = new AuxiliarPK(auxiliar_tdd.getIdorigenp(),auxiliar_tdd.getIdproducto(),auxiliar_tdd.getIdauxiliar());
				FolioTarjeta folio_tdd = foliosTarjetaService.buscarPorId(pk_tdd);
				//Ahora buscamos registro para la tarjeta
				Tarjeta tarjeta = tarjetaService.buscarPorId(folio_tdd.getIdtarjeta());
				if(tarjeta.getFecha_vencimiento().after(origen.getFechatrabajo())) {
					TablaPK tb_pk_sopar = new TablaPK(idtabla,"prezzta_empleados");
					Tabla tb_sopar = tablasService.buscarPorId(tb_pk_sopar);
					PersonaPK pk_persona = new PersonaPK(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
					System.out.println(pk_persona.getIdorigen()+","+pk_persona.getIdgrupo()+","+pk_persona.getIdsocio()+","+tb_sopar.getDato2());
					Sopar sopar = soparService.buscarPorIdTipo(pk_persona, tb_sopar.getDato2());
					if(sopar != null ) {
						//Vamos a buscar si es persona relacionada
						Sopar sopar_persona_relacionada = soparService.buscarPorIdTipo(pk_persona, "personas_relacionadas");
						Sopar sopar_persona_relacionada2 = soparService.buscarPorIdTipo(pk_persona, "personas_relacionadas2");
						//Buscamos realacionada en ingresos
						CsnVacIngreso ingreso =  csnVacIngresoService.buscarPorIdActivo(pk_persona);						
						if(sopar_persona_relacionada !=null || sopar_persona_relacionada2 != null || ingreso != null) {
							response.setLista_Control(1);
						}else {
							response.setLista_Control(0);
						}
						bandera_so = true;
					}else {
						log.info("Esta es una prueba controlada");
						response.setNota("Esta es una prueba controlada...");
					}
				}else {
					log.info("Tarjeta esta vencida");
					response.setNota("Tarjeta esta vencida");
				}
			}else {
				bandera_so = true;
			}
		}else {
			log.info("Socio no alcanza el minimo para procesar su solicitud....");
			response.setNota("Socio no alcanza el minimo para procesar solicitud....");
		}
		
		
		if(persona != null && bandera_so == true) {
			//Para saber si es socio
			TablaPK tb_pk_parte_social = new TablaPK(idtabla,"parte_social");
			Tabla tb_producto_parte_social = tablasService.buscarPorId(tb_pk_parte_social);
			
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
                    }

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
				    String monto_maximo_prestar = funcionesService.validacion_monto_prestar(persona.getIdorigen(),persona.getIdgrupo(),persona.getIdsocio());
					String[] montos_array = monto_maximo_prestar.split("\\|");                    
		 		    rangos = Arrays.asList(montos_array);
		 			String plazoMin = "0",plazoMax="0",montoMin="0",montoMax="0";
		 			if(!monto_maximo_prestar.toUpperCase().contains("ERROR")) {
   		 			   //Buscamos las tablas de mensajes 
		 			   TablaPK tb_pk_tmp = new TablaPK();
		 			   tb_pk_tmp.setIdtabla(idtabla);
		 			   tb_pk_tmp.setIdelemento("comision");
		 			   Tabla tb_comision_servicio = tablasService.buscarPorId(tb_pk_tmp);
		 			   tb_pk_tmp.setIdelemento("nota_comision");
		 			   Tabla tb_nota_comision  = tablasService.buscarPorId(tb_pk_tmp);
		 			   tb_pk_tmp.setIdelemento("nota_dispersion");
		 			   Tabla tb_nota_dispersion = tablasService.buscarPorId(tb_pk_tmp);
		 			   tb_pk_tmp.setIdelemento("nota_renovacion");
		 			   Tabla tb_nota_renovacion = tablasService.buscarPorId(tb_pk_tmp);
		 			   tb_pk_tmp.setIdelemento("nota_montos_atrasado");
		 			   Tabla tb_nota_monto_atraso = tablasService.buscarPorId(tb_pk_tmp);
		 			   	 			   	 			 
		 			  plazoMin = config_minimo_solicitud.getDato2();
		 			  plazoMax = rangos.get(1).toString();
		 			  montoMin = config_minimo_solicitud.getDato1();
		 			  montoMax = rangos.get(3).toString();
		 			  
		 			  TablaPK tb_pk = new TablaPK(idtabla,"monto_maximo_solicitud");
		 			  Tabla tb_monto_maximo = tablasService.buscarPorId(tb_pk);
                      if(Double.parseDouble(montoMax)> Double.parseDouble(tb_monto_maximo.getDato1())) {
                    	  montoMax = tb_monto_maximo.getDato1();
                      }
		 			  
		 			  String tipoApertura = rangos.get(4).toString();
		 			  String totalRenovar = rangos.get(5).toString();
		 			  String totalAtraso = rangos.get(7).toString();
		 			  String idorigenp = rangos.get(10).toString();
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
			        	  /*validacion.setNota(tb_nota_comision.getDato2().replace("@comision@",String.valueOf(Double.parseDouble(tb_comision_servicio.getDato1()) + Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16))+","+
			        	                     tb_nota_renovacion.getDato2().replace("@renovacion@",totalRenovar)+","+mmensajeAtraseo+ 		
			        			             tb_nota_dispersion.getDato2().replace("@dispersion@", "montoSolicitado - "+totalRenovar+" - "+String.valueOf(Double.parseDouble(tb_comision_servicio.getDato1()) + Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16))+imprt);*/
			        	 			        	  
			        	  validacion.setNota(tb_nota_comision.getDato2().replace("@comision@",String.valueOf(0))+","+
	        	                     tb_nota_renovacion.getDato2().replace("@renovacion@",totalRenovar)+","+mmensajeAtraseo+ 		
	        			             tb_nota_dispersion.getDato2().replace("@dispersion@", "montoSolicitado - "+totalRenovar) + imprt);
	        			  
	        			
	        			  tmp_saver.setTipoapertura("Renovacion");
	        			  tmp_saver.setMontorenovar(Double.parseDouble(rangos.get(5).toString()));
	        			  tmp_saver.setAtrasado(Double.parseDouble(rangos.get(7).toString()));	
	        			  tmp_saver.setIdproducto(Integer.parseInt(rangos.get(8).toString()));
	        			  tmp_saver.setOpaactivo(rangos.get(9).toString());
	        			  //double totallibre = tmp_saver.getMontorenovar()+Double.parseDouble(tb_comision_servicio.getDato1())+(Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16);
	        			  Double totallibre = tmp_saver.getMontorenovar();
	        			  tmp_saver.setGastos_pagar(totallibre);
			          }else {
			        	  tmp_saver.setTipoapertura("Activacion");
			        	  tmp_saver.setIdproducto(Integer.parseInt(rangos.get(8).toString()));
			        	  tmp_saver.setOpaactivo("0-0-0");		
			        	  validacion.setTipo_apertura("Activacion");
			        	  /*validacion.setNota(tb_nota_comision.getDato2().replace("@comision@", tb_comision_servicio.getDato1())+","+
	        	                            tb_nota_dispersion.getDato2().replace("@dispersion@", "montoSolicitado - "+tb_comision_servicio.getDato1()));*/
			        	  validacion.setNota(tb_nota_comision.getDato2().replace("@comision@", "0") + "," +
  	                            tb_nota_dispersion.getDato2().replace("@dispersion@", "montoSolicitado"));
			        	  validacion.setMonto_renovar(0.0);
			        	  //double totallibre = Double.parseDouble(tb_comision_servicio.getDato1())+(Double.parseDouble(tb_comision_servicio.getDato1()) * 0.16);
			        	  double totallibre = 0.0;
	        			  tmp_saver.setGastos_pagar(totallibre);			        	  
			          }		
			          tmp_saver = tmpService.guardar(tmp_saver);
			          
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
                    TablaPK tb_pk_gatosa = new TablaPK(idtabla,"gastos_alimentacion");
                    Tabla tb_gastos_alimentacion = tablasService.buscarPorId(tb_pk_gatosa);
                    
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
                    TablaPK tb_pk_gastos_servicio = new TablaPK(idtabla,"gastos_servicio");
                    Tabla tb_gastos_servicio = tablasService.buscarPorId(tb_pk_gastos_servicio);
                    
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
                    TablaPK tb_pk_gatos = new TablaPK(idtabla,"gastos");
                    Tabla tb_gastos = tablasService.buscarPorId(tb_pk_gatos);
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
                    if(response.getNum_socio_conyugue() != "") {
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
                   
                   relacionesSociosDTO relaciones_socios = new relacionesSociosDTO();
                   List<relacionesSociosDTO> listaRS = new ArrayList<>();
                   listaRS.add(relaciones_socios);                        
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
			    }else {
			    	response.setNota("Parte social incompleta...");
			    }
		     }			
		  }
	   }catch(Exception e) {
			System.out.println("Error al llenar data result:"+e.getMessage());
		}
		
		return response;
	}
	
	
	public PrestamoCreadoDTO aperturaFolio(String num_socio,Double monto,int plazos) {
		boolean bandera = false;
		PrestamoCreadoDTO prestamo = null;		
		String montoCubrir = "",opaAnterior ="";
		log.info("Accediendo al ws 2....");
		try {			 		    
			prestamo = new PrestamoCreadoDTO();
			ogsDTO ogs = new HerramientasUtil().ogs(num_socio);
			//Obtenemos informacion que ya se valido
			tmp_aperturas tmp_validacion = tmpService.buscar(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio());
			log.info("Vamos a buscar validaciones de ws 1...");
		    if(tmp_validacion != null) {
		    if(tmp_validacion.getTipoapertura().toUpperCase().contains("R")) {
		   		//Validacion que no halla monto atrasado				 
				String monto_maximo_prestar = funcionesService.validacion_monto_prestar(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio());					
				String[] montos_array = monto_maximo_prestar.split("\\|");                    
	 		    List<String>rangos = Arrays.asList(montos_array);	
	 		    montoCubrir = rangos.get(7).toString();
 			    String[]cadena = rangos.get(9).toString().split("\\-");
		        opaAnterior = String.format("%06d",Integer.parseInt(cadena[0]))+String.format("%05d",Integer.parseInt(cadena[1]))+String.format("%08d",Integer.parseInt(cadena[2]));
		        if(tmp_validacion.getAtrasado().intValue() == 0) {
		        	if((monto > tmp_validacion.getGastos_pagar())) {
			    		bandera = true;	
					 }else{
						 log.info("No es posible renovar prestamo porque monto solicitado:"+monto+" es menor o igual a monto a renovar:"+tmp_validacion.getMontorenovar());
						 prestamo.setNota("No es posible renovar prestamo porque monto solicitado:"+monto+" es menor o igual a monto a renovar:"+tmp_validacion.getMontorenovar());
					 }
			      }else {
			    	  log.info("Asegurese de haber cubierto:"+montoCubrir+", al folio:"+opaAnterior);
			    	  prestamo.setNota("Asegurese de haber cubierto:"+montoCubrir+", al folio:"+opaAnterior);  
			      }
		      }else {
		        	bandera = true;
		      }
		    
		    if(bandera) {
					//Configuracion de apertura para obtener el origen
					if(monto <= tmp_validacion.getMontoalcanzado()) {
						String aperturar_opa = funcionesService.aperturar_opa(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio(), monto, plazos,tmp_validacion.getIdproducto(),tmp_validacion.getOpaactivo(),tmp_validacion.getIdorigenp());								
						Auxiliar creado_aux = auxiliaresService.AuxiliarByOpa(tmp_validacion.getIdorigenp(),tmp_validacion.getIdproducto(),Integer.parseInt(aperturar_opa.replace("|","").toString()));
						prestamo.setOpa(String.format("%06d",creado_aux.getIdorigenp())+String.format("%05d",creado_aux.getIdproducto())+String.format("%08d",creado_aux.getIdauxiliar()));  
					    prestamo.setIdorigenp(String.valueOf(creado_aux.getIdorigenp()));
					    prestamo.setNumero_producto(String.valueOf(creado_aux.getIdproducto()));
					    prestamo.setIdauxiliar(String.valueOf(creado_aux.getIdauxiliar()));
						prestamo.setClasificacion_cartera(creado_aux.getCartera());
						prestamo.setFolio_prestamo(String.format("%06d",creado_aux.getIdorigenp())+String.format("%05d",creado_aux.getIdproducto())+String.format("%08d",creado_aux.getIdauxiliar()));
						TablaPK tb_pk_producto = new TablaPK("numero_reca_por_producto",String.valueOf(creado_aux.getIdproducto()));
						Tabla tb_reca_por_producto = tablasService.buscarPorId(tb_pk_producto);
						prestamo.setReca_completo(tb_reca_por_producto.getDato1());
						String[]tb_reca_array_recortado = tb_reca_por_producto.getDato1().split("\\/");
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
						TablaPK tb_pk_cdispersion = new TablaPK(idtabla,"producto_para_dispersion");
						Tabla tb_config_dispersion  = tablasService.buscarPorId(tb_pk_cdispersion);
						Auxiliar aux_tdd = auxiliaresService.AuxiliarByOgsIdproducto(ogs.getIdorigen(),ogs.getIdgrupo(),ogs.getIdsocio(),new Integer(tb_config_dispersion.getDato1()));					
						if(aux_tdd.getEstatus() == 2) {
							prestamo.setTarjetaDebito(String.format("%06d",aux_tdd.getIdorigenp().intValue())+String.format("%05d",aux_tdd.getIdproducto().intValue())+String.format("%08d",aux_tdd.getIdauxiliar().intValue()));
						}
						Amortizacion amortizacion_final = amortizacionesService.findUltimaAmortizacion(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar());
						prestamo.setFecha_vencimiento_pagare(String.valueOf(amortizacion_final.getVence()));
						Origenes origen = origenesService.findMatrizOrigen();
						DetallesSiscore detallesSiscore = null;
						if(origen.getIdorigen() == 30200) {				
							detallesSiscore = ResumenSiscoreCSN(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar()); 
							prestamo.setId_solicitud_siscore(String.valueOf(detallesSiscore.getIdsolicitud()));
							prestamo.setResumen_calificacion_siscore(detallesSiscore);
						}					
						
						List<Amortizacion>cuotas = amortizacionesService.findAll(creado_aux.getIdorigenp(),creado_aux.getIdproducto(),creado_aux.getIdauxiliar());
						prestamo.setCuotas(cuotas);	
						tmpService.eliminar(tmp_validacion);
					   }else {
						   log.info("Monto solicitado excede el permitido en el core");
						   prestamo.setNota("Monto solicitado excede el permitido en el core");
					   }
			        }
			    }else {
			    	log.info("No se ha realizado una validacion para el socio:"+num_socio);
			    	 prestamo.setNota("No se ha realizado una validacion para el socio:"+num_socio); 
			    }
			 }catch (Exception e) {
		         	log.info("Error al procesar el registro:"+e.getMessage());
		     }		
		 log.info("Saliendo ws 2...");
		 log.info("El prestamo aperturado es:"+prestamo);
		return prestamo;		
	}
	
	
	
		
	
	public PrestamoEntregado entregarPrestamo(String opaReq,String confirmar) {
		PrestamoEntregado entregado = new PrestamoEntregado();
		log.info("Accediendo al ws 3 dispersion...");
		MovimientosPK mov_pk = null;
		try {
			Origenes matriz=origenesService.findMatrizOrigen();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			opaDTO opa = new HerramientasUtil().opa(opaReq);
			log.info("Pasando de leer origen matriz");
			Auxiliar auxiliar = auxiliaresService.AuxiliarByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			//Verifico que el opa no se halla activado antes para evitar activar 2 veces
			if(auxiliar.getEstatus() == 1) {
			log.info("El estatus es autorizado");
			LocalDateTime localDate = LocalDateTime.parse(matriz.getFechatrabajo().toString().substring(0,19), dtf);
			Timestamp fecha_transferencia = Timestamp.valueOf(localDate);	
			String sesion = otrosService.sesion();
			int rn = (int) (Math.random() * 999999 + 1);
			String referencia = String.valueOf(rn) + "" + String.valueOf(opaReq);
			RegistraMovimiento registrar_movimiento = new RegistraMovimiento();
			double total_depositar = 0.0;
			TablaPK tb_pk_all = new TablaPK("bankingly_banca_movil","liga_envio_mensajes");
			log.info("Vamos a buscar configuraciones para envio sms");
			Tabla tb_url_sms = tablasService.buscarPorId(tb_pk_all);
			log.info("Configuracion sms encontrada");
			tb_pk_all.setIdelemento("smsactivo");
			log.info("Buscando servicio sms activo");
	        Tabla tb_sms_activo = tablasService.buscarPorId(tb_pk_all);
	        tb_pk_all.setIdelemento("monto_minimo_sms");
	        log.info("Buscando servicio sms minimo");
	        Tabla tb_minimo_sms = tablasService.buscarPorId(tb_pk_all);
	        tb_pk_all.setIdtabla(idtabla);
	        tb_pk_all.setIdelemento("texto_sms_dispersion");
	        log.info("Buscando servicio dispersion");
			Tabla tb_texto_sms_dispersion = tablasService.buscarPorId(tb_pk_all);
			tb_pk_all.setIdelemento("txto_sms_declinacion");
			log.info("Buscando msj sms declinacion");
			Tabla tb_texto_sms_declinacion = tablasService.buscarPorId(tb_pk_all);
			Persona persona = personaService.findByOgs(auxiliar.getIdorigen(),auxiliar.getIdgrupo(),auxiliar.getIdsocio());
			//Tabla para obtener el monto de comision
			tb_pk_all.setIdelemento("comision");
			log.info("Buscando config. comision");
			Tabla tb_monto_comision = tablasService.buscarPorId(tb_pk_all);
			tb_pk_all.setIdelemento("usuario");
			log.info("Buscando usuario");
			Tabla tb_usuario = tablasService.buscarPorId(tb_pk_all);
			//Si se confirma entregamos el prestamo
			if(confirmar.equalsIgnoreCase("si")) {	
			  //Busco el producto para dispersion
	          tb_pk_all.setIdelemento("producto_para_dispersion");
	          log.info("Buscando servicio para dispersion");
	          Tabla tb_config_dispersion  = tablasService.buscarPorId(tb_pk_all);
	          log.info("Dispersion config a:"+tb_config_dispersion);
			  Auxiliar auxiliar_tdd = auxiliaresService.AuxiliarByOgsIdproducto(auxiliar.getIdorigen(),auxiliar.getIdgrupo(),auxiliar.getIdsocio(),Integer.parseInt(tb_config_dispersion.getDato1()));
			  if(auxiliar_tdd != null) {
				  log.info("Se encontraron registros para la tdd");
    	 		  procesaMovimientoService.eliminaMovimientoTodos(auxiliar.getIdorigen(), auxiliar.getIdgrupo(),auxiliar.getIdsocio());
		    	  mov_pk = new MovimientosPK(Integer.parseInt(tb_usuario.getDato1()), sesion, referencia,auxiliar.getIdorigenp(),auxiliar.getIdproducto(),auxiliar.getIdauxiliar());
    	 		  /*registrar_movimiento.setIdorigenp(auxiliar.getIdorigenp());
				  registrar_movimiento.setIdproducto(auxiliar.getIdproducto());
				  registrar_movimiento.setIdauxiliar(auxiliar.getIdauxiliar());*/
		    	  registrar_movimiento.setPk(mov_pk);
				  registrar_movimiento.setFecha(fecha_transferencia);
				  /*registrar_movimiento.setIdusuario();
				  registrar_movimiento.setSesion(sesion);
				  registrar_movimiento.setReferencia(referencia);*/
				  registrar_movimiento.setIdorigen(auxiliar.getIdorigen());
				  registrar_movimiento.setIdgrupo(auxiliar.getIdgrupo());
				  registrar_movimiento.setIdsocio(auxiliar.getIdsocio());
				  registrar_movimiento.setCargoabono(0);
				  registrar_movimiento.setMonto(auxiliar.getMontoautorizado().doubleValue());
				  registrar_movimiento.setIva(Double.parseDouble(auxiliar.getIva().toString()));
				  registrar_movimiento.setTipo_amort(Integer.parseInt(String.valueOf(auxiliar.getTipoamortizacion())));
				  registrar_movimiento.setSai_aux("");
				  
				  log.info("");
				  boolean bandera_renovacion = false;
				  boolean procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
				  		  
				  //Buscamos si lo que se va a aplicar es renovacion
				  AuxiliarPK pk_referenciap = new AuxiliarPK(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
				  Referenciasp referenciasp =  referenciaspService.buscarPorIdTipoReferencia(pk_referenciap);
				  if(referenciasp != null) {
					  //Aplicamos movimiento para realizar pago del prestamo a renovar(Viejito)
					  mov_pk = new MovimientosPK(Integer.parseInt(tb_usuario.getDato1()), sesion, referencia,referenciasp.getIdorigenpr(),referenciasp.getIdproductor(),referenciasp.getIdauxiliarr());
					  /*registrar_movimiento.setIdorigenp(referenciasp.getIdorigenpr());
					  registrar_movimiento.setIdproducto(referenciasp.getIdproductor());
					  registrar_movimiento.setIdauxiliar(referenciasp.getIdauxiliarr());*/
					  registrar_movimiento.setPk(mov_pk);
					  registrar_movimiento.setFecha(fecha_transferencia);
					  /*registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
					  registrar_movimiento.setSesion(sesion);
					  registrar_movimiento.setReferencia(referencia);*/
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
					  Auxiliar auxiliar_activo_original = auxiliaresService.AuxiliarByOpa(referenciasp.getIdorigenpr(),referenciasp.getIdproductor(),referenciasp.getIdauxiliarr());
					  Auxiliar nuevo = auxiliaresService.AuxiliarByOpa(referenciasp.getPk().getIdorigenp(),referenciasp.getPk().getIdproducto(),referenciasp.getPk().getIdauxiliar());
					  registrar_movimiento.setTipo_amort(auxiliar_activo_original.getTipoamortizacion().intValue());
					  registrar_movimiento.setSai_aux("");
					  //total_depositar = (auxiliar.getMontoautorizado().doubleValue()-Double.parseDouble(tb_monto_comision.getDato1()) - (Double.parseDouble(tb_monto_comision.getDato1())* 0.16)) - Double.parseDouble(totalRenovar);
					  total_depositar = (auxiliar.getMontoautorizado().doubleValue() - Double.parseDouble(totalRenovar));
					  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
					  bandera_renovacion = true;
					  
				  }
				  	  //Registrando movimiento abono a dispersion
				      mov_pk = new MovimientosPK(Integer.parseInt(tb_usuario.getDato1()), sesion, referencia,auxiliar_tdd.getIdorigenp(),auxiliar_tdd.getIdproducto(),auxiliar_tdd.getIdauxiliar()); 
				      /*registrar_movimiento.setIdorigenp(auxiliar_tdd.getIdorigenp());
					  registrar_movimiento.setIdproducto(auxiliar_tdd.getIdproducto());
					  registrar_movimiento.setIdauxiliar(auxiliar_tdd.getIdauxiliar());*/
					  registrar_movimiento.setPk(mov_pk);
				      registrar_movimiento.setFecha(fecha_transferencia);
					  /*registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
					  registrar_movimiento.setSesion(sesion);
					  registrar_movimiento.setReferencia(referencia);*/
					  registrar_movimiento.setIdorigen(auxiliar_tdd.getIdorigen());
					  registrar_movimiento.setIdgrupo(auxiliar_tdd.getIdgrupo());
					  registrar_movimiento.setIdsocio(auxiliar_tdd.getIdsocio());
					  registrar_movimiento.setCargoabono(1);
					  if(total_depositar>0 && bandera_renovacion) {
						  registrar_movimiento.setMonto(total_depositar);
					  }else {
						  //registrar_movimiento.setMonto(auxiliar.getMontoautorizado().doubleValue()-Double.parseDouble(tb_monto_comision.getDato1()) - (Double.parseDouble(tb_monto_comision.getDato1())* 0.16));
						  registrar_movimiento.setMonto(auxiliar.getMontoautorizado().doubleValue());
						  total_depositar = registrar_movimiento.getMonto();
					  }
					  registrar_movimiento.setIva(Double.parseDouble(auxiliar_tdd.getIva().toString()));
					  registrar_movimiento.setTipo_amort(auxiliar_tdd.getTipoamortizacion().intValue());
					  registrar_movimiento.setSai_aux("");					  					 
					  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);
				  
				  
				  //Preparamos el movimiento a donde mandaremos la comision 			 
				  //Tabla para obtener el idproducto a donde se enviara la comision				 
				  /*Tablas tb_producto_comision = tablasService.findIdtablaAndIdelemento("prezzta","producto_comision");
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
				  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);*/
				  log.info("Registros procesados con exito");
				  boolean deposito_csn = false;
				  int total_procesados = 0;
				  //Si el origen es CSN
				  if(matriz.getIdorigen() == 30200) {					  
					  //Busco la validacion si se puede usar tdd en el proyecto prezzta
					  TablaPK tb_pk_tdd = new TablaPK(idtabla,"activa_tdd");
					  Tabla tb_uso_tdd = tablasService.buscarPorId(tb_pk_tdd);
					  if(auxiliar_tdd.getIdproducto() == 133 && auxiliar_tdd.getEstatus()==2 && new Integer(tb_uso_tdd.getDato1())==1) {
						 //Buscamos la tarjeta de debito
						 AuxiliarPK pk_tdd = new AuxiliarPK(auxiliar_tdd.getIdorigenp(),auxiliar_tdd.getIdproducto(),auxiliar_tdd.getIdauxiliar());
						 FolioTarjeta folioTdd = foliosTarjetaService.buscarPorId(pk_tdd);
						 TablaPK tb_pk_url_tdd = new TablaPK(idtabla,"url_tdd");
						 log.info("Buscando registros para conexion alestra");
						 Tabla tb_url_tdd = tablasService.buscarPorId(tb_pk_url_tdd);
						 System.out.println("Url endpoint tdd:"+tb_url_tdd.getDato2());
						 String respuestaDeposito = consumoTddService.depositarSaldo(tb_url_tdd.getDato2(),folioTdd.getIdtarjeta(), total_depositar);
						 log.info("Respuesta alestra:"+respuestaDeposito);
						 JSONObject respuestaAlestra = new JSONObject(respuestaDeposito);
					        if(!respuestaAlestra.getString("deposito").toUpperCase().equals("FALLIDO")) {
					         log.info("La dispersion se realizo exitosamente,Total entregado:"+total_depositar);
						     entregado.setNota("La dispersion se realizo exitosamente,Total entregado:"+total_depositar);  
						     deposito_csn = true;
					        }else {
					        	log.info("Falla al dispersar credito,contacte proveedor...");
						     entregado.setNota("Falla al dispersar credito,contacte proveedor...");
					        }			     
					     
					     if(deposito_csn) {
					    	 log.info("Vamos a procesar registros csn");
					    	 String datos_procesar =funcionesService.sai_aplica_transaccion(matriz.getFechatrabajo(),
							         registrar_movimiento.getPk().getIdusuario(),
							         registrar_movimiento.getPk().getSesion(),
							         registrar_movimiento.getPk().getReferencia());
					       total_procesados = Integer.parseInt(String.valueOf(datos_procesar));
					       //Si la poliza no se procesa de manera correcta
					       if(total_procesados <= 0) {
					    	   log.info("No se proceso correctamente vamos a regresar el saldo");
					    	   String respuestaRetiro = consumoTddService.retirarSaldo(tb_url_tdd.getDato2(),folioTdd.getIdtarjeta(), total_depositar);
					    	   entregado.setNota("Error al dispersar credito");
					    	   log.info("Error al dispersar credito");
					    	   deposito_csn = false;
					       }
					     }
					     //Verificamos si el envio de sms esta activo
					     	if(Integer.parseInt(tb_sms_activo.getDato1()) == 1 && deposito_csn) {		
					     		//si el servicio de sms esta activo,validamos que el monto entregado sea mayor o igual para enviar sms
					     		if(Double.parseDouble(auxiliar.getMontoautorizado().toString()) >= Double.parseDouble(tb_minimo_sms.getDato1())) {
					     			//llamamos el servicio para enviar sms
					     			String sms_enviar = smsCsn.enviarSMS(tb_url_sms.getDato2(),persona.getTelefono(),
					     					tb_texto_sms_dispersion.getDato2().replace("@monto@",auxiliar.getMontoautorizado().toString()));
					     			log.info("Sms Enviado con Exito");
					     		}
					     	}
					   }else {	
						   String datos_procesar =funcionesService.sai_aplica_transaccion(matriz.getFechatrabajo(),
							         registrar_movimiento.getPk().getIdusuario(),
							         registrar_movimiento.getPk().getSesion(),
							         registrar_movimiento.getPk().getReferencia());
					       total_procesados = Integer.parseInt(String.valueOf(datos_procesar));
					   }//Termina CSn
				  }else {
					  String datos_procesar =funcionesService.sai_aplica_transaccion(matriz.getFechatrabajo(),
						         registrar_movimiento.getPk().getIdusuario(),
						         registrar_movimiento.getPk().getSesion(),
						         registrar_movimiento.getPk().getReferencia());
				       total_procesados = Integer.parseInt(String.valueOf(datos_procesar));
				  }
				  
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
				  
				 		  
				   
				  if(total_procesados > 0) {					
				    entregado.setNota("La dispersion se realizo exitosamente,Total entregado:"+total_depositar);
				  } else {					
						entregado.setNota("No se completo la activacion del producto...");
						entregado.setNumero_pagare("");
						entregado.setProteccion_ahorro_prestamo("");
						entregado.setGarantia_liquida("");
						entregado.setTipo_garantia("");
						entregado.setDeposito_garantia_letras("");
						
				  }
				  
				  String termina_transaccion = funcionesService.terminar_transaccion(matriz.getFechatrabajo(),
					         registrar_movimiento.getPk().getIdusuario(),
					         registrar_movimiento.getPk().getSesion(),
					         registrar_movimiento.getPk().getReferencia());
				  
		      }else {		    	
		    	  entregado.setNota("Producto para dispersion no configurado");
		      }
			  
			  			   
			}//si no aplicamos descuento por uso de servicio
			else {
			      //Si se declina la operacion se hace un cargo al ahorro
				  Auxiliar ahorro = auxiliaresService.AuxiliarByOgsIdproducto(auxiliar.getIdorigen(),auxiliar.getIdgrupo(),auxiliar.getIdsocio(),110);

				  //Tabla para obtener el monto de comision
				  TablaPK tb_pk_c = new TablaPK(idtabla,"comision");
				  tb_monto_comision = tablasService.buscarPorId(tb_pk_c);
				  mov_pk = new MovimientosPK(Integer.parseInt(tb_usuario.getDato1()), sesion, referencia,ahorro.getIdorigenp(),ahorro.getIdproducto(),ahorro.getIdauxiliar());
				  /*registrar_movimiento.setIdorigenp(ahorro.getIdorigenp());
				  registrar_movimiento.setIdproducto(ahorro.getIdproducto());
				  registrar_movimiento.setIdauxiliar(ahorro.getIdauxiliar());*/
				  registrar_movimiento.setPk(mov_pk);
				  registrar_movimiento.setFecha(fecha_transferencia);
				  /*registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
				  registrar_movimiento.setSesion(sesion);
				  registrar_movimiento.setReferencia(referencia);*/
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
				  TablaPK tb_pk_cp = new TablaPK(idtabla,"producto_comision");
				  Tabla tb_producto_comision = tablasService.buscarPorId(tb_pk_cp);
				  mov_pk = new MovimientosPK(Integer.parseInt(tb_usuario.getDato1()), sesion, referencia,0,Integer.parseInt(tb_producto_comision.getDato1()),0);
				  
				  /*registrar_movimiento.setIdorigenp(0);
				  registrar_movimiento.setIdproducto(Integer.parseInt(tb_producto_comision.getDato1()));
				  registrar_movimiento.setIdauxiliar(0);*/
				  registrar_movimiento.setPk(mov_pk);
				  registrar_movimiento.setFecha(fecha_transferencia);
				  /*registrar_movimiento.setIdusuario(Integer.parseInt(tb_usuario.getDato1()));
				  registrar_movimiento.setSesion(sesion);
				  registrar_movimiento.setReferencia(referencia);*/
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
					         registrar_movimiento.getPk().getIdusuario(),
					         registrar_movimiento.getPk().getSesion(),
					         registrar_movimiento.getPk().getReferencia());
			      int total_procesados = Integer.parseInt(String.valueOf(datos_procesar));
			       
				  entregado.setOpa(opaReq);
				  entregado.setEstatus("Declinado");
				  entregado.setMonto_entregado("0.0");
				  entregado.setNota("Se aplicaron cargos por servicio, total:"+(Double.parseDouble(tb_monto_comision.getDato1())+(Double.parseDouble(tb_monto_comision.getDato1())*0.16))+" al producto Ahorro");
				
				if(total_procesados > 0) {
				String termina_transaccion = funcionesService.terminar_transaccion(matriz.getFechatrabajo(),
				         registrar_movimiento.getPk().getIdusuario(),
				         registrar_movimiento.getPk().getSesion(),
				         registrar_movimiento.getPk().getReferencia());
				
			
				String eliminadoAutorizado = funcionesService.eliminarAutorizado(opa.getIdorigenp(),
																				 opa.getIdproducto(),
																				 opa.getIdauxiliar());
				log.info("Respuesta eliminar autorizado:"+eliminadoAutorizado);
				
				
				//Verificamos si el envio de sms esta activo
		     	if(Integer.parseInt(tb_sms_activo.getDato1()) == 1) {
		     		//si el servicio de sms esta activo,validamos que el monto entregado sea mayor o igual para enviar sms
		     		if(Double.parseDouble(entregado.getMonto_entregado()) >= Double.parseDouble(tb_minimo_sms.getDato1())) {		     			
		     			String sms_enviar = smsCsn.enviarSMS(tb_url_sms.getDato2(),persona.getTelefono(),
		     					tb_texto_sms_declinacion.getDato2().replace("@monto@", String.valueOf(registrar_movimiento.getMonto()+registrar_movimiento.getIva())));		     			
		     		}
		     	}
		        System.out.println("Mensaje termina transaccion:"+termina_transaccion);
		        
				}else {		
					log.info("No se completo la activacion del producto...");
					entregado.setNota("No se completo la activacion del producto...");
				}
			 }
		  }else {
			  log.info("Folio ya fue entregado con anterioridad");
			  entregado.setNota("Folio ya fue entregado con anterioridad");
		  }
			
		} catch (Exception e) {
			log.info("Error al realizar la dispersion del prestamo:"+e.getMessage());
			entregado.setNota(e.getMessage());
			return entregado;
		}
		log.info("Saliendo ws 3 dispersion...");
		return entregado;
	}
	
	public DetallesSiscore ResumenSiscoreCSN(Integer idorigenp,Integer idproducto,Integer idauxiliar) {
		DetallesSiscore consumoWsSiscore = new DetallesSiscore();
    	try {		
			
		List<PuntosScore> Listapuntos = new ArrayList<PuntosScore>();
		PuntosScore puntos = new PuntosScore();		
		//String siscore = siscoreCsn.requisitionImport(idorigenp+"-"+idproducto+"-"+idauxiliar);
		System.out.println("Vamos a conectar a siscore");
		String siscore = siscoreCsn.requisitionImport(idorigenp+"-"+idproducto+"-"+idauxiliar);
		System.out.println("Respuesta siscore:"+siscore);	
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
			System.out.println(opa.getIdorigenp()+"-"+opa.getIdproducto()+"-"+opa.getIdauxiliar());
			Auxiliar auxiliar = auxiliaresService.AuxiliarByOpa(opa.getIdorigenp(),opa.getIdproducto(),opa.getIdauxiliar());
			LocalDateTime localDate = LocalDateTime.parse(matriz.getFechatrabajo().toString().substring(0,19), dtf);
			Timestamp fecha_transferencia = Timestamp.valueOf(localDate);	
			String sesion = otrosService.sesion();
			int rn = (int) (Math.random() * 999999 + 1);
			String referencia = String.valueOf(rn) + "" + String.valueOf(opaReq);
			 // if(auxiliar.getMontosolicitado().doubleValue() <=60000) {
			
			RegistraMovimiento registrar_movimiento = new RegistraMovimiento();	
			 
			  // se cubre el adeudo del prestamo						  
			  /*registrar_movimiento.setIdorigenp(auxiliar.getIdorigenp());
			  registrar_movimiento.setIdproducto(auxiliar.getIdproducto());
			  registrar_movimiento.setIdauxiliar(auxiliar.getIdauxiliar());*/
			  registrar_movimiento.setFecha(fecha_transferencia);
			  /*registrar_movimiento.setIdusuario(999);
			  registrar_movimiento.setSesion(sesion);
			  registrar_movimiento.setReferencia(referencia);*/
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
			  /*registrar_movimiento.setIdorigenp(auxiliar_tdd.getIdorigenp());
			  registrar_movimiento.setIdproducto(auxiliar_tdd.getIdproducto());
			  registrar_movimiento.setIdauxiliar(auxiliar_tdd.getIdauxiliar());*/
			  registrar_movimiento.setFecha(fecha_transferencia);
			  /*registrar_movimiento.setIdusuario(999);
			  registrar_movimiento.setSesion(sesion);
			  registrar_movimiento.setReferencia(referencia);*/
			  registrar_movimiento.setIdorigen(auxiliar_tdd.getIdorigen());
			  registrar_movimiento.setIdgrupo(auxiliar_tdd.getIdgrupo());
			  registrar_movimiento.setIdsocio(auxiliar_tdd.getIdsocio());
			  registrar_movimiento.setCargoabono(0);
			  registrar_movimiento.setMonto(monto);
			  registrar_movimiento.setIva(Double.parseDouble(auxiliar_tdd.getIva().toString()));
			  registrar_movimiento.setTipo_amort(0);
			  registrar_movimiento.setSai_aux("");
			 
			  procesado = procesaMovimientoService.insertarMovimiento(registrar_movimiento);			 
			  
			  /*String datos_procesar = funcionesService.sai_aplica_transaccion(matriz.getFechatrabajo(),
				         registrar_movimiento.getIdusuario(),
				         registrar_movimiento.getSesion(),
				         registrar_movimiento.getReferencia());
		      int total_procesados = Integer.parseInt(String.valueOf(datos_procesar));*/
		      
		      
		      /*procesaMovimientoService.eliminaMovimiento(matriz.getFechatrabajo(),
				         registrar_movimiento.getIdusuario(),
				         registrar_movimiento.getSesion(),
				         registrar_movimiento.getReferencia());*/
		     
		    /*  if(total_procesados > 0) {
		    	  mensaje = "Se realizo el pago correctamente";
		      }
		      */
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
	        return fechaDate.replace("/","-");
	    }
	
	public int cal_edad(Date fecha_nac){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaNac = sdf.format(fecha_nac);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNac, formatter);
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        return edad.getYears();
    }


}
