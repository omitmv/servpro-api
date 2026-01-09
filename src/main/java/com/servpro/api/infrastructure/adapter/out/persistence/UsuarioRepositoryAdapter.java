package com.servpro.api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.servpro.api.domain.model.Usuario;
import com.servpro.api.domain.port.out.IUsuarioRepository;
import com.servpro.api.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.servpro.api.infrastructure.adapter.out.persistence.mapper.UsuarioPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioRepositoryAdapter implements IUsuarioRepository {
  
  private final UserJpaRepository jpaRepository;
  private final UsuarioPersistenceMapper mapper;

  @Override
  public Usuario save(Usuario usuario) {
    log.info("Saving usuario: {}", usuario);
    UserEntity entity = mapper.toEntity(usuario);
    UserEntity savedEntity = jpaRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Usuario> findByUsername(String username) {
    log.info("Finding usuario by username: {}", username);
    return jpaRepository.findByUsername(username)
      .map(mapper::toDomain);
  }

  @Override
  public List<Usuario> findAll() {
    log.info("Finding all usuarios");
    return jpaRepository.findAll().stream()
      .map(mapper::toDomain)
      .toList();
  }

  @Override
  public void deleteByUsername(String username) {
    log.info("Deleting usuario by username: {}", username);
    jpaRepository.deleteByUsername(username);
  }

  @Override
  public Usuario update(String username, Usuario usuario) {
    log.info("Updating usuario by username: {}", username);
    UserEntity oldEntity = jpaRepository.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    UserEntity newEntity = mapper.toEntity(usuario);
    UserEntity updatedEntity = jpaRepository.save(newEntity);
    return mapper.toDomain(updatedEntity);
  }

  @Override
  public boolean existsByUsername(String username) {
    log.info("Checking existence of usuario by username: {}", username);
    return jpaRepository.existsByUsername(username);
  }
}
