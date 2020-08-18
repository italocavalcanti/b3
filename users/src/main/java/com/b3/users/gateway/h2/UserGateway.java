package com.b3.users.gateway.h2;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.b3.users.domain.User;

public interface UserGateway {
	
	void saveAll(List<User> users);
	User save(User user) throws DataIntegrityViolationException;
	List<User> getAllUsers();
	List<User> getAllUsersByEmail(String email);
	List<User> getAllUsersByCompany(Integer idCompany);
	User getUserById(Integer id);
	void deleteUserById(Integer id);

}
