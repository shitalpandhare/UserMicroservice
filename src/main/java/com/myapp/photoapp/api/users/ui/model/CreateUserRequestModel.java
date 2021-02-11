package com.myapp.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

	@NotNull(message="firstname cannot be null")
	@Size(min=2,message="firstname cannot be less than 2 characters")
	private String firstName;
	
	@NotNull(message="lastname cannot be null")
	@Size(min=2,message="lastname cannot be less than 2 characters")
	private String lastName;
	
	@NotNull(message="email cannot be null")
	@Email(message=" please enter valid email")
	private String email;
	
	@NotNull(message="password cannot be null")
	@Size(min=8,max=16,message="password is min 8 characters and max 16 characters")
	private String password;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
