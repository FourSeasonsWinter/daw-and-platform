package com.example.server_api.dtos;

import lombok.Data;

@Data
public class PlaylistPostRequest {
  private String name;
  private Long userId;
}
