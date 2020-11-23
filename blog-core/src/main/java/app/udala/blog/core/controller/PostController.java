package app.udala.blog.core.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.udala.blog.core.controller.dto.PostDto;
import app.udala.blog.core.controller.form.PostCreationForm;
import app.udala.blog.core.model.Post;
import app.udala.blog.core.model.User;
import app.udala.blog.core.repository.PostRepository;
import app.udala.blog.core.repository.UserRepository;
import app.udala.blog.core.service.TokenService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public Page<PostDto> getAll(@PageableDefault() Pageable pagination) {
		Page<Post> posts = postRepository.findAll(pagination);

		return Post.convertAll(posts);
	}

	@PostMapping
	public ResponseEntity<PostDto> create(@RequestBody @Valid PostCreationForm form, UriComponentsBuilder uriBuilder,
			@RequestHeader(name = "Authorization") String token) {
		Post post = form.toNewPost();

		String publicId = tokenService.getPublicId(token);

		Optional<User> checkUser = userRepository.findByPublicId(UUID.fromString(publicId));

		if (!checkUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		User user = checkUser.get();
		post.setAuthor(user);

		postRepository.save(post);

		URI uri = uriBuilder.path("/posts/{publicId}").buildAndExpand(post.getPublicId().toString()).toUri();
		return ResponseEntity.created(uri).body(new PostDto(post));
	}

	@GetMapping("/{publicId}")
	public ResponseEntity<PostDto> get(@PathVariable String publicId) {
		Optional<Post> checkPost = postRepository.findByPublicId(UUID.fromString(publicId));

		if (!checkPost.isPresent())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(new PostDto(checkPost.get()));
	}
}
