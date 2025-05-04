package com.bya.basic_chatting.domain.chat.service;

import com.bya.basic_chatting.domain.chat.model.Message;
import com.bya.basic_chatting.domain.chat.model.response.ChatListResponse;
import com.bya.basic_chatting.domain.repository.ChatRepository;
import com.bya.basic_chatting.domain.repository.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatServiceV1 {

    private final ChatRepository chatRepository;

    public ChatListResponse chatList(String from, String to) {
        List<Chat> chats = chatRepository.findTop10Chats(from, to);

        // Entity -> DTO
        List<Message> res = chats.stream()
                .map(chat -> new Message(chat.getReceiver(), chat.getSender(), chat.getMessage()))
                .collect(Collectors.toList());

        Collections.reverse(res);

        return new ChatListResponse(res);
    }

    @Transactional(transactionManager = "createChatTransactionManager")
    public void saveChatMessage(Message msg) {
        Chat chat = Chat.builder()
                .sender(msg.getFrom())
                .receiver(msg.getTo())
                .message(msg.getMessage())
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();

        chatRepository.save(chat);
    }
}
