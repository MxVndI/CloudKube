package io.unitbean.controller;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.service.impl.FriendshipServiceImpl;
import io.unitbean.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final FriendshipServiceImpl friendshipService;
    private final UserServiceImpl userService;

    @PostMapping("/add-friend")
    public String subscribeOnUser(Authentication authentication, @RequestParam("id") Integer userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
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
    public String getUserProfileById(@PathVariable("id") Integer userId, Model model) {
        log.debug("Received request getting profile for {} ", userId);
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
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
}
