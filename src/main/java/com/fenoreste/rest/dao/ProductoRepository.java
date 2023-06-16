package com.fenoreste.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fenoreste.rest.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Integer>{
	
	

}
