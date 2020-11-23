package app.udala.blog.core.controller.form;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.udala.blog.core.model.User;

public class UserCreationForm {
	private String email;
	private String username;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User toNewUser() {
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		return user;
	}

}
