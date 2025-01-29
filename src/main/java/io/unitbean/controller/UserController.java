package io.unitbean.controller;

import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.service.impl.FriendshipServiceImpl;
import io.unitbean.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final FriendshipServiceImpl friendshipService;
    private final UserServiceImpl userService;

    @PostMapping("/add-friend")
    public String subscribeOnUser(Authentication authentication, @RequestParam("id") Integer userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        friendshipService.subscribeOnUser(userDetails.getId(), userId);
        return "redirect:/users/" + userId;
    }

    @GetMapping("/{id}/friends")
    public String getUserFriends(@PathVariable("id") Integer userId, Model model) {
        Set<User> friends = friendshipService.getUserFriends(userId);
        model.addAttribute("friends", friends);
        return "user/user-friends";
    }

    @GetMapping("/{id}")
    public String getUserProfileById(@PathVariable("id") Integer userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping
    public String findUsersByUsername(@RequestParam(required = false) String username, Model model) {
        if (username != null && !username.isEmpty()) {
            model.addAttribute("users", userService.findUsersByUsername(username));
        } else {
            model.addAttribute("users", userService.getAllUsers());
        }
        return "user/all-users";
    }
}
