package com.b3.users.gateway.h2;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.b3.users.domain.User;
import com.b3.users.gateway.h2.entities.UserDao;
import com.b3.users.gateway.h2.mapper.UserToEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserGatewayImpl implements UserGateway {

	private UserRepository userRepository;

	@Autowired
	public UserGatewayImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void saveAll(List<User> users) {
//		userRepository.saveAll();

		UserToEntity.parseObject(users).forEach(s -> {
			try {
				userRepository.saveAndFlush(s);
			} catch (DataIntegrityViolationException e) {
				log.error("User contraint violation {} ", e.getCause());
			}
		});
	}

	@Override
	public User save(User user) throws DataIntegrityViolationException {

		UserDao userDao = userRepository.save(UserToEntity.parseObject(user));
		return UserToEntity.toDomain(userDao);
	}

	@Override
	public List<User> getAllUsers() {
		return UserToEntity.toListDomain(userRepository.findAll());
	}

	@Override
	public List<User> getAllUsersByEmail(String email) {
		List<UserDao> users = userRepository.findByEmail(email).orElse(Collections.EMPTY_LIST);

		return UserToEntity.toListDomain(users);
	}

	@Override
	public List<User> getAllUsersByCompany(Integer idCompany) {
		List<UserDao> users = userRepository.findByCompanyId(idCompany).orElse(Collections.EMPTY_LIST);

		return UserToEntity.toListDomain(users);
	}

	@Override
	public User getUserById(Integer id) {

		Optional<UserDao> user = userRepository.findById(id);

		if (!user.isPresent()) {
			return null;
		}

		return UserToEntity.toDomain(user.get());
	}

	@Override
	public void deleteUserById(Integer id) {
		userRepository.deleteById(id);
	}
}