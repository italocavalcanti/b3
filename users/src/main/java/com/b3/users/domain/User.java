package com.b3.users.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.b3.users.domain.exceptions.DataInvalidaException;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	private Integer id;
	private Integer companyId;
	private String email;
	private LocalDate birthdate;

	public static User convertToUser(String usuario) throws DataInvalidaException {
		UserBuilder builder = User.builder();
		String[] splitUser = usuario.split(";");

		builder.companyId(Integer.valueOf(splitUser[0]));
		builder.email(splitUser[1]);
		builder.birthdate(formartDate(splitUser[2]));

		return builder.build();

	}

	public static LocalDate formartDate(String data) throws DataInvalidaException {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			LocalDate localDate = LocalDate.parse(data, formatter);
			return localDate;
		} catch (Exception e) {
			throw new DataInvalidaException("Data inválida");
		}

	}
}
