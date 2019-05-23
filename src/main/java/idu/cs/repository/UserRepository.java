package idu.cs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import idu.cs.domain.User;

import java.util.List;

public interface UserRepository 
	extends JpaRepository<User, Long> {
	List<User> findByName(String name);

}
