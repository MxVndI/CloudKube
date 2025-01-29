package io.unitbean.service.friendship_status;

import io.unitbean.model.enums.FriendshipStatus;
import io.unitbean.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NoFriendshipHandler implements FriendshipStatusHandler {
    private FriendshipStatusHandler next;
    @Autowired
    private FriendshipRepository friendshipRepository;

    public NoFriendshipHandler(FriendshipStatusHandler next) {
        this.next = next;
    }

    @Override
    public String checkFriendship(Integer firstUserId, Integer secondUserId) {
        if (friendshipRepository.findById(firstUserId).isEmpty() &&
                friendshipRepository.findById(secondUserId).isEmpty()) {
            return FriendshipStatus.ADD_TO_FRIENDS.toString();
        }
        return next.checkFriendship(firstUserId, secondUserId);
    }
}
