package app.udala.blog.core.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.udala.blog.core.controller.dto.UserDto;
import app.udala.blog.core.controller.form.UserCreationForm;
import app.udala.blog.core.model.User;
import app.udala.blog.core.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository repository;

	@PostMapping
	@Transactional
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreationForm form, UriComponentsBuilder uriBuilder) {
		User user = form.toNewUser();
		Optional<User> checkUser = repository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
		
		if (checkUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		repository.save(user);

		URI uri = uriBuilder.path("/users/{publicId}").buildAndExpand(user.getPublicId().toString()).toUri();
		return ResponseEntity.created(uri).body(new UserDto(user));
	}

	@GetMapping("/{publicId}")
	public ResponseEntity<UserDto> getUser(@PathVariable String publicId) {
		Optional<User> checkUser = repository.findByPublicId(UUID.fromString(publicId));

		if (checkUser.isPresent()) {
			return ResponseEntity.ok(new UserDto(checkUser.get()));
		}

		return ResponseEntity.notFound().build();
	}
}
