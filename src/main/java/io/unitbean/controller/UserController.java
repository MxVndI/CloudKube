package io.unitbean.controller;

import io.unitbean.dto.PostDto;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final FriendshipServiceImpl friendshipService;
    private final UserService userService;
    private final PostService postService;
    private final ImageService imageService;

    @PostMapping("/add-friend")
    public String subscribeOnUser(Principal principal, @RequestParam("id") Integer userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        friendshipService.subscribeOnUser(userDetails.getId(), userId);
        log.debug("Received request for {} adding friend {}", userDetails.getUsername(), userId);
        return "redirect:/users/" + userId;
    }

    @GetMapping("/{id}/friends")
    public String getUserFriends(@PathVariable("id") Integer userId, Model model) {
        log.debug("Received request getting friends for {} ", userId);
        Set<User> friends = friendshipService.getUserFriends(userId);
        model.addAttribute("friends", friends);
        return "user/user-friends";
    }

    @GetMapping("/{id}")
    public String getUserProfileById(@PathVariable("id") Integer userId, Model model, Principal principal) {
        log.debug("Received request getting profile for {} ", userId);
        User user = userService.getUserById(userId);
        UserDetailsImpl currentUser = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        String friendshipStatus = friendshipService.getFriendshipStatus(currentUser.getId(), user.getId());
        List<Post> userPosts = postService.getUserPosts(userId);
        boolean isCurrentUser = currentUser.getId().equals(user.getId());
        String imageName = userService.getUserImageName(userId);
        imageService.getUserImage(imageName);
        String imageUrl = null;
        if (imageName != null && !imageName.isEmpty()) {
            imageUrl = "/images/" + imageName;
        }
        System.out.println(imageUrl + " AAAAAAAAAAAAAAAAAAAA");
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("friendshipStatus", friendshipStatus);
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("isCurrentUser", isCurrentUser);
        model.addAttribute("imageUrl", imageUrl);
        return "user/profile";
    }

    @GetMapping
    public String findUsersByUsername(@RequestParam(required = false) String username, Model model) {
        if (username != null && !username.isEmpty()) {
            log.debug("Received request getting users");
            model.addAttribute("users", userService.getUsersByUsername(username));
        } else {
            log.debug("Received request getting users with filter");
            model.addAttribute("users", userService.getAllUsers());
        }
        return "user/all-users";
    }

    @PostMapping("/posts")
    public String createUserPost(Principal principal, @Valid PostDto postDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/create-post";
        }

        UserDetailsImpl currentUser = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        log.debug("Received request creating post for {} ", currentUser.getId());

        try {
            postService.createPost(currentUser.getId(), postDto.getContent());
        } catch (Exception e) {
            log.error("Error creating post: ", e);
            bindingResult.reject("postCreationError", "Не удалось создать пост. Попробуйте еще раз.");
            return "user/create-post";
        }

        return "redirect:/users/" + currentUser.getId();
    }

    @GetMapping("/create-post")
    public String showCreatePostForm(Model model, Principal principal) {
        UserDetailsImpl currentUser = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("currentUser", currentUser);
        return "user/create-post";
    }

}
