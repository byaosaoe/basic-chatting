package com.bya.basic_chatting.domain.chat.model.response;

import com.bya.basic_chatting.domain.chat.model.Message;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Chatting List")
public record ChatListResponse(
    @Schema(description = "return Message : []")
    List<Message> result
) {
}
