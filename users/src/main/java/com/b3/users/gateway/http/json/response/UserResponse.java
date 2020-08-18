package com.b3.users.gateway.http.json.response;


import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserResponse {
	
	@ApiModelProperty(value = "Id User")
	private Integer idUser;
	
	@ApiModelProperty(value = "Id Company")
	private Integer companyId;

	@ApiModelProperty(value = "email")
	private String email;

	@ApiModelProperty(value = "Data Nascimento")
	private LocalDate birthdate;

}
