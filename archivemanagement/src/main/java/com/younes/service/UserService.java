package com.younes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.younes.entity.User;
import com.younes.repository.UserRepository;
import com.younes.service.impl.UserServiceImpl;

@Service
public class UserService implements UserServiceImpl{

	@Autowired
	private UserRepository userRepository; 
	
	
	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public User saveUser(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public User findUserById(Long id) {
		
		return userRepository.findById(id).get();
	}

	@Override
	public User updateUser(User user, Long id) {

		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public User getUserByEmail(String email) {
		
		return userRepository.getUserByEmail(email);
	}



}
