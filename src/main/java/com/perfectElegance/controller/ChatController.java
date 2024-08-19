package com.perfectElegance.controller;

import com.perfectElegance.modal.ChatMessage;
import com.perfectElegance.service.ChatMessageService;
import com.perfectElegance.utils.ChatNotification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j

public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage){


        ChatMessage savedMsg = chatMessageService.save(chatMessage);

        log.info("Message saved: " + savedMsg);

        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getRecipientId()),
                "/queue/messages",
                ChatNotification
                        .builder()
                        .id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .read(savedMsg.isRead())
                        .build());


    }



    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable("senderId")Integer senderId,
            @PathVariable("recipientId")Integer recipientId
    ){
      return ResponseEntity.ok(chatMessageService.findChatMessages(senderId,recipientId));
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity<Void> deleteSelectedMessage(@PathVariable Integer id) {
            chatMessageService.deleteMessage(id);
            simpMessagingTemplate.convertAndSend("/queue/messages/delete", id);
            return ResponseEntity.noContent().build();

    }

    @PostMapping("/{messageId}/read")
    public ResponseEntity<?> markMessageAsRead(@PathVariable("messageId") Integer id) {
        chatMessageService.markMessageAsRead(id);
        return ResponseEntity.ok().build();
    }


}
