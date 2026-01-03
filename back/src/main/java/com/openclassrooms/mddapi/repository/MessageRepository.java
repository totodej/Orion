package com.openclassrooms.mddapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
	List<Message> findByPostIdOrderByCreatedAtAsc(Integer postId);
}
