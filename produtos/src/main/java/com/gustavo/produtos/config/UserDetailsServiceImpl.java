package com.gustavo.produtos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.gustavo.produtos.model.Usuario;
import com.gustavo.produtos.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
        .username(usuario.getUsername())
        .password(usuario.getPassword())
        .roles(usuario.getRole())
        .build();

    }
}
