package com.example.server_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.server_api.dtos.TrackDto;
import com.example.server_api.dtos.TrackPostRequest;
import com.example.server_api.dtos.TrackPutRequest;
import com.example.server_api.entities.Track;
import com.example.server_api.mappers.TrackMapper;
import com.example.server_api.repositories.TrackRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tracks")
public class TrackController {

  private final TrackRepository repository;
  private final TrackMapper mapper;
  
  @GetMapping
  public ResponseEntity<List<TrackDto>> getTracks(
    @RequestParam(defaultValue = "10") int limit,
    @RequestParam(defaultValue = "0") int skip
  ) {
    List<Track> tracks = repository.findAll().subList(skip, skip + limit);
    List<TrackDto> dtos = tracks.stream().map(mapper::toDto).toList();

    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrackDto> getTrackById(@PathVariable Long id) {
    Track track = repository.findById(id).orElse(null);

    if (track == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(mapper.toDto(track));
  }

  @PostMapping
  public ResponseEntity<TrackDto> createTrack(
    @RequestBody TrackPostRequest trackRequest,
    UriComponentsBuilder uriBuilder
  ) {
    var track = mapper.toEntity(trackRequest);
    repository.save(track);

    var trackDto = mapper.toDto(track);
    var uri = uriBuilder.path("/tracks/{id}").buildAndExpand(trackDto.getId()).toUri();

    return ResponseEntity.created(uri).body(trackDto);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<TrackDto> updateTrack(
    @PathVariable Long id,
    @RequestBody TrackPutRequest request
  ) {
    var track = repository.findById(id).orElse(null);
    if (track == null) {
      ResponseEntity.notFound().build();
    }

    mapper.update(request, track);
    repository.save(track);

    return ResponseEntity.ok(mapper.toDto(track));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
    var track = repository.findById(id).orElse(null);
    if (track == null) {
      ResponseEntity.notFound().build();
    }

    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
