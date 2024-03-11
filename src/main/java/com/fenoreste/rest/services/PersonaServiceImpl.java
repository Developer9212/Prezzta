package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.PersonaRepository;
import com.fenoreste.rest.entity.Persona;
import com.fenoreste.rest.entity.PersonaPK;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonaServiceImpl implements IPersonaService {

	
	@Autowired
	private PersonaRepository personaRepository;

	@Override
	public Persona findPersonaByDocumento(String tipoDocumento, String documento) {
		log.info("En persona service:"+documento);
		Persona p = personaRepository.buscarPorCurp(documento);
		if (p != null) {
			if (caracteres_especiales(p.getCalle())) {
				log.info("Si en calle:"+p.getCalle());
				p.setCalle(nuevaCadena(p.getCalle()));
			}
			if (caracteres_especiales(p.getEntrecalles())) {
				log.info("Si en entre calles:"+p.getEntrecalles());
				p.setEntrecalles(nuevaCadena(p.getEntrecalles()));
			}
			if (caracteres_especiales(p.getNombre())) {
				log.info("Si en nombre:"+p.getNombre());
				p.setNombre(nuevaCadena(p.getNombre()));
			}
			if (caracteres_especiales(p.getAppaterno())) {
				log.info("Si en appaterno:"+p.getEntrecalles());
				p.setAppaterno(nuevaCadena(p.getAppaterno()));
			}
			if (caracteres_especiales(p.getApmaterno())) {
				log.info("Si en apmaterno:"+p.getApmaterno());
				p.setApmaterno(nuevaCadena(p.getApmaterno()));
			}			
			if (caracteres_especiales(p.getEmail())) {
				p.setEmail(nuevaCadena(p.getEmail()));
			}
		}
		return p;// personaRepository.buscarPorCurp(documento);
	}

	@Override
	public Persona findByOgs(PersonaPK pk) {
		return personaRepository.findById(pk).orElse(null);
	}

	private boolean caracteres_especiales(String nombre) {
		boolean bandera = false;
		if (!nombre.matches("^[a-zA-Z0-9\\s]*$")) {
			bandera = true;
		}
		return bandera;
	}

	private String nuevaCadena(String nombre) {
		return nombre.replace(obtenerCaracteresEspeciales(nombre), "Ñ").replace("Ã", "");
	}

	private String obtenerCaracteresEspeciales(String input) {
		StringBuilder caracteresEspeciales = new StringBuilder();
		for (char c : input.toCharArray()) {
			if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
				caracteresEspeciales.append(c);
			}
		}
		return caracteresEspeciales.toString();
	}

}
