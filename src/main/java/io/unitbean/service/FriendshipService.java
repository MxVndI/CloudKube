package io.unitbean.service;

import io.unitbean.model.User;

import java.util.Set;

public interface FriendshipService {

    void subscribeOnUser(Integer firstUserId, Integer secondUserId);

    Set<User> getUserFriends(Integer userId);

    String getFriendshipStatus(Integer firstUserId, Integer secondUserId);

    Set<User> getUserSubscribes(Integer userId);

    Set<User> getUserSubscribers(Integer userId);
}
