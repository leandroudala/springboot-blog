package app.udala.blog.core.controller.dto;

import java.time.LocalDateTime;

import app.udala.blog.core.model.User;

public class UserDto {
	private String publicId;
	private String email;
	private String username;
	private LocalDateTime createdAt;

	public final String getPublicId() {
		return publicId;
	}

	public final String getEmail() {
		return email;
	}

	public final String getUsername() {
		return username;
	}

	public final LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserDto() {
	}
	public UserDto(User user) {
		this.publicId = user.getPublicId().toString();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.createdAt = user.getCreatedAt();
	}
	
	public User toUser() {
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		return user;
	}
	public String toString() {
		return String.format("UserDto{publicId: %s, username: %s, email: %s}", publicId.toString(), username, email);
	}
}
