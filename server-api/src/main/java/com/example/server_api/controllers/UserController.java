package com.example.server_api.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
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
import com.example.server_api.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
  
  private UserService service;

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
    UserDto user = service.getUser(id);
    return ResponseEntity.ok(user);
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(
    @RequestBody UserPostRequest userRequest,
    UriComponentsBuilder uriBuilder
  ) {
    UserDto user = service.createUser(userRequest);
    URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

    return ResponseEntity.created(uri).body(user);
  }

  @PostMapping("/{id}/change-password")
  public ResponseEntity<Void> changePassword(
    @PathVariable Long id,
    @RequestBody ChangePasswordRequest request
  ) {
    service.changePassword(id, request);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(
    @PathVariable Long id,
    @RequestBody UserPutRequest request
  ) {
    UserDto user = service.updateUser(id, request);
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    service.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
