package com.openclassrooms.mddapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	List<Post> findByTopicIdIn(List<Integer> topicIds, Sort sort);
	List<Post> findByTopicId(Integer topicId);
	Optional<Post> findById(Integer id);
}
