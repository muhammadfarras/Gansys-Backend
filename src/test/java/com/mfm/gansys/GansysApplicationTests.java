package com.mfm.gansys;

import com.mfm.gansys.model.Role;
import com.mfm.gansys.model.Users;
import com.mfm.gansys.repositories.RoleRepository;
import com.mfm.gansys.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GansysApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;


	@Test
	void contextLoads() {
	}

	@Test
	void insertUsers(){
		Users users = new Users();
		users.setFirst_name("Muhammad")
						.setRole(null)
				.setLast_name("Farras")
				.setEmail("maruffarras@gmail.com")
						.setIsEnable(1L)
								.setPassword("Farras");

		userRepository.save(users);
	}

	@Test
	void checkEmailExist(){
		System.out.println(userRepository.existsByEmail("maruffarras@gmail.com"));

		System.out.println(roleRepository.existsById(1L));
	}

}
