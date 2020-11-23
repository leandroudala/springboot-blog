package app.udala.blog.core.controller.form;

import app.udala.blog.core.model.Post;
import app.udala.blog.core.model.User;

public class PostCreationForm {
	private String title;
	private User author;
	private String text;

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final User getAuthor() {
		return author;
	}

	public final void setAuthor(User author) {
		this.author = author;
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public Post toNewPost() {
		Post post = new Post();
		post.setTitle(this.title);
		post.setAuthor(this.author);
		post.setText(this.text);
		return post;
	}

}
