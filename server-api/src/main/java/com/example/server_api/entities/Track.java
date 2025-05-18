package com.example.server_api.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@Table(name = "tracks")
public class Track {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String genre;
  private String description;

  private String fileUrl;
  private String coverUrl;
  private String waveformData;

  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;
}
