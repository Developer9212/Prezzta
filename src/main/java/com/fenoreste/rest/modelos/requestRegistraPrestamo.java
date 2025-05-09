package com.fenoreste.rest.modelos;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class requestRegistraPrestamo {
     
	private String num_socio;
    private BigDecimal monto;
    private Integer plazos;
    private int producto_id;

}
