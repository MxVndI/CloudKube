package io.unitbean.controller;

import io.unitbean.dto.MessageDto;
import io.unitbean.model.User;
import io.unitbean.model.security.UserDetailsImpl;
import io.unitbean.repository.MessageHistory;
import io.unitbean.service.UserService;
import io.unitbean.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final MessageHistory messageRepository;
    private final UserService userService;

    @GetMapping("/chat")
    public List<MessageDto> showChatPage() {
        return messageRepository.findTop50ByOrderByTimestampAsc().stream()
                .map(m -> new MessageDto(m.getContent(), m.getTimestamp(), m.getUser().getUsername()))
                .toList();
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload Message chatMessage, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        Integer userId = userDetails.getId();
        User user = userService.getUserById(userId);
        chatMessage.setUser(user);
        chatMessage.setName(userDetails.getUsername());
        messageRepository.save(chatMessage);
        return new MessageDto(chatMessage.getContent(), chatMessage.getTimestamp(), user.getUsername());
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(principal.getName());
        Integer userId = userDetails.getId();
        Message chatMessage = new Message();
        User user = userService.getUserById(userId);
        chatMessage.setUser(user);
        chatMessage.setName(userDetails.getUsername());
        chatMessage.setContent(principal.getName() + " присоединился к чату!");
        return chatMessage;
    }
}