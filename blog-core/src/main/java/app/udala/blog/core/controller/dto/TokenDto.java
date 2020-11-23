package app.udala.blog.core.controller.dto;

public class TokenDto {
	private final String token;
	private final String type;

	public TokenDto(String token, String type) {
		this.token = token;
		this.type = type;
	}

	public final String getToken() {
		return token;
	}

	public final String getType() {
		return type;
	}

}
