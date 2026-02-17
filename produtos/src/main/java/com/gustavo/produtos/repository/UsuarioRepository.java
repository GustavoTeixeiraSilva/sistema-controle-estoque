package com.gustavo.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gustavo.produtos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

}
