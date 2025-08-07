package io.unitbean.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friendships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    @EmbeddedId
    private FriendshipId friendshipId;

    @ManyToOne
    @MapsId("firstUserId")
    @JoinColumn(name = "first_user_id")
    private User firstUser;

    @ManyToOne
    @MapsId("secondUserId")
    @JoinColumn(name = "second_user_id")
    private User secondUser;
}
