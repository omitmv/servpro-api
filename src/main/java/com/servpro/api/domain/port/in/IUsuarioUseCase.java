package com.servpro.api.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.servpro.api.domain.model.Usuario;

public interface IUsuarioUseCase {
  Usuario salvarUsuario(Usuario usuario);
  Optional<Usuario> buscarPorUsername(String username);
  List<Usuario> listarUsuarios();
  Usuario atualizarUsuario(String username, Usuario usuario);
  void deletarUsuario(String username);
}
