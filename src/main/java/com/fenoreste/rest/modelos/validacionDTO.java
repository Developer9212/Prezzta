package com.fenoreste.rest.modelos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class validacionDTO {
   	
	public String rangoMontos;
	public String rangoPlazos;
	public String tipo_apertura;
	public String nota;	
	@JsonInclude(value = Include.NON_NULL)
	public DatoRenovacionVO detalleRenovacion;
	public double monto_renovar;
	
}
