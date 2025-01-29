package io.unitbean.service.friendship_status;

import io.unitbean.model.enums.FriendshipStatus;

public class FriendsHandler implements FriendshipStatusHandler {

    @Override
    public String checkFriendship(Integer firstUserId, Integer secondUserId) {
        return FriendshipStatus.FRIENDS.toString();
    }

}