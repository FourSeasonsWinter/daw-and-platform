package com.example.server_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.server_api.dtos.TrackDto;
import com.example.server_api.dtos.TrackPostRequest;
import com.example.server_api.dtos.TrackPutRequest;
import com.example.server_api.entities.Track;

@Mapper(componentModel = "spring")
public interface TrackMapper {
  TrackDto toDto(Track track);
  Track toEntity(TrackPostRequest trackRequest);
  void update(TrackPutRequest request, @MappingTarget Track track);
}
