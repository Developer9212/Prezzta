package com.fenoreste.rest.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatoRenovacionVO {
   
	private Double capital;
	private Double interes;
	private String notaRenovacion;
	
}
