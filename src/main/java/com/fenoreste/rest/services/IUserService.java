package com.fenoreste.rest.services;

import java.util.List;

import com.fenoreste.rest.entity.User;

public interface IUserService {
   
	public List<User>findAll();
	
	public void save(User user);
	
	public User findById(Integer id);
}
