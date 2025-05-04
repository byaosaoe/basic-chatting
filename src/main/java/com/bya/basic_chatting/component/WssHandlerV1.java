package com.bya.basic_chatting.component;

// low한 level의 handler를 작성하고 싶으면 이런식으로 작성하면 된다는 예시이기 때문에 실제로 사용하지는 않을 것

import com.bya.basic_chatting.domain.chat.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WssHandlerV1 extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) {

        try {
            String payload = msg.getPayload();
            Message message = objectMapper.readValue(payload, Message.class);

            // 1. DB에 있는 데이터 인지 [from, to]
            // 2. 채팅 메시지 데이터 저장

            session.sendMessage(new TextMessage(payload));
        } catch(Exception e) {

        }
    }
}
