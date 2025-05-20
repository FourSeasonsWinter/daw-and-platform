package com.example.server_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.server_api.dtos.UserDto;
import com.example.server_api.dtos.UserPostRequest;
import com.example.server_api.dtos.UserPutRequest;
import com.example.server_api.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  UserDto toDto(User user);

  User toEntity(UserPostRequest request);
  void update(UserPutRequest request, @MappingTarget User user);
}
