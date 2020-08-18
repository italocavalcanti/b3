package com.b3.users.gateway.listener.readfile;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.b3.users.domain.User;
import com.b3.users.domain.exceptions.DataInvalidaException;
import com.b3.users.usecase.UserUsecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReadFileUsers {

	private UserUsecase userUsecase;

	@Autowired
	public ReadFileUsers(UserUsecase userUsecase) {
		this.userUsecase = userUsecase;
	}

	public void process(Reader in) throws IOException {

		List<User> users = new ArrayList<>();

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			try {
				users.add(User.convertToUser(record.get(0)));
			} catch (DataInvalidaException e) {
				log.info("invalid date user : {}", record);
			}
		}
		
		log.info("Quantidade de usuários: {}", users.size());
		
		userUsecase.registerUsers(users);
		
		log.info("Usuários Processados");

	}
}