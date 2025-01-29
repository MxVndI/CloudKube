package io.unitbean.service.friendship_status;

import io.unitbean.model.FriendshipId;
import io.unitbean.model.enums.FriendshipStatus;
import io.unitbean.repository.FriendshipRepositoryEmbeded;

public class YouAreSubscribedHandler implements FriendshipStatusHandler {
    private final FriendshipStatusHandler next;
    private final FriendshipRepositoryEmbeded friendshipRepositoryEmbeded;

    public YouAreSubscribedHandler(FriendshipStatusHandler next, FriendshipRepositoryEmbeded friendshipRepositoryEmbeded) {
        this.next = next;
        this.friendshipRepositoryEmbeded = friendshipRepositoryEmbeded;
    }

    @Override
    public String checkFriendship(Integer firstUserId, Integer secondUserId) {
        FriendshipId friendshipIdFirst = new FriendshipId(firstUserId, secondUserId);
        FriendshipId friendshipIdSecond = new FriendshipId(secondUserId, firstUserId);
        if (friendshipRepositoryEmbeded.findById(friendshipIdFirst).isPresent() &&
                friendshipRepositoryEmbeded.findById(friendshipIdSecond).isEmpty()) {
            return FriendshipStatus.YOU_ARE_SUBSCRIBED.toString();
        }
        return next.checkFriendship(firstUserId, secondUserId);
    }
}
