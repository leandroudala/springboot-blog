package app.udala.blog.core.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.udala.blog.core.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByPublicId(UUID publicId);

	@Query("select u from User u where u.username = :username or u.email = :email")
	Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
}
