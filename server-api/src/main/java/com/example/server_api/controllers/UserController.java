package com.example.server_api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.server_api.dtos.ChangePasswordRequest;
import com.example.server_api.dtos.UserDto;
import com.example.server_api.dtos.UserPostRequest;
import com.example.server_api.dtos.UserPutRequest;
import com.example.server_api.entities.User;
import com.example.server_api.mappers.UserMapper;
import com.example.server_api.repositories.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
  
  private UserRepository repository;
  private UserMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
    User user = repository.findById(id).orElse(null);

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(mapper.toDto(user));
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(
    @RequestBody UserPostRequest userRequest,
    UriComponentsBuilder uriBuilder
  ) {
    var user = mapper.toEntity(userRequest);
    repository.save(user);

    var userDto = mapper.toDto(user);
    var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
    
    return ResponseEntity.created(uri).body(userDto);
  }

  @PostMapping("/{id}/change-password")
  public ResponseEntity<Void> changePassword(
    @PathVariable Long id,
    @RequestBody ChangePasswordRequest request
  ) {
    var user = repository.findById(id).orElse(null);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    var encoder = new BCryptPasswordEncoder();
    if (encoder.encode(request.getOldPassword()) != user.getPassword()) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    user.setPassword(encoder.encode(request.getNewPassword()));
    repository.save(user);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(
    @PathVariable Long id,
    @RequestBody UserPutRequest request
  ) {
    User user = repository.findById(id).orElse(null);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    mapper.update(request, user);
    repository.save(user);

    return ResponseEntity.ok(mapper.toDto(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    User user = repository.findById(id).orElse(null);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
