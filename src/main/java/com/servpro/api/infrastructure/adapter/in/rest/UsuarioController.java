package com.servpro.api.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servpro.api.application.service.UsuarioService;
import com.servpro.api.domain.model.Usuario;
import com.servpro.api.infrastructure.adapter.in.rest.dto.UsuarioRequest;
import com.servpro.api.infrastructure.adapter.in.rest.swagger.UsuarioSwagger;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController implements UsuarioSwagger {

  private final UsuarioService usuarioService;

  @Override
  @PostMapping
  public ResponseEntity<Void> createUsuario(@Valid @RequestBody UsuarioRequest request) {
    log.info("Creating usuario with data: {}", request);
    usuarioService.salvarUsuario(Usuario.builder()
        .username(request.getLogin())
        .email(request.getEmail())
        .password(request.getSenha())
        .build());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  @PutMapping("/{login}")
  public ResponseEntity<Void> updateUsuario(
    @Parameter(description = "ID do produto", required = true)
    @PathVariable String login,
    @Valid @RequestBody UsuarioRequest request) {
    log.info("Updating usuario with login: {} and data: {}", login, request);
    return usuarioService.buscarPorUsername(login)
      .map(existingUsuario -> {
        existingUsuario.setUsername(request.getLogin());
        existingUsuario.setEmail(request.getEmail());
        existingUsuario.setPassword(request.getSenha());
        usuarioService.atualizarUsuario(login, existingUsuario);
        return ResponseEntity.ok().<Void>build();
      })
      .orElse(ResponseEntity.notFound().build());
  }
}
