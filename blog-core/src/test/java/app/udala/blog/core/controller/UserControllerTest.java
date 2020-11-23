package app.udala.blog.core.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yaml.snakeyaml.util.UriEncoder;

import app.udala.blog.core.controller.dto.TokenDto;
import app.udala.blog.core.controller.dto.UserDto;
import app.udala.blog.core.controller.form.LoginForm;
import app.udala.blog.core.model.User;
import app.udala.blog.core.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerTest {
	@LocalServerPort
	private int port;

	private String baseUri;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;

	private String usersUri;
	private String authUri;

	private User newUser;

	Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

	@BeforeEach
	public void init() {
		// uris
		baseUri = "http://localhost:" + port;
		usersUri = UriEncoder.encode(baseUri + "/users");
		authUri = UriEncoder.encode(baseUri + "/auth");

		newUser = new User();
		newUser.setEmail("test@example.com");
		newUser.setPassword("654@123");
		newUser.setUsername("tester");
	}

	ResponseEntity<UserDto> createUser() {
		return restTemplate.postForEntity(usersUri, newUser, UserDto.class);
	}

	@Test
	void testCreateNewUser() {
		ResponseEntity<UserDto> response = createUser();
		UserDto body = response.getBody();

		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(body.getPublicId(), IsNull.notNullValue());
	}

	@Test
	void testGetUser() {
		Optional<User> checkUser = userRepository.findByUsernameOrEmail(newUser.getUsername(), newUser.getEmail());
		
		assertThat(checkUser.isPresent(), equalTo(true));
		assertThat(checkUser.get(), IsNull.notNullValue(User.class));
		assertThat(checkUser.get().getPublicId(), IsNull.notNullValue());
	}
	
	@Test
	void testLoginAuthentication() {
		LoginForm form = new LoginForm(newUser);
		
		logger.info("Email: {}", newUser.getEmail());
		logger.info("Username: {}", newUser.getUsername());
		logger.info("Password: {}", newUser.getPassword());

		ResponseEntity<TokenDto> response = restTemplate.postForEntity(authUri, form, TokenDto.class);
		TokenDto body = response.getBody();

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(body.getType(), equalTo("Bearer"));
		assertThat(body.getToken(), IsNull.notNullValue());
	}

}
