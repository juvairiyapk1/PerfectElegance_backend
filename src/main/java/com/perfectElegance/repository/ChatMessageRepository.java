package com.perfectElegance.repository;

import com.perfectElegance.modal.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Integer> {

    List<ChatMessage> findByChatId(Integer integer);
}
