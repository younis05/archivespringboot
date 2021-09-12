package com.younes.service.impl;

import java.util.List;

import com.younes.entity.User;

public interface UserServiceImpl {
	
	public List<User> findAllUsers();
	public User getUserByEmail(String email);
	public User saveUser(User user);
	public User findUserById(Long id);
	public User updateUser(User user,Long id);
	public void deleteUser(Long id);

	
}
