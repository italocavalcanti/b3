package com.b3.users.usecase;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b3.users.domain.User;
import com.b3.users.domain.exceptions.ResourceNotFoundException;
import com.b3.users.gateway.h2.UserGateway;
import com.b3.users.gateway.http.json.request.UserRequest;
import com.b3.users.usecase.exceptions.ValidationException;
import com.b3.users.usecase.validator.ValidatorUser;

import io.undertow.util.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserUsecase {

	private UserGateway userGateway;
	public static final String ID_COMPANY = "Id Company";
	public static final String DESCRIPTION = "Invalid data";
	public static final String NOT_FOUND = "Not found Users";
	

	@Autowired
	public UserUsecase(UserGateway userGateway) {
		this.userGateway = userGateway;
	}

	public void registerUsers(List<User> users) {

		users.removeIf(u -> false == ValidatorUser.isUserValid(u));

		if (!users.isEmpty()) {
			log.info("saving users");

			userGateway.saveAll(users);
		}
	}

	public User creatUser(User user) throws BadRequestException, ValidationException {
		
		if (!ValidatorUser.isUserValid(user)) {
			HashMap<String, String> erros = new HashMap<String, String>();
			erros.put(ID_COMPANY, DESCRIPTION);
			throw new ValidationException(erros);
		}

		return userGateway.save(user);
	}

	public List<User> getUsers() throws ResourceNotFoundException {
		
		List<User> users = userGateway.getAllUsers();
		
		if(users.isEmpty()) {
			throw new ResourceNotFoundException(NOT_FOUND);
		}
		
		return users;
	}

	public User getUser(Integer id) throws ResourceNotFoundException {
		
		User user = userGateway.getUserById(id);
		
		if(user == null) {
			throw new ResourceNotFoundException(NOT_FOUND);
		}
		
		return user;
	}

	public List<User> getUsersByEmailOrCompany(String chave, Object value) throws ResourceNotFoundException {
		List<User> users = null;		
		
		if (chave.equals("idCompany")) {
			users = userGateway.getAllUsersByCompany((Integer) value);
		} else {
			users = userGateway.getAllUsersByEmail(String.valueOf(value));
		}

		if (users.isEmpty()) {
			throw new ResourceNotFoundException(NOT_FOUND);
		}

		return users;
	}

	public void deleteUser(Integer id) throws ResourceNotFoundException {
		try {
			userGateway.deleteUserById(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException(NOT_FOUND);
		}
	}

}