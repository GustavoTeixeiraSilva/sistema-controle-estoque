package com.gustavo.produtos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listarProdutos(
            @RequestParam(required = false) String q,
            @RequestParam(required = false, defaultValue = "false") boolean baixo,
            Model model
    ) {

        List<Produto> produtos;

        if (q != null && !q.trim().isEmpty()) {
            produtos = repository.findByNomeContainingIgnoreCase(q.trim());
        } else {
            produtos = repository.findAll();
        }

        // filtro estoque baixo (<= 5)
        if (baixo) {
            produtos = produtos.stream()
                    .filter(p -> p.getQuantidade() <= 5)
                    .toList();
        }

        model.addAttribute("produtos", produtos);
        model.addAttribute("q", q);
        model.addAttribute("baixo", baixo);

        return "index";
    }

    @GetMapping("/novo")
    public String novoProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "form";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto) {
        repository.save(produto);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = repository.findById(id).orElseThrow();
        model.addAttribute("produto", produto);
        return "form";
    }

    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
