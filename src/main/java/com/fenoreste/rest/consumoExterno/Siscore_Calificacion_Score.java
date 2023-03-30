package com.fenoreste.rest.consumoExterno;

import java.util.Arrays;

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

import com.fenoreste.rest.Util.FicheroConexion;
import com.fenoreste.rest.entity.Tabla;
import com.fenoreste.rest.entity.TablaPK;
import com.fenoreste.rest.services.ITablaService;

@Service
public class Siscore_Calificacion_Score {
	@Autowired
	ITablaService tablasService;
	
	static String domain = "";
	static String basePath = "/api";
	static String ping = "/ping";
	static String importRequisition = "/requisition/import/{OPA}";
	
	private static RestTemplate restTemplate = new RestTemplate();

	public String getPing() {
		TablaPK tb_pk = new TablaPK("prezzta","url_servicios_score");
		Tabla tb_base_url_siscore  = tablasService.buscarPorId(tb_pk);
		domain = tb_base_url_siscore.getDato1();
		System.out.println("Intentando hacer ping a siscore");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> lista = restTemplate.exchange(domain + basePath + ping, HttpMethod.GET, entity,String.class);

		return lista.getBody();
	}

	public String requisitionImport(String opa) {
		System.out.println("Servicios Score");
		JSONObject json = null;
		ResponseEntity<String> requisition = null;
		try {
			TablaPK tb_pk = new TablaPK("prezzta","url_servicios_score");
			Tabla tb_base_url_siscore  = tablasService.buscarPorId(tb_pk);
			domain = tb_base_url_siscore.getDato1()+":"+tb_base_url_siscore.getDato2();
			System.out.println("Intentando obtener calificacion para el opa :" + opa);
			System.out.println("Endpoint :" +domain+basePath+importRequisition);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>("", headers);
			requisition =  restTemplate.exchange(domain + basePath + importRequisition, HttpMethod.POST, entity,String.class, opa);
			System.out.println("Response:" + requisition.getBody());
		    json = new JSONObject(requisition.getBody());
		} catch (JSONException e) {
			System.out.println("Error al consumir siscore:" + e.getMessage());
		}
		return json.toString();// requisition.getBody();
	}
	
	public String requisitionImportTest(String opa) {
		JSONObject json = null;
		ResponseEntity<String> requisition = null;
		try {
			System.out.println("Intentando obtener calificacion para el opa :" + opa);
			FicheroConexion f = new FicheroConexion();			
			json = new JSONObject(f.obtenerTexto());
		} catch (JSONException e) {
			System.out.println("Error al consumir siscore:" + e.getMessage());
		}
		return json.toString();
	}
	


}
