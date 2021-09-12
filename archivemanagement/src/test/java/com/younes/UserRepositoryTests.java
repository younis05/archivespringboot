package com.younes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import com.younes.entity.User;

import com.younes.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestEntityManager testEntityManager;

	
	@Test
	public void getTestUser() {
		User user=new User();
		user.setEmail("user@gmail.com");
		user.setPassword("1234");
		user.setFirstName("user0");
		user.setLastName("user");
		User saveUser=userRepository.save(user);
		User userexist=testEntityManager.find(User.class, user.getId());
		
		assertThat(userexist.getEmail()).isEqualTo(user.getEmail());
		
	}
//	@Test
//	public void findUserByEmail() {
//		String email="test@gmail.com";
//		User user=userRepository.getUserByEmail(email);
//		assertThat(user.getEmail()).isNotNull();
//	}


	@Test
	public void SaveUser() {
		User user=new User();
		user.setEmail("user1@gmail.com");
		user.setPassword("1234");
		user.setFirstName("user1");
		user.setLastName("user");
		
//		user.setEmail("younes@gmail.com");
//		user.setPassword("1234");
//		user.setFirstName("younes");
//		user.setLastName("boukhtache");
		

		User saveUser=userRepository.save(user);
		

	}
	@Test
	public void testAddRolesToExistingUser() {
		User user=userRepository.findById(1L).get();
	
        User saveUser=userRepository.save(user);
		

	}
	
	
}
