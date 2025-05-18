package com.example.server_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server_api.entities.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
  
}
