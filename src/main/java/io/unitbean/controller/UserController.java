package io.unitbean.controller;

import io.unitbean.dto.*;
import io.unitbean.model.Post;
import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.service.ImageService;
import io.unitbean.service.PostService;
import io.unitbean.service.UserService;
import io.unitbean.service.impl.FriendshipServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final FriendshipServiceImpl friendshipService;
    private final UserService userService;
    private final PostService postService;
    private final ImageService imageService;

    @PostMapping("/add-friend")
    public ResponseEntity<?> subscribeOnUser(Principal principal, @RequestParam("id") Integer userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        friendshipService.subscribeOnUser(userDetails.getId(), userId);
        log.debug("Received request for {} adding friend {}", userDetails.getUsername(), userId);
        return ResponseEntity.ok(Map.of("message", "Friend added", "userId", userId));
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<Set<FriendDto>> getUserFriends(@PathVariable("id") Integer userId) {
        log.debug("Received request getting friends for {} ", userId);
        Set<User> friends = friendshipService.getUserFriends(userId);
        Set<FriendDto> result = friends.stream()
                .map(u -> new FriendDto(u.getUsername()))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserProfileById(@PathVariable("id") Integer userId, Principal principal) {
        log.debug("Received request getting profile for {} ", userId);
        User user = userService.getUserById(userId);
        UserDetailsImpl currentUser = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        String friendshipStatus = friendshipService.getFriendshipStatus(currentUser.getId(), user.getId());
        List<PostResponseDto> userPosts = postService.getUserPosts(userId)
                .stream()
                .map(p -> new PostResponseDto(p.getContent()))
                .collect(Collectors.toList());
        boolean isCurrentUser = currentUser.getId().equals(user.getId());
        String imageName = userService.getUserImageName(userId);
        imageService.getUserImage(imageName);
        String imageUrl = (imageName != null && !imageName.isEmpty()) ? "/images/" + imageName : "/images/defaultimage.png";

        UserProfileDto body = new UserProfileDto(
                new UserSummaryDto(user.getUsername()),
                friendshipStatus,
                userPosts,
                isCurrentUser,
                imageUrl
        );
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDto>> findUsersByUsername(@RequestParam(required = false) String username) {
        List<User> users = (username != null && !username.isEmpty())
                ? userService.getUsersByUsername(username)
                : userService.getAllUsers();
        List<UserSummaryDto> result = users.stream()
                .map(u -> new UserSummaryDto(u.getUsername()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createUserPost(Principal principal,
                                            @Valid @RequestBody(required = false) PostDto postDto,
                                            @RequestParam(value = "content", required = false) String content,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Validation failed"));
        }
        UserDetailsImpl currentUser = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        log.debug("Received request creating post for {} ", currentUser.getId());
        try {
            String bodyContent = (postDto != null && postDto.getContent() != null) ? postDto.getContent() : content;
            if (bodyContent == null || bodyContent.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "content is required"));
            }
            Post created = postService.createPost(currentUser.getId(), bodyContent);
            return ResponseEntity.ok(new PostResponseDto(created.getContent()));
        } catch (Exception e) {
            log.error("Error creating post: ", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to create post"));
        }
    }
}
