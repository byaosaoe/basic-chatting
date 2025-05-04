package com.bya.basic_chatting.domain.chat.controller;

import com.bya.basic_chatting.domain.chat.model.Message;
import com.bya.basic_chatting.domain.chat.service.ChatServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class WssControllerV1 {

    private final ChatServiceV1 chatServiceV1;

    // 여기를 좀 수정해서 roomId를 활용하고 싶음
    @MessageMapping("/chat/message/{from}")
    @SendTo("/sub/chat")
    public Message receivedMessage(
        @DestinationVariable("from") String roomId,
        Message msg
    ) {
        log.info("Message Received -> From: {}, to: {}, msg: {}", msg.getFrom(), msg.getTo(), msg.getMessage());

        // TODO -> Save
        chatServiceV1.saveChatMessage(msg);
        return msg;
    }
}
