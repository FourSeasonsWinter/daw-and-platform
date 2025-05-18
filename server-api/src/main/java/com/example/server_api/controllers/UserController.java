package com.example.server_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server_api.dtos.UserDTO;
import com.example.server_api.entities.User;
import com.example.server_api.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
  
  private UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable Long id) {
    User user = repository.findById(id).orElse(null);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(user);
  }

  @PostMapping
  public ResponseEntity<User> addUser(@RequestBody User user) {
    User savedUser = repository.save(user);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // TODO Use factory method
  }

  @PatchMapping
  public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
    User savedUser = repository.findById(user.getId()).orElse(null);

    if (savedUser == null) {
      return ResponseEntity.notFound().build();
    }

    var encoder = new BCryptPasswordEncoder();
    var hashedPassword = encoder.encode(user.getPassword());

    savedUser.setEmail(user.getEmail());
    savedUser.setPassword(hashedPassword);
    savedUser.setPlaylists(user.getPlaylists());
    savedUser.setTracks(user.getTracks());
    savedUser.setUsername(user.getUsername());

    var userDto = new UserDTO(
      savedUser.getId(),
      savedUser.getUsername(),
      savedUser.getEmail(),
      savedUser.getTracks(),
      savedUser.getPlaylists()
    );

    return ResponseEntity.ok(userDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
