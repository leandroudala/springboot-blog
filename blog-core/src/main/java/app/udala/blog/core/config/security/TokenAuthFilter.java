package app.udala.blog.core.config.security;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import app.udala.blog.core.model.User;
import app.udala.blog.core.repository.UserRepository;
import app.udala.blog.core.service.TokenService;

public class TokenAuthFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository userRepository;

	public TokenAuthFilter(TokenService tokenService, UserRepository userRepository) {
		super();
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = retrieveToken(request);
		boolean isValid = tokenService.isValidToken(token);
		if (isValid) {
			authUser(token);
		}

		filterChain.doFilter(request, response);
	}

	private void authUser(String token) {
		String publicId = tokenService.getPublicId(token);
		Optional<User> checkUser = userRepository.findByPublicId(UUID.fromString(publicId));
		if (checkUser.isPresent()) {
			User user = checkUser.get();
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

	}

	private String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		return tokenService.retrieveToken(token);
	}

}
