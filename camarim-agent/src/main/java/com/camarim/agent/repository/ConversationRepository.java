package com.camarim.agent.repository;

import com.camarim.agent.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findBySessionIdOrderByTimestampAsc(String sessionId);

    Long countBySessionId(String sessionId);

    void deleteBySessionId(String sessionId);
}

