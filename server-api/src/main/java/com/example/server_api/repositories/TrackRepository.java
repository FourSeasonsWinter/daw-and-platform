package com.example.server_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.server_api.entities.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
  
  @EntityGraph(attributePaths = "genre")
  @Query("SELECT t FROM Track t")
  List<Track> findAllWithGenre();
  
  List<Track> findAllWithTitle(String title);
}
