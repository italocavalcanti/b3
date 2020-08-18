package com.b3.users.gateway.http.mapper;

import java.util.ArrayList;
import java.util.List;

import com.b3.users.domain.User;
import com.b3.users.domain.exceptions.DataInvalidaException;
import com.b3.users.gateway.http.json.request.UserRequest;
import com.b3.users.gateway.http.json.response.UserResponse;

public class UserMapper {
	
	
	public static User toDomain(UserRequest userRequest) throws DataInvalidaException {
		
		return User.builder()
					.birthdate(User.formartDate(userRequest.getBirthdate()))
					.email(userRequest.getEmail())
					.companyId(userRequest.getCompanyId())
				.build();
	}
	
	
	public static UserResponse toResponse(User user) {
			
			return UserResponse.builder()
						.birthdate(user.getBirthdate())
						.email(user.getEmail())
						.companyId(user.getCompanyId())
						.idUser(user.getId())
					.build();
		}
	
	
	public static List<UserResponse> toResponseListUser(List<User> users) {
		List<UserResponse> usersResponse = new ArrayList<UserResponse>();
		
		users.forEach(u->{
			usersResponse.add(toResponse(u));
		});
		
		return usersResponse;
	}
}