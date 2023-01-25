package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.rest.entity.AuxiliarPK;
import com.fenoreste.rest.entity.FolioTarjeta;


public interface FolioTarjetaRepository extends JpaRepository<FolioTarjeta, AuxiliarPK>{

}
