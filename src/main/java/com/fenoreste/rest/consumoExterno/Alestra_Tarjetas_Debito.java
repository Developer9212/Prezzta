package com.fenoreste.rest.consumoExterno;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fenoreste.rest.services.ITablaService;

@Service
public class Alestra_Tarjetas_Debito {
	
	@Autowired
	ITablaService tablasService;
	
	private static RestTemplate restTemplate=new RestTemplate();
	
	private static String basePath = "/api/cards/";
	
	public static String obtenerSaldo(String url,String idtarjeta) {
		JSONObject json = null;
		ResponseEntity<String> requisition = null;
		try {			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			//Busco la tabla que tiene la url de las tdd
			
			requisition =  restTemplate.exchange(url+basePath + "/getBalanceQuery/idcard="+idtarjeta, HttpMethod.GET, entity,String.class);
			System.out.println("Response:" + requisition.getBody());
		    json = new JSONObject(requisition.getBody());
		} catch (JSONException e) {
			System.out.println("Error al obtener saldo de cuenta:"+idtarjeta+","+ e.getMessage());
		}
		return json.toString();// requisition.getBody();
	}
	
	public static String retirarSaldo(String url,String idtarjeta,Double monto) {
		JSONObject json = null;
		ResponseEntity<String> requisition = null;
		try {			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			requisition =  restTemplate.exchange(url+basePath + "doWithdrawal/idcard="+idtarjeta+"&amount="+monto, HttpMethod.GET, entity,String.class);
			System.out.println("Response:" + requisition.getBody());
		    json = new JSONObject(requisition.getBody());
		} catch (JSONException e) {
			System.out.println("Error al realizar retiro de tarjeta:"+idtarjeta+"," + e.getMessage());
		}
		return json.toString();// requisition.getBody();
	}
	
	public static String depositarSaldo(String url,String idtarjeta,Double monto) {
		JSONObject json = null;
		ResponseEntity<String> requisition = null;
		try {			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			requisition =  restTemplate.exchange(url+basePath + "loadBalance/idcard="+idtarjeta+"&monto="+monto, HttpMethod.GET, entity,String.class);
			System.out.println("Response:" + requisition.getBody());
		    json = new JSONObject(requisition.getBody());
		} catch (JSONException e) {
			System.out.println("Error al deposito de tarjeta:"+idtarjeta+"," + e.getMessage());
		}
		return json.toString();// requisition.getBody();
	}
	
	
	
}
