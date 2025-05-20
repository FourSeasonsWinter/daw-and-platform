package com.example.server_api.dtos;

import java.util.List;

import lombok.Data;

@Data
public class PlaylistPutRequest {
  private String name;
  private List<Long> trackId;
}
