package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ws_siscoop_folios_tarjetas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoliosTarjetas implements Serializable {
    @Id
    @Column(name = "idtarjeta")
    private String idtarjeta;
	@Column(name = "idorigenp")
    private int idorigenp;
    @Column(name = "idproducto")
    private int idproducto;
    @Column(name = "idauxiliar")
    private int idauxiliar;
    @Column(name = "asignada")
    private Boolean asignada;
    @Column(name = "activa")
    private Boolean activa;
    @Column(name = "bloqueada")
    private Boolean bloqueada; 
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    
    
    private static final long serialVersionUID = 1L;
}
