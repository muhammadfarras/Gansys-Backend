package com.mfm.gansys;

import com.mfm.gansys.model.Role;
import com.mfm.gansys.model.Users;
import com.mfm.gansys.repositories.RoleRepository;
import com.mfm.gansys.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
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

	@Test
	void checkSentEmail (){
		Mailer mailer = MailerBuilder.withSMTPServer(
				"mail.vensys.co.id",
				587, "muhammad.farras@vensys.co.id", "F4r12a5-2712"
		).buildMailer();

		Email email = EmailBuilder.startingBlank().from("muhammad.farras@vensys.co.id").to("maruffarras@gmail.com")
				.withSubject("Register NardiBot Venturium System Indonesia").withHTMLText("<b>This is the register file").buildEmail();

		mailer.testConnection();

		// validate the email
		mailer.validate(email);

		// set the email
		mailer.sendMail(email);
	}

}
