package com.example.server_api.dtos;

import java.time.LocalDateTime;

import com.example.server_api.entities.User;

import lombok.Data;

@Data
public class TrackDto {
  private Long id;
  private String title;
  private String genre;
  private String description;
  private String fileUrl;
  private String coverUrl;
  private String waveformData;
  private LocalDateTime createdAt;
  private User author;
}
