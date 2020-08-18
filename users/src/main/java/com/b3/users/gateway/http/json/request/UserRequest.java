package com.b3.users.gateway.http.json.request;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@Builder
public class UserRequest implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1097204247789722257L;

	@NotNull
	@Size(max = 10000)
	@NotEmpty
	@ApiModelProperty(value = "id company", required = true, example = "1")
	private Integer companyId;

	@NotNull
	@Size(max = 255)
	@NotEmpty
	@ApiModelProperty(value = "email", required = true)
	private String email;

	@NotNull
	@NotEmpty
	@ApiModelProperty(value = "data Nascimento", required = true, example = "05/05/2018")
	private String birthdate;

}
