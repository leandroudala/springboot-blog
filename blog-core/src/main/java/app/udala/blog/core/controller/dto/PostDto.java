package app.udala.blog.core.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import app.udala.blog.core.model.Post;

public class PostDto {
	private UUID publicId;
	private String title;
	private UserDto author;
	private String text;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public PostDto(Post post) {
		this.publicId = post.getPublicId();
		this.title = post.getTitle();
		this.author = new UserDto(post.getAuthor());
		this.text = post.getText();
		this.createdAt = post.getCreatedAt();
		this.updatedAt = post.getUpdatedAt();
	}

	public final UUID getPublicId() {
		return publicId;
	}

	public final void setPublicId(UUID publicId) {
		this.publicId = publicId;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final UserDto getAuthor() {
		return author;
	}

	public final void setAuthor(UserDto author) {
		this.author = author;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public final void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public final LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public final void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
