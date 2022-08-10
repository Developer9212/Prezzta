/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wilmer
 */
@Entity
@Table(name = "socioeconomicos")
public class Socioeconomicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer idorigen;
    private Integer idgrupo;
    private Integer idsocio;
    private Integer estatusvivienda;
    private Double mensualidadvivienda;
    @Temporal(TemporalType.DATE)
    private Date fechahabitacion;
    private Integer dependientes;
    private Double ingresosordinarios;
    private Double ingresosextraordinarios;
    private Double gastosordinarios;
    private Double gastosextraordinarios;
    private String propietariovivienda;
    private Integer tipoingresos;
    private Double valorpropiedad;
    private Integer dependientes_menores;
    private Integer banco;
    private String cuenta;
    private String clabe;
    private Integer gastos_tipo1;
    private Integer gastos_tipo2;
    private Integer gastos_tipo3;
    private Integer gastos_tipo4;
    private Integer gastos_tipo5;
    private Integer gastos_tipo6;
    private String cuenta_int;
    private String clabe_int;
    private Integer bancodom;
    private String cuentadom;
    private Integer tipo_cuentadom;
    private Boolean bloqueo_info_bancaria;

    public Socioeconomicos() {
    }

	public Integer getIdorigen() {
		return idorigen;
	}

	public void setIdorigen(Integer idorigen) {
		this.idorigen = idorigen;
	}


	public Integer getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(Integer idgrupo) {
		this.idgrupo = idgrupo;
	}

	public Integer getIdsocio() {
		return idsocio;
	}

	public void setIdsocio(Integer idsocio) {
		this.idsocio = idsocio;
	}

	public Integer getEstatusvivienda() {
		return estatusvivienda;
	}

	public void setEstatusvivienda(Integer estatusvivienda) {
		this.estatusvivienda = estatusvivienda;
	}

	public Double getMensualidadvivienda() {
		return mensualidadvivienda;
	}

	public void setMensualidadvivienda(Double mensualidadvivienda) {
		this.mensualidadvivienda = mensualidadvivienda;
	}

	public Date getFechahabitacion() {
		return fechahabitacion;
	}

	public void setFechahabitacion(Date fechahabitacion) {
		this.fechahabitacion = fechahabitacion;
	}

	public Integer getDependientes() {
		return dependientes;
	}

	public void setDependientes(Integer dependientes) {
		this.dependientes = dependientes;
	}

	public Double getIngresosordinarios() {
		return ingresosordinarios;
	}

	public void setIngresosordinarios(Double ingresosordinarios) {
		this.ingresosordinarios = ingresosordinarios;
	}

	public Double getIngresosextraordinarios() {
		return ingresosextraordinarios;
	}

	public void setIngresosextraordinarios(Double ingresosextraordinarios) {
		this.ingresosextraordinarios = ingresosextraordinarios;
	}

	public Double getGastosordinarios() {
		return gastosordinarios;
	}

	public void setGastosordinarios(Double gastosordinarios) {
		this.gastosordinarios = gastosordinarios;
	}

	public Double getGastosextraordinarios() {
		return gastosextraordinarios;
	}

	public void setGastosextraordinarios(Double gastosextraordinarios) {
		this.gastosextraordinarios = gastosextraordinarios;
	}

	public String getPropietariovivienda() {
		return propietariovivienda;
	}

	public void setPropietariovivienda(String propietariovivienda) {
		this.propietariovivienda = propietariovivienda;
	}

	public Integer getTipoingresos() {
		return tipoingresos;
	}

	public void setTipoingresos(Integer tipoingresos) {
		this.tipoingresos = tipoingresos;
	}

	public Double getValorpropiedad() {
		return valorpropiedad;
	}

	public void setValorpropiedad(Double valorpropiedad) {
		this.valorpropiedad = valorpropiedad;
	}

	public Integer getDependientes_menores() {
		return dependientes_menores;
	}

	public void setDependientes_menores(Integer dependientes_menores) {
		this.dependientes_menores = dependientes_menores;
	}

	public Integer getBanco() {
		return banco;
	}

	public void setBanco(Integer banco) {
		this.banco = banco;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	public Integer getGastos_tipo1() {
		return gastos_tipo1;
	}

	public void setGastos_tipo1(Integer gastos_tipo1) {
		this.gastos_tipo1 = gastos_tipo1;
	}

	public Integer getGastos_tipo2() {
		return gastos_tipo2;
	}

	public void setGastos_tipo2(Integer gastos_tipo2) {
		this.gastos_tipo2 = gastos_tipo2;
	}

	public Integer getGastos_tipo3() {
		return gastos_tipo3;
	}

	public void setGastos_tipo3(Integer gastos_tipo3) {
		this.gastos_tipo3 = gastos_tipo3;
	}

	public Integer getGastos_tipo4() {
		return gastos_tipo4;
	}

	public void setGastos_tipo4(Integer gastos_tipo4) {
		this.gastos_tipo4 = gastos_tipo4;
	}

	public Integer getGastos_tipo5() {
		return gastos_tipo5;
	}

	public void setGastos_tipo5(Integer gastos_tipo5) {
		this.gastos_tipo5 = gastos_tipo5;
	}

	public Integer getGastos_tipo6() {
		return gastos_tipo6;
	}

	public void setGastos_tipo6(Integer gastos_tipo6) {
		this.gastos_tipo6 = gastos_tipo6;
	}

	public String getCuenta_int() {
		return cuenta_int;
	}

	public void setCuenta_int(String cuenta_int) {
		this.cuenta_int = cuenta_int;
	}

	public String getClabe_int() {
		return clabe_int;
	}

	public void setClabe_int(String clabe_int) {
		this.clabe_int = clabe_int;
	}

	public Integer getBancodom() {
		return bancodom;
	}

	public void setBancodom(Integer bancodom) {
		this.bancodom = bancodom;
	}

	public String getCuentadom() {
		return cuentadom;
	}

	public void setCuentadom(String cuentadom) {
		this.cuentadom = cuentadom;
	}

	public Integer getTipo_cuentadom() {
		return tipo_cuentadom;
	}

	public void setTipo_cuentadom(Integer tipo_cuentadom) {
		this.tipo_cuentadom = tipo_cuentadom;
	}

	public Boolean getBloqueo_info_bancaria() {
		return bloqueo_info_bancaria;
	}

	public void setBloqueo_info_bancaria(Boolean bloqueo_info_bancaria) {
		this.bloqueo_info_bancaria = bloqueo_info_bancaria;
	}

	@Override
	public String toString() {
		return "Socioeconomicos [idorigen=" + idorigen+ ", idgrupo=" + idgrupo + ", idsocio=" + idsocio
				+ ", estatusvivienda=" + estatusvivienda + ", mensualidadvivienda=" + mensualidadvivienda
				+ ", fechahabitacion=" + fechahabitacion + ", dependientes=" + dependientes + ", ingresosordinarios="
				+ ingresosordinarios + ", ingresosextraordinarios=" + ingresosextraordinarios + ", gastosordinarios="
				+ gastosordinarios + ", gastosextraordinarios=" + gastosextraordinarios + ", propietariovivienda="
				+ propietariovivienda + ", tipoingresos=" + tipoingresos + ", valorpropiedad=" + valorpropiedad
				+ ", dependientes_menores=" + dependientes_menores + ", banco=" + banco + ", cuenta=" + cuenta
				+ ", clabe=" + clabe + ", gastos_tipo1=" + gastos_tipo1 + ", gastos_tipo2=" + gastos_tipo2
				+ ", gastos_tipo3=" + gastos_tipo3 + ", gastos_tipo4=" + gastos_tipo4 + ", gastos_tipo5=" + gastos_tipo5
				+ ", gastos_tipo6=" + gastos_tipo6 + ", cuenta_int=" + cuenta_int + ", clabe_int=" + clabe_int
				+ ", bancodom=" + bancodom + ", cuentadom=" + cuentadom + ", tipo_cuentadom=" + tipo_cuentadom
				+ ", bloqueo_info_bancaria=" + bloqueo_info_bancaria + "]";
	}

    
    

}
