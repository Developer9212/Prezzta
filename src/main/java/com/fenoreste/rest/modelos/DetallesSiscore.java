package com.fenoreste.rest.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties("idsolicitud")
public class DetallesSiscore {
	
	private Integer idsolicitud;
	List<DetallesScore>detalles;
	List<PuntosScore>Resumen;
	List<BanderasSiscore> Banderas;
	private Boolean hasFlags;
	
}
