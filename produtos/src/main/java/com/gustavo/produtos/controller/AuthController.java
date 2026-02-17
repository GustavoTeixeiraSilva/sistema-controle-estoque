package com.gustavo.produtos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gustavo.produtos.model.Usuario;
import com.gustavo.produtos.repository.UsuarioRepository;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Tela de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Tela de cadastro
    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    // Salvar usuário
    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {

        // Criptografa a senha
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        usuario.setRole("USER");

        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

    // 🔐 Página de acesso negado
    @GetMapping("/403")
    public String acessoNegado() {
        return "403";
    }
}
