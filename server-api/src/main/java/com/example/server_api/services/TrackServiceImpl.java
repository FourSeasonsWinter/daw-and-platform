package com.example.server_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.server_api.ResourceNotFoundException;
import com.example.server_api.dtos.TrackDto;
import com.example.server_api.dtos.TrackPostRequest;
import com.example.server_api.dtos.TrackPutRequest;
import com.example.server_api.entities.Track;
import com.example.server_api.mappers.TrackMapper;
import com.example.server_api.repositories.TrackRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TrackServiceImpl implements TrackService {

  private TrackRepository repository;
  private TrackMapper mapper;

  @Override
  public TrackDto getTrackById(Long id) {
    Track track = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Track not found with id: " + id));
    return mapper.toDto(track);
  }

  @Override
  public List<TrackDto> getTracks(int skip, int limit) {
    List<Track> tracks = repository.findAll().subList(skip, limit + skip);
    return tracks.stream().map(mapper::toDto).toList();
  }

  @Override
  public TrackDto createTrack(TrackPostRequest request) {
    Track track = mapper.toEntity(request);
    repository.save(track);
    return mapper.toDto(track);
  }

  @Override
  public TrackDto updateTrack(Long id, TrackPutRequest request) {
    Track track = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Track not found with id " + id));

    mapper.update(request, track);
    repository.save(track);

    return mapper.toDto(track);
  }

  @Override
  public void deleteTrack(Long id) {
    Track track = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Track not found with id " + id));
    repository.delete(track);
  }
}
