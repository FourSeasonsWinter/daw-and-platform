package com.example.server_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server_api.entities.Track;
import com.example.server_api.repositories.TrackRepository;

@RestController
@RequestMapping("/tracks")
public class TrackController {

  private TrackRepository repository;

  public TrackController(TrackRepository repository) {
    this.repository = repository;
  }
  
  @GetMapping
  public ResponseEntity<List<Track>> getTracks(
    @RequestParam(defaultValue = "10") int limit,
    @RequestParam(defaultValue = "0") int skip
  ) {
    List<Track> tracks = repository.findAll();
    List<Track> filteredTracks = tracks.subList(skip, skip + limit);

    return ResponseEntity.ok(filteredTracks);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
    Track track = repository.findById(id).orElse(null);

    if (track == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(track);
  }

  @PostMapping
  public ResponseEntity<Track> addTrack(@RequestBody Track track) {
    Track savedTrack = repository.save(track);
    return new ResponseEntity<>(savedTrack, HttpStatus.CREATED); // TODO Use factory method
  }
  
  @PatchMapping
  public ResponseEntity<Track> updateTrack(@RequestBody Track track) {
    Track savedTrack = repository.findById(track.getId()).orElse(null);

    if (savedTrack == null) {
      ResponseEntity.notFound().build();
    }

    savedTrack.setAuthor(track.getAuthor());
    savedTrack.setCoverUrl(track.getCoverUrl());
    savedTrack.setCreatedAt(track.getCreatedAt());
    savedTrack.setDescription(track.getDescription());
    savedTrack.setFileUrl(track.getFileUrl());
    savedTrack.setGenre(track.getGenre());
    savedTrack.setTitle(track.getTitle());
    savedTrack.setWaveformData(track.getWaveformData());

    return ResponseEntity.ok(savedTrack);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
