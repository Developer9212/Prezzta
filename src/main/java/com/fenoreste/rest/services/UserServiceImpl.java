package com.fenoreste.rest.services;

import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenoreste.rest.dao.UserRepository;
import com.fenoreste.rest.entity.User;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService{
    
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuario no valido");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE ADMIN")));
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
    	return (List<User>)userRepository.findAll();
	}

	@Override
	@javax.transaction.Transactional
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Integer id) {
		
		return userRepository.findById(id).orElse(null);
	}

	

}
