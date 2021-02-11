package com.myapp.photoapp.api.users.serviceimpl;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.photoapp.api.users.data.UserEntity;
import com.myapp.photoapp.api.users.data.UsersRepository;
import com.myapp.photoapp.api.users.service.UsersService;
import com.myapp.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {

	BCryptPasswordEncoder bCryptPasswordEncoder;
	UsersRepository usersRepository;
	
	 public UsersServiceImpl() {
	}
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository,BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.usersRepository=usersRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	

	public UserDto createUser(UserDto userDetails)
	{
		
		String userId=UUID.randomUUID().toString();
		userDetails.setUserId(userId);
		
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		UserEntity userEntity=modelMapper.map(userDetails, UserEntity.class);
		
		usersRepository.save(userEntity);
		
		UserDto returnValue=modelMapper.map(userEntity, UserDto.class);
		return returnValue;
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity= usersRepository.findByEmail(username);

		if(userEntity==null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());  
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
	
		UserEntity userEntity=usersRepository.findByEmail(email);
		
		return new ModelMapper().map(userEntity, UserDto.class);
	}
}
