package com.example.server_api.services;

import com.example.server_api.dtos.ChangePasswordRequest;
import com.example.server_api.dtos.UserDto;
import com.example.server_api.dtos.UserPostRequest;
import com.example.server_api.dtos.UserPutRequest;

public interface UserService {
  UserDto getUser(Long id);
  UserDto createUser(UserPostRequest request);
  UserDto updateUser(Long id, UserPutRequest request);
  void deleteUser(Long id);
  void changePassword(Long id, ChangePasswordRequest request);
}
