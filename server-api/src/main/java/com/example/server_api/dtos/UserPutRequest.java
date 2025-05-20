package com.example.server_api.dtos;

import lombok.Data;

@Data
public class UserPutRequest {
  private String username;
  private String email;
}
