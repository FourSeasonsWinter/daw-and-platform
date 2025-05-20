package com.example.server_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.server_api.dtos.PlaylistDto;
import com.example.server_api.dtos.PlaylistPostRequest;
import com.example.server_api.dtos.PlaylistPutRequest;
import com.example.server_api.entities.Playlist;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
  PlaylistDto toDto(Playlist request);
  Playlist toEntity(PlaylistPostRequest request);
  void update(PlaylistPutRequest request, @MappingTarget Playlist playlist);
}
