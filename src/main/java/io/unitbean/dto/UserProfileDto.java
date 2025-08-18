package io.unitbean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private UserSummaryDto user;
    private String friendshipStatus;
    private List<PostResponseDto> userPosts;
    private boolean isCurrentUser;
    private String imageUrl;
}
 
