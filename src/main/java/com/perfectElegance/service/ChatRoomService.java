package com.perfectElegance.service;

import com.perfectElegance.modal.ChatRoom;
import com.perfectElegance.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<Integer> getChatRoomId(Integer senderId,
                                           Integer recipientId,
                                           boolean createNewRoomIfNotExist){
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId)
                .map(ChatRoom::getChatId)
                .or(()->{
                    if (createNewRoomIfNotExist){
                        int chatId = createChatId(senderId,recipientId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });

    }

    private Integer createChatId(Integer senderId, Integer recipientId) {

        int chatId = senderId*1000+recipientId;

        ChatRoom senderRecipient = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return chatId;
    }
}
