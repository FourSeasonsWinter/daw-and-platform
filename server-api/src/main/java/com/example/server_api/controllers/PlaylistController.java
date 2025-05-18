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

import com.example.server_api.entities.Playlist;
import com.example.server_api.repositories.PlaylistRepository;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

  private PlaylistRepository repository;

  public PlaylistController(PlaylistRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<List<Playlist>> getPlaylists(
    @RequestParam(defaultValue = "10") int limit,
    @RequestParam(defaultValue = "0") int skip
  ) {
    List<Playlist> playlists = repository.findAll();
    List<Playlist> filtered = playlists.subList(skip, skip + limit);

    return ResponseEntity.ok(filtered);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
    Playlist playlist = repository.findById(id).orElse(null);

    if (playlist == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(playlist);
  }

  @PostMapping
  public ResponseEntity<Playlist> addPlaylist(@RequestBody Playlist playlist) {
    Playlist savedPlaylist = repository.save(playlist);
    return new ResponseEntity<>(savedPlaylist, HttpStatus.CREATED); // TODO Use factory method
  }

  @PatchMapping
  public ResponseEntity<Playlist> updatePlaylist(@RequestBody Playlist playlist) {
    Playlist savedPlaylist = repository.findById(playlist.getId()).orElse(null);

    if (savedPlaylist == null) {
      return ResponseEntity.notFound().build();
    }

    savedPlaylist.setCreatedAt(playlist.getCreatedAt());
    savedPlaylist.setName(playlist.getName());
    savedPlaylist.setOwner(playlist.getOwner());
    savedPlaylist.setTracks(playlist.getTracks());

    return ResponseEntity.ok(savedPlaylist);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
