package com.bya.basic_chatting.domain.repository;

import com.bya.basic_chatting.domain.repository.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findTop10BySenderOrReceiverOrderByTIDDesc(String sender, String receiver);

    @Query("SELECT c FROM Chat AS c WHERE (c.sender = :sender AND c.receiver = :receiver) OR (c.sender = :receiver AND c.receiver = :sender) ORDER BY c.TID DESC LIMIT 10")
    List<Chat> findTop10Chats(@Param("sender") String sender, @Param("receiver") String receiver);
}
