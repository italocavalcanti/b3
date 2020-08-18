package com.b3.users.gateway.h2.mapper;

import java.util.ArrayList;
import java.util.List;

import com.b3.users.domain.User;
import com.b3.users.gateway.h2.entities.UserDao;

public class UserToEntity {

	public static List<UserDao> parseObject(List<User> users) {

		List<UserDao> usersEntity = new ArrayList<UserDao>();
		users.forEach(u -> {
			usersEntity.add(parseObject(u));
		});

		return usersEntity;
	}

	public static UserDao parseObject(com.b3.users.domain.User user) {
		return UserDao.builder()
					.companyId(user.getCompanyId())
					.email(user.getEmail())
					.birthdate(user.getBirthdate())
				.build();
	}

	public static User toDomain(UserDao userSave) {
		
		return User.builder()
					 .id(userSave.getIdUser())
					 .birthdate(userSave.getBirthdate())
					 .companyId(userSave.getCompanyId())
					 .email(userSave.getEmail())
					.build();
	}
	
	public static List<User> toListDomain(List<UserDao> usersDao) {
		List<User> users = new ArrayList<>();
		
		usersDao.forEach(u->{
			users.add(toDomain(u));
		});
		
		return users;
		
	}
}