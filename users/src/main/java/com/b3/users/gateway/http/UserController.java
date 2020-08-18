package com.b3.users.gateway.http;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.b3.users.domain.User;
import com.b3.users.domain.exceptions.DataInvalidaException;
import com.b3.users.domain.exceptions.ResourceNotFoundException;
import com.b3.users.gateway.http.json.request.UserRequest;
import com.b3.users.gateway.http.json.response.UserResponse;
import com.b3.users.gateway.http.mapper.UserMapper;
import com.b3.users.usecase.UserUsecase;
import com.b3.users.usecase.exceptions.ValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.undertow.util.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Api(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController extends CustomRestExceptionHandler {

	private UserUsecase userUsecase;
	

	@Autowired
	public UserController(UserUsecase userUsecase) {
		this.userUsecase = userUsecase;
	}

	@ApiOperation(value = "Created user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "User Created"),
			@ApiResponse(code = 400, message = "Bad request creating User"),
			@ApiResponse(code = 403, message = "Access is forbidden"),
			@ApiResponse(code = 422, message = "Error creating User"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping
	public ResponseEntity<?> saveUser(@RequestBody UserRequest userRequest) {

		ResponseEntity<?> response = null;
		log.info("Request received save User");

		try {
			User user = userUsecase.creatUser(UserMapper.toDomain(userRequest));

			log.info("new user created");

			response = new ResponseEntity<>(UserMapper.toResponse(user), HttpStatus.CREATED);
		} catch (BadRequestException e) {
			response = new ResponseEntity<>("Integration error ", HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (ValidationException e) {
			response = new ResponseEntity<>("Validation Data: " + e.getErrorsMap(), HttpStatus.BAD_REQUEST);
		} catch (DataInvalidaException e) {
			response = new ResponseEntity<>("Invalid date " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	@ApiOperation(value = "Search all Users ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success to find the Users"),
			@ApiResponse(code = 404, message = "Users not found"),
			@ApiResponse(code = 403, message = "Access is forbidden"),
			@ApiResponse(code = 422, message = "Error Search Users"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping
	public ResponseEntity<?> getUsers() throws BadRequestException {

		log.info("Request received get Users");
		ResponseEntity<?> response = null;

		try {
			List<UserResponse> userResponse = UserMapper.toResponseListUser(userUsecase.getUsers());
			response = new ResponseEntity<>(userResponse, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@ApiOperation(value = "Search User by Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success to find the User"),
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 403, message = "Access is forbidden"),
			@ApiResponse(code = 422, message = "Error creating User"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("id/{id}")
	public ResponseEntity<?> getUserByID(@RequestParam(value="id") Integer id) throws BadRequestException {

		log.info("Request received get User by id");
		ResponseEntity<?> response = null;

		try {
			UserResponse userResponse = UserMapper.toResponse(userUsecase.getUser(id));
			response = new ResponseEntity<>(userResponse, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@ApiOperation(value = "Search User by idCompany")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success to find the User"),
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 403, message = "Access is forbidden"),
			@ApiResponse(code = 422, message = "Error creating User"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("idCompany/{idCompany}")
	public ResponseEntity<?> getUserByIdCompany(@RequestParam(value="idCompany") Integer idCompany) throws BadRequestException {

		log.info("Request received get User by idCompany");
		ResponseEntity<?> response = null;

		try {
			List<UserResponse> usersResponse = UserMapper.toResponseListUser(userUsecase.getUsersByEmailOrCompany("idCompany", idCompany));
			response = new ResponseEntity<>(usersResponse, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@ApiOperation(value = "Search Users by email")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success to find the User"),
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 403, message = "Access is forbidden"),
			@ApiResponse(code = 422, message = "Error creating User"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping("email/{email}")
	public ResponseEntity<?> getUserByEmail(@RequestParam(value="email") String email) throws BadRequestException {

		log.info("Request received get User by email");
		ResponseEntity<?> response = null;

		try {
			List<UserResponse> usersResponse = UserMapper.toResponseListUser((userUsecase.getUsersByEmailOrCompany("email", email)));
			response = new ResponseEntity<>(usersResponse, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@ApiOperation(value = "Delete User by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success to delete the User"),
			@ApiResponse(code = 404, message = "User not found"),
			@ApiResponse(code = 403, message = "Access is forbidden"),
			@ApiResponse(code = 422, message = "Error delete User"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") Integer id) throws BadRequestException {

		log.info("Request received delete user ");
		ResponseEntity<?> response = null;

		try {
			userUsecase.deleteUser(id);
			response = new ResponseEntity<>("successfully deleted", HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
}