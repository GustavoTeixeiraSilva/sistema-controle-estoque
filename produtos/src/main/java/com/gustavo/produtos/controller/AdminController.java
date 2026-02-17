package com.gustavo.produtos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gustavo.produtos.model.Usuario;
import com.gustavo.produtos.repository.UsuarioRepository;

@Controller
public class AdminController {

    private final UsuarioRepository usuarioRepository;

    public AdminController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // LISTA USUÁRIOS
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // PROMOVER PARA ADMIN
    @PostMapping("/usuarios/{id}/admin")
    public String tornarAdmin(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setRole("ADMIN");
        usuarioRepository.save(u);
        return "redirect:/usuarios";
    }

    // REBAIXAR PARA USER
    @PostMapping("/usuarios/{id}/user")
    public String tornarUser(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setRole("USER");
        usuarioRepository.save(u);
        return "redirect:/usuarios";
    }
}
