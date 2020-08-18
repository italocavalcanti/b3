package com.b3.users.usecase.validator;

import java.util.Arrays;
import java.util.List;

import com.b3.users.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidatorUser {

	private static final List<String> COMPANY_ID = Arrays.asList("1", "2", "5", "7", "10");

	public static boolean isUserValid(User u) {

		boolean isValid = true;
		log.info("validating User");

		if (!COMPANY_ID.contains(u.getCompanyId().toString())) {
			isValid = false;
			log.info("User with COMPANY_ID inválid : {}", u);
		}

		if (u.getEmail() == null || !u.getEmail().contains("@")) {
			isValid = false;
			log.info("User with Email inválid : {}", u);
		}

	
		return isValid;

	}
}
