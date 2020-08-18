package com.b3.users.gateway.h2;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b3.users.gateway.h2.entities.UserDao;


@Repository
public interface UserRepository extends JpaRepository<UserDao, Integer>{
	Optional<List<UserDao>> findByEmail(String email);
	Optional<List<UserDao>> findByCompanyId(Integer companyId);

}