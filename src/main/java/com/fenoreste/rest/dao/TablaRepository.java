package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.rest.entity.Tabla;
import com.fenoreste.rest.entity.TablaPK;

public interface TablaRepository extends JpaRepository<Tabla,TablaPK> {

}
