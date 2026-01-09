package com.servpro.api.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servpro.api.domain.model.Usuario;
import com.servpro.api.domain.port.in.IUsuarioUseCase;
import com.servpro.api.domain.port.out.IUsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsuarioService implements IUsuarioUseCase {
  
  private final IUsuarioRepository usuarioRepository;

  @Override
  public Usuario salvarUsuario(Usuario usuario) {
    log.info("Saving user: {}", usuario);
    return usuarioRepository.save(usuario);
  }

  @Override
  public Optional<Usuario> buscarPorUsername(String username) {
    log.info("Finding user by username: {}", username);
    return usuarioRepository.findByUsername(username);
  }

  @Override
  public List<Usuario> listarUsuarios() {
    log.info("Listing all users");
    return usuarioRepository.findAll();
  }

  @Override
  public Usuario atualizarUsuario(String username, Usuario usuario) {
    log.info("Updating user with username: {}", username);
    return usuarioRepository.findByUsername(username)
            .map(existingUsuario -> {
                existingUsuario.setUsername(usuario.getUsername());
                existingUsuario.setEmail(usuario.getEmail());
                existingUsuario.setPassword(usuario.getPassword());
                return usuarioRepository.save(existingUsuario);
            })
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
  }

  @Override
  public void deletarUsuario(String username) {
    log.info("Deleting user with username: {}", username);
    if (!usuarioRepository.existsByUsername(username)) {
        log.error("User not found with username: {}", username);
        throw new RuntimeException("User not found with username: " + username);
    }
    usuarioRepository.deleteByUsername(username);
  }
}
