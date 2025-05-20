package com.example.server_api.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.server_api.ResourceNotFoundException;
import com.example.server_api.UnauthorizedException;
import com.example.server_api.dtos.ChangePasswordRequest;
import com.example.server_api.dtos.UserDto;
import com.example.server_api.dtos.UserPostRequest;
import com.example.server_api.dtos.UserPutRequest;
import com.example.server_api.entities.User;
import com.example.server_api.mappers.UserMapper;
import com.example.server_api.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository repository;
  private UserMapper mapper;

  @Override
  public UserDto getUser(Long id) {
    User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    return mapper.toDto(user);
  }

  @Override
  public UserDto createUser(UserPostRequest request) {
    User user = mapper.toEntity(request);
    var encoder = new BCryptPasswordEncoder();
    var hashedPassword = encoder.encode(user.getPassword());

    user.setPassword(hashedPassword);
    repository.save(user);

    return mapper.toDto(user);
  }

  @Override
  public UserDto updateUser(Long id, UserPutRequest request) {
    User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    mapper.update(request, user);
    repository.save(user);

    return mapper.toDto(user);
  }

  @Override
  public void deleteUser(Long id) {
    User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    repository.delete(user);
  }

  @Override
  public void changePassword(Long id, ChangePasswordRequest request) {
    User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    var encoder = new BCryptPasswordEncoder();

    if (encoder.encode(request.getOldPassword()) != user.getPassword()) {
      throw new UnauthorizedException("Old password is wrong");
    }

    user.setPassword(encoder.encode(request.getNewPassword()));
    repository.save(user);
  }
}
