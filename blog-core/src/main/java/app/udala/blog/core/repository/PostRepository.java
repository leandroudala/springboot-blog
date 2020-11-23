package app.udala.blog.core.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.udala.blog.core.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findByPublicId(UUID uuid);

}
