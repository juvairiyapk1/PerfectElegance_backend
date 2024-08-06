package com.perfectElegance.service;

import com.perfectElegance.modal.ChatMessage;
import com.perfectElegance.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;

    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage){
        int chatId = chatRoomService.getChatRoomId(chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true).orElseThrow();

        chatMessage.setChatId(chatId);
//        chatMessage.setTimeStamp(new Date());
        repository.save(chatMessage);

        return chatMessage;
    }

    public List<ChatMessage>findChatMessages(Integer senderId,Integer recipientId){
        var chatId = chatRoomService.getChatRoomId(senderId,
                recipientId,
                false);

        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }


}
