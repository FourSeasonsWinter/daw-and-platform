package com.example.server_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.server_api.dtos.PlaylistDto;
import com.example.server_api.dtos.PlaylistPostRequest;
import com.example.server_api.dtos.PlaylistPutRequest;
import com.example.server_api.entities.Playlist;
import com.example.server_api.mappers.PlaylistMapper;
import com.example.server_api.repositories.PlaylistRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/playlists")
public class PlaylistController {

  private PlaylistRepository repository;
  private PlaylistMapper mapper;

  @GetMapping
  public ResponseEntity<List<PlaylistDto>> getPlaylists(
    @RequestParam(defaultValue = "10") int limit,
    @RequestParam(defaultValue = "0") int skip
  ) {
    List<Playlist> playlists = repository.findAll();
    List<Playlist> filtered = playlists.subList(skip, skip + limit);

    return ResponseEntity.ok(filtered.stream().map(mapper::toDto).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable Long id) {
    Playlist playlist = repository.findById(id).orElse(null);

    if (playlist == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(mapper.toDto(playlist));
  }

  @PostMapping
  public ResponseEntity<PlaylistDto> addPlaylist(
    @RequestBody PlaylistPostRequest request,
    UriComponentsBuilder uriBuilder
  ) {
    var playlist = mapper.toEntity(request);
    repository.save(playlist);

    var uri = uriBuilder.path("/playlists/{id}").buildAndExpand(playlist.getId()).toUri();
    var dto = mapper.toDto(playlist);
    dto.setId(playlist.getId());
    return ResponseEntity.created(uri).body(dto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PlaylistDto> updatePlaylist(
    @PathVariable Long id,
    @RequestBody PlaylistPutRequest request
  ) {
    var playlist = repository.findById(id).orElse(null);
    if (playlist == null) {
      return ResponseEntity.notFound().build();
    }
    
    mapper.update(request, playlist);
    repository.save(playlist);

    return ResponseEntity.ok(mapper.toDto(playlist));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
    var playlist = repository.findById(id).orElse(null);
    if (playlist == null) {
      return ResponseEntity.notFound().build();
    }

    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
