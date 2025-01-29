package io.unitbean.repository;

import io.unitbean.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, Long> {
    List<Post> findByUserId(String userId);
}
