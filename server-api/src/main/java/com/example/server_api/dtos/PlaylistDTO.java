package com.example.server_api.dtos;

import java.util.List;

import com.example.server_api.entities.Track;

import lombok.Data;

@Data
public class PlaylistDto {
  private Long id;
  private String name;
  private Long userId;
  private List<Track> trackIds;
}
