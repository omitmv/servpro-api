package com.servpro.api.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
  private Long id;
  private String username;
  private String password;
  private ArrayList<String> roles;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean enabled;
}
