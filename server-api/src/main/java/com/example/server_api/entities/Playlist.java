package com.example.server_api.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "playlists")
public class Playlist {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @ManyToMany
  @JoinTable(
    name = "playlist_tracks",
    joinColumns = @JoinColumn(name = "playlist_id"),
    inverseJoinColumns = @JoinColumn(name = "track_id")
  )
  private List<Track> tracks;

  private LocalDateTime createdAt;
}
