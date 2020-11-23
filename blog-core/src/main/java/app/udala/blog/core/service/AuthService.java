package app.udala.blog.core.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.udala.blog.core.model.User;
import app.udala.blog.core.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> checkUser = repository.findByEmail(username);
		if (checkUser.isPresent()) {
			return checkUser.get();
		}
		
		throw new UsernameNotFoundException("Username or password invalid.");
	}

}
