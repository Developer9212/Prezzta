package com.fenoreste.rest.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "v_auxiliares")
@Data
public class V_Auxiliares {
   
	    @EmbeddedId
	    private AuxiliarPK pk;
	    @Column(name = "idorigen")
	    private Integer idorigen;
	    @Column(name = "idgrupo")
	    private Integer idgrupo;
	    @Column(name = "idsocio")
	    private Integer idsocio;
	    @Column(name = "fechaape")
	    @Temporal(TemporalType.DATE)
	    private Date fechaape;
	    @Column(name = "estatus")
	    private Short estatus;
	    @Column(name = "fechaactivacion")
	    @Temporal(TemporalType.DATE)
	    private Date fechaactivacion;
}
