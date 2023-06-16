package com.fenoreste.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenoreste.rest.dao.ProductoRepository;
import com.fenoreste.rest.entity.Producto;

@Service
public class ProductoServiceImpl implements IProductoService{
	
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public Producto buscarPorId(Integer idproducto) {
		return productoRepository.findById(idproducto).orElse(null);
	}

}
