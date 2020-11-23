package app.udala.blog.core.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import app.udala.blog.core.model.User;

public class LoginForm {
	private String username;
	private String password;
	private String email;

	public LoginForm(User newUser) {
		username = newUser.getUsername();
		email = newUser.getEmail();
		password = newUser.getPassword();
	}
	public LoginForm() {}

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

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(this.email, this.password);
	}

}
