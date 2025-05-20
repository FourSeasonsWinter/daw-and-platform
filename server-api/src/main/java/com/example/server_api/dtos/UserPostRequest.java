package com.example.server_api.dtos;

import lombok.Data;

@Data
public class UserPostRequest {
  private String username;
  private String email;
  private String password;
}
