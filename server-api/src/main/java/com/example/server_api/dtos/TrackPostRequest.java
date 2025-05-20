package com.example.server_api.dtos;

import lombok.Data;

@Data
public class TrackPostRequest {
  private String title;
  private String genre;
  private String description;

  private String fileUrl;
  private String coverUrl;
  private String waveformData;

  private Long userId;
}
