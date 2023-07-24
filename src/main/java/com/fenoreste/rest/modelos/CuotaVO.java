package com.fenoreste.rest.modelos;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuotaVO implements Serializable{
	
	    private Integer idorigenp;
	    private Integer idproducto;
	    private Integer idauxiliar; 
	    private Integer idamortizacion;
	    private String vence;
	    private BigDecimal abono;
	    private BigDecimal io;
	    private BigDecimal abonopag;
	    private BigDecimal iopag;
	    private Boolean bonificado;
	    private Boolean pagovariable;
	    private Boolean todopag;
	    private Boolean atiempo;
	    private BigDecimal bonificacion;
	    private BigDecimal anualidad;   
	    private Integer diasvencidos;
	    @JsonInclude(value = Include.NON_EMPTY)
	    private Double saldoInsoluto;
	    @JsonInclude(value = Include.NON_EMPTY)
	    private BigDecimal ivaIntereses;
	    @JsonInclude(value = Include.NON_EMPTY)
	    private Double montoTotal;
	    
	    private static final long serialVersionUID = 1L;
}
