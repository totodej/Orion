package com.openclassrooms.mddapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.mddapi.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);
	Optional<User> findByEmailOrName(String email, String name);
	boolean existsByEmail(String email);
}
