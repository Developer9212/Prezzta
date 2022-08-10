package com.fenoreste.rest.dao;

import org.springframework.data.repository.CrudRepository;

import com.fenoreste.rest.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public User findUserByUsername(String username);
	
}
