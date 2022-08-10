package com.fenoreste.rest.services;

import org.springframework.stereotype.Service;

import com.fenoreste.rest.entity.CatalogoMenus;

@Service
public interface ICatalogoMenusService {
	public CatalogoMenus findByMenuOpcion(String menu,Integer opcion);
}
