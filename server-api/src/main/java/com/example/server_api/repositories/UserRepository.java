package com.example.server_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server_api.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
}
