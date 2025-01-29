package io.unitbean.service.friendship_status;

import io.unitbean.model.enums.FriendshipStatus;
import io.unitbean.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscribedToYouHandler implements FriendshipStatusHandler{
    private FriendshipStatusHandler next;
    @Autowired
    private FriendshipRepository friendshipRepository;

    public SubscribedToYouHandler(FriendshipStatusHandler next) {
        this.next = next;
    }

    @Override
    public String checkFriendship(Integer firstUserId, Integer secondUserId) {
        if (friendshipRepository.findById(firstUserId).isEmpty() &&
                friendshipRepository.findById(secondUserId).isPresent()) {
            return FriendshipStatus.SUBSCRIBED_TO_YOU.toString();
        }
        return next.checkFriendship(firstUserId, secondUserId);
    }
}
