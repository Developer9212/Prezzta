package com.fenoreste.rest.modelos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class validacionDTO {
   	
	public String rangoMontos;
	public String rangoPlazos;
	public String tipo_apertura;
	public String nota;
	
	public double monto_renovar;
	
}
