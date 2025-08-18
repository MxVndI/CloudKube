package io.unitbean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendDto {
    private Integer id;
    private String username;
    private String imageUrl;
}
