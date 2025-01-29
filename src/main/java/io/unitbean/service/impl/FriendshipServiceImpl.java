package io.unitbean.service.impl;

import io.unitbean.exception.UserAddToFriendsHimselfException;
import io.unitbean.model.Friendship;
import io.unitbean.model.FriendshipId;
import io.unitbean.model.User;
import io.unitbean.repository.FriendshipRepository;
import io.unitbean.service.FriendshipService;
import io.unitbean.service.UserService;
import io.unitbean.service.friendship_status.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final UserService userService;
    private final FriendshipRepository friendshipRepository;

    public void subscribeOnUser(Integer firstUserId, Integer secondUserId) {
        if (firstUserId.equals(secondUserId)) {
            throw new UserAddToFriendsHimselfException("Нельзя добавить в друзья самого себя");
        }
        User firstUser = userService.getUserById(firstUserId);
        User secondUser = userService.getUserById(secondUserId);
        Friendship friendship = new Friendship();
        FriendshipId friendshipId = new FriendshipId(firstUserId, secondUserId);
        friendship.setFriendshipId(friendshipId);
        friendship.setFirstUser(firstUser);
        friendship.setSecondUser(secondUser);
        friendshipRepository.save(friendship);
    }

    public Set<User> getUserFriends(Integer userId) {
        return getUserSubscribes(userId).stream()
                .filter(getUserSubscribers(userId)::contains)
                .collect(Collectors.toSet());
    }

    public String getFriendshipStatus(Integer firstUserId, Integer secondUserId) {
        FriendshipStatusHandler handler = new NoFriendshipHandler(
                new YouAreSubscribedHandler(
                        new SubscribedToYouHandler(
                                new FriendsHandler()
                        )
                )
        );
        return handler.checkFriendship(firstUserId, secondUserId);
    }

    @Override
    public Set<User> getUserSubscribes(Integer userId) {
        return friendshipRepository.findAllByFirstUserId(userId).stream()
                .map(Friendship::getSecondUser).collect(Collectors.toSet());
    }

    @Override
    public Set<User> getUserSubscribers(Integer userId) {
        return friendshipRepository.findAllBySecondUserId(userId).stream()
                .map(Friendship::getFirstUser).collect(Collectors.toSet());
    }
}
