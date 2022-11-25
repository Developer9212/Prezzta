/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fenoreste.rest.modelos.DetallesSiscore;
import com.fenoreste.rest.modelos.InfoClienteDTO;
import com.fenoreste.rest.modelos.InfoDetallesSiscoreDTO;
import com.fenoreste.rest.modelos.InfoPrestamoCreadoDTO;
import com.fenoreste.rest.modelos.InfoSolicitudDTO;
import com.fenoreste.rest.modelos.PrestamoCreadoDTO;
import com.fenoreste.rest.modelos.PrestamoEntregado;
import com.fenoreste.rest.modelos.clientRequestDTO;
import com.fenoreste.rest.modelos.dataDTO;
import com.fenoreste.rest.modelos.requestRegistraPrestamo;
import com.fenoreste.rest.modelos.validacionDTO;
import com.fenoreste.rest.services.CustomerServiceSpring;
import com.github.cliftonlabs.json_simple.JsonObject;


/**
 *
 * @author Elliot
 */

@RestController
@RequestMapping({"/Clients" })
public class CustomerController {

	@Autowired
	private CustomerServiceSpring serviceCustomerSpring;	
	
    @PostMapping(value = "/buscar", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> cliente(@RequestBody clientRequestDTO request){    
    	System.out.println("Iniciando ws 1....");
        InfoClienteDTO info= null;
        //leemos el request
        try {        	
         dataDTO dto = serviceCustomerSpring.informacionPersona(request.getTipo_documento(),request.getNumero_documento());
           if(dto.getOgs() != null) {
        	   info = new InfoClienteDTO();        	   
        	   info.setCode(200);
        	   info.setData(dto);
        	   info.setMessage("Datos verificados con exito");
        	   System.out.println("Terminando ws 1....");
        	   return new ResponseEntity<>(info, HttpStatus.OK);
           }else {
        	   info = new InfoClienteDTO();
        	   info.setCode(400);
        	   info.setData(null);
        	   info.setMessage("No se encontraron registros para: "+request.getNumero_documento());
        	   System.out.println("Error en ws 1 No se encontraron registros....");
        		return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
           }
        
        }catch (Exception e) {
			System.out.println("Error en Ws 1:"+e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
        
    }  

    
    @PostMapping(value = "/solicitud/registra", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> solicitud(@RequestBody requestRegistraPrestamo request) {
    	InfoPrestamoCreadoDTO info = new InfoPrestamoCreadoDTO();
    	
    	try {
    		PrestamoCreadoDTO prestamo= serviceCustomerSpring.aperturaFolio(request.getNum_socio(),request.getMonto().doubleValue(),request.getPlazos());
    		if(prestamo.getOpa() != null) {
    			info.setCode(200);
    			info.setMessage("El prestamo se ha aperturado con exito");
    			info.setData(prestamo);
    			return new ResponseEntity<>(info,HttpStatus.OK);
    		}else {
    			info.setCode(400);
    			info.setMessage("Error al aperturar o renovar el prestamo");
    			info.setNota(prestamo.getNota());
    			info.setData(null);
    			return new ResponseEntity<>(info,HttpStatus.BAD_REQUEST);
    		}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    }
    
    @GetMapping(value="/solicitud/backend/calificacion/opa={opa}&bandera={confirmacion}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?>calificacionBackendPrezzta(@PathVariable String opa,@PathVariable String confirmacion){
    	JsonObject mensaje = new JsonObject();
    	try {
    		 
    		   if(confirmacion.equalsIgnoreCase("si") || confirmacion.equalsIgnoreCase("no")){
    			   String mensaje1 = serviceCustomerSpring.calificacionPrezztaBackend(opa, confirmacion);
    			   if(mensaje1.equalsIgnoreCase("recibido")) {
    				   mensaje.put("code",200);
    				   mensaje.put("mensaje",mensaje1);
    				   return new ResponseEntity<>(mensaje,HttpStatus.OK);
    			   }else {
    				   mensaje.put("code",500);
    				   mensaje.put("mensaje","Error en el servidor");
    				   return new ResponseEntity<>(mensaje,HttpStatus.INTERNAL_SERVER_ERROR);
    			   }
    			   
    		   }else {
    			   mensaje.put("code",400);
        		   mensaje.put("mensaje","opcion no valida,para confirmar=SI para declinar=NO");
        		  return new ResponseEntity<>(mensaje,HttpStatus.BAD_REQUEST);
        	  }
         
		} catch (Exception e) {
		   return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    }
    
    //En este paso verificamos si el usuario prosigue o se queda hasta ahi entonces aplicamos un descuento al ahorro(parametrizada)
    @GetMapping(value = "/solicitud/finalizar/opa={opa}&confirmar={opcion}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> terminaSolicitud(@PathVariable String opa , @PathVariable String opcion) {   
          JsonObject response = new JsonObject();
          try {
        	  if(opcion.equalsIgnoreCase("si") || opcion.equalsIgnoreCase("no")) {
        		  PrestamoEntregado entregado = serviceCustomerSpring.entregarPrestamo(opa, opcion);
            	 
            	 if(entregado.getMonto_entregado() != null && Double.parseDouble(entregado.getMonto_entregado())>0) {
            		 response.put("code", 200);
            		 response.put("mensaje", "Solicitud terminada exitosamente.");
            		 response.put("detallesDispersion", entregado);
            		 return new ResponseEntity<>(response,HttpStatus.OK);
            	 }else {
            		 response.put("code", 400);
            		 response.put("mensaje", "La solicitud se ha declinado.");
            		 response.put("detallesDispersion", entregado);
            		 return new ResponseEntity<>(response,HttpStatus.CONFLICT);
            	 }            	 
            	 
              }else {
            	  response.put("code", 400);
            	  response.put("menaje","opcion no valida,para confirmar=SI para declinar=NO");
            	  response.put("detalleDispersion", null);
            	  return new ResponseEntity<>(response,HttpStatus.CONFLICT);
              }
          } catch (Exception e) {
        	  return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
          }
    	  
    }
    
    @GetMapping(value = "/liquidarAdeudos/opa={opa}&monto={total}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> pagarAdeudo(@PathVariable String opa ,@PathVariable Double total) {   
          JsonObject response = new JsonObject();
          String mensaje_pago = serviceCustomerSpring.pruebaPagoIntereses(opa, total);
     	  response.put("mensaje", mensaje_pago);
          return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    
}
