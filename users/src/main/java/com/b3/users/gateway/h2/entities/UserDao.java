package com.b3.users.gateway.h2.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames={"companyId", "email"}))
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer idUser;

	@NotNull
	@NotEmpty
	@Column(name = "companyId")
	private Integer companyId;

	@NotNull
	@NotEmpty
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "birthdate")
	@NotEmpty
	private LocalDate birthdate;

}