package com.myapp.photoapp.api.users.ui.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.photoapp.api.users.service.UsersService;
import com.myapp.photoapp.api.users.shared.UserDto;
import com.myapp.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.myapp.photoapp.api.users.ui.model.CreateUserResponseModel;

@RestController
@RequestMapping(path = "/users")
public class UsersController {

	@Autowired
	private Environment env;

	@Autowired
	UsersService userService;
	
	

	@GetMapping
	public String status() {
		return "working on port " + env.getProperty("local.server.port");
	}

	@PostMapping(produces = 
		{ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	,consumes=
		{ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);

		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

//		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);

		return new ResponseEntity<CreateUserResponseModel>(returnValue, HttpStatus.CREATED);
	}

}
