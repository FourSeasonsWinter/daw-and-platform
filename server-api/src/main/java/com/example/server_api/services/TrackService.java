package com.example.server_api.services;

import java.util.List;

import com.example.server_api.dtos.TrackDto;
import com.example.server_api.dtos.TrackPostRequest;
import com.example.server_api.dtos.TrackPutRequest;

public interface TrackService {
  TrackDto getTrackById(Long id);
  List<TrackDto> getTracks(int skip, int limit);
  TrackDto createTrack(TrackPostRequest request);
  TrackDto updateTrack(Long id, TrackPutRequest request);
  void deleteTrack(Long id);
}
