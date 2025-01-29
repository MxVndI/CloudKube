package io.unitbean.service;

import io.unitbean.exception.UserNotFoundException;
import io.unitbean.model.Friendship;
import io.unitbean.model.FriendshipId;
import io.unitbean.model.User;
import io.unitbean.repository.FriendshipRepository;
import io.unitbean.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public void subscribeOnUser(Integer firstUserId, Integer secondUserId) {
        if (firstUserId.equals(secondUserId))
            throw new RuntimeException("Нельзя добавить в друзья самого себя");
        User firstUser = userRepository.findById(firstUserId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + firstUserId + " не найден"));
        User secondUser = userRepository.findById(secondUserId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + secondUserId + " не найден"));
        Friendship friendship = new Friendship();
        FriendshipId friendshipId = new FriendshipId(firstUserId, secondUserId);
        friendship.setFriendshipId(friendshipId);
        friendship.setFirstUser(firstUser);
        friendship.setSecondUser(secondUser);
        friendshipRepository.save(friendship);
    }

    public Set<User> getUserFriends(Integer userId) {
        Set<User> userSubscribes = friendshipRepository.findAllByFirstUserId(userId).stream()
                .map(user -> user.getSecondUser()).collect(Collectors.toSet());
        Set<User> userSubscribers = friendshipRepository.findAllBySecondUserId(userId).stream()
                .map(user -> user.getFirstUser()).collect(Collectors.toSet());
        return userSubscribes.stream().filter(user -> userSubscribers.contains(user)).collect(Collectors.toSet());
    }
}
