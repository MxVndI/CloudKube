package io.unitbean.repository;

import io.unitbean.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageHistory extends JpaRepository<Message, Integer> {
    List<Message> findTop50ByOrderByTimestampAsc();
}
