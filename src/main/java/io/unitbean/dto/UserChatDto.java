package io.unitbean.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChatDto {

    private String username;

    public UserChatDto(String username) {
        this.username = username;
    }
}
