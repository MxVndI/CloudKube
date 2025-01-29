package io.unitbean.service.friendship_status;

import io.unitbean.model.enums.FriendshipStatus;
import io.unitbean.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class YouAreSubscribedHandler implements FriendshipStatusHandler {
    private FriendshipStatusHandler next;
    @Autowired
    private FriendshipRepository friendshipRepository;

    public YouAreSubscribedHandler(FriendshipStatusHandler next) {
        this.next = next;
    }

    @Override
    public String checkFriendship(Integer firstUserId, Integer secondUserId) {
        if (friendshipRepository.findById(firstUserId).isPresent() &&
                friendshipRepository.findById(secondUserId).isEmpty()) {
            return FriendshipStatus.YOU_ARE_SUBSCRIBED.toString();
        }
        return next.checkFriendship(firstUserId, secondUserId);
    }
}
