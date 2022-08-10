package com.fenoreste.rest.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CampoScore {
    
	private String  $id;
    private Integer IdCampo;
    private Integer IdFamilia;
    private String Nombre;
    private Integer TipoDato;
    private Integer TipoVariable;
    private String NombreTecnico;
}
