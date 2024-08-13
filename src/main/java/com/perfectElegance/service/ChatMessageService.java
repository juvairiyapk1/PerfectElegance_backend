package com.perfectElegance.service;

import com.perfectElegance.modal.ChatMessage;
import com.perfectElegance.repository.ChatMessageRepository;
import com.perfectElegance.utils.ReadReceipt;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatMessage save(ChatMessage chatMessage){
        int chatId = chatRoomService.getChatRoomId(chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true).orElseThrow();

        chatMessage.setChatId(chatId);
        repository.save(chatMessage);

        return chatMessage;
    }

//    public List<ChatMessage>findChatMessages(Integer senderId,Integer recipientId){
//        var chatId = chatRoomService.getChatRoomId(senderId,
//                recipientId,
//                false);
//
//        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
//    }
public List<ChatMessage> findChatMessages(Integer senderId, Integer recipientId) {
    var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
    if (chatId.isPresent()) {
        List<ChatMessage> messages = repository.findByChatId(chatId.get());

        // Mark all unread messages as read for the recipient
        messages.stream()
                .filter(message -> message.getRecipientId().equals(recipientId) && !message.isRead())
                .forEach(message -> {
                    message.setRead(true);
                    repository.save(message);  // Save the updated message
                    notifySender(message);      // Notify the sender that the message was read
                });

        return messages;
    }
    return new ArrayList<>();
}


    public void deleteMessage(Integer id) {
        ChatMessage message = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + id));
        repository.delete(message);
    }


    public void markMessageAsRead(Integer id) {
        System.out.println("hihih");
        ChatMessage message = repository.findById(id)
                .orElseThrow();
        System.out.println(message.isRead());
        if (!message.isRead()) {
            message.setRead(true);
            repository.save(message);
            System.out.println(message.isRead());
            notifySender(message);
        }
    }

    private void notifySender(ChatMessage message) {
        messagingTemplate.convertAndSendToUser(
                message.getSenderId().toString(),
                "/queue/message-read",
                new ReadReceipt(message.getId(), message.getRecipientId())
        );
    }

}
