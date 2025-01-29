package io.unitbean.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipId {
    private Integer firstUserId;
    private Integer secondUserId;
}
