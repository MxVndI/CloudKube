package io.unitbean.service;

import io.unitbean.model.User;

import java.util.Set;

public interface FriendshipService {
    public void subscribeOnUser(Integer firstUserId, Integer secondUserId);

    public Set<User> getUserFriends(Integer userId);
}
