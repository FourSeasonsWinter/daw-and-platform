package com.example.server_api.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.example.server_api.entities.Playlist;
import com.example.server_api.entities.Track;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDto {
  private Long id;
  private String username;
  private String email;
  private List<Track> tracks;
  private List<Playlist> playlists;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
}
