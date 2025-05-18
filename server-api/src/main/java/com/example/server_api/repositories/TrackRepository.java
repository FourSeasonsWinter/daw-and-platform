package com.example.server_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server_api.entities.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
  
}
