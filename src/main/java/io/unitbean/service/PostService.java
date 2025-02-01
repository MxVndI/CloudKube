package io.unitbean.service;

import io.unitbean.model.Post;
import io.unitbean.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(Integer userId, String content) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        return postRepository.save(post);
    }

    public List<Post> getUserPosts(Integer userId) {
        return postRepository.findByUserId(userId);
    }
}
