package com.example.server_api.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tracks")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String email;
  private String password;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Track> tracks;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private List<Playlist> playlists;
}
