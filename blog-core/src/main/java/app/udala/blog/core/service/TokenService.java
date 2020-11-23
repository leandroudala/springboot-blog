package app.udala.blog.core.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import app.udala.blog.core.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	@Value("${blog.jwt.expiration}")
	private String expiration;

	@Value("${blog.jwt.secret")
	private String secretKey;

	Logger logger = LoggerFactory.getLogger(TokenService.class);

	public String generateToken(Authentication auth) {
		User user = (User) auth.getPrincipal();
		Date today = new Date();

		Date expiresAt = new Date(today.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setIssuer("Udala App's Blog API").setSubject(user.getPublicId().toString())
				.setIssuedAt(today).setExpiration(expiresAt).signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public String retrieveToken(String token) {
		if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
			return null;

		return token.substring(7, token.length());
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getPublicId(String header) {
		String token = retrieveToken(header);
		if (!isValidToken(token)) {
			logger.error("Received invalid token");
			return null;
		}

		Claims body = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();

		return body.getSubject();
	}
}
