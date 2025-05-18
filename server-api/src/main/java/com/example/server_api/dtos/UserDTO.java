package com.example.server_api.dtos;

import java.util.List;

import com.example.server_api.entities.Playlist;
import com.example.server_api.entities.Track;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
  private Long id;
  private String username;
  private String email;
  private List<Track> tracks;
  private List<Playlist> playlists;
}
