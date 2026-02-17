package com.gustavo.produtos.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gustavo.produtos.Produto; // Produto está em com.gustavo.produtos
import com.gustavo.produtos.model.Movimentacao;
import com.gustavo.produtos.repository.MovimentacaoRepository;
import com.gustavo.produtos.ProdutoRepository; // ProdutoRepository está em com.gustavo.produtos

@Controller
public class MovimentacaoController {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoController(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    // Tela para movimentar (entrada/saida)
    @GetMapping("/movimentar/{id}")
    public String telaMovimentar(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "ENTRADA") String tipo,
            Model model
    ) {
        Produto produto = produtoRepository.findById(id).orElseThrow();

        model.addAttribute("produto", produto);
        model.addAttribute("tipo", tipo.toUpperCase());

        return "movimentar";
    }

    // Salvar movimentação
    @PostMapping("/movimentar/{id}")
    public String salvarMovimentacao(
            @PathVariable Long id,
            @RequestParam String tipo,
            @RequestParam int quantidade,
            @RequestParam(required = false) String observacao,
            Principal principal,
            Model model
    ) {
        Produto produto = produtoRepository.findById(id).orElseThrow();

        if (quantidade <= 0) {
            model.addAttribute("produto", produto);
            model.addAttribute("tipo", tipo.toUpperCase());
            model.addAttribute("erro", "Quantidade deve ser maior que 0.");
            return "movimentar";
        }

        int atual = produto.getQuantidade();

        if ("SAIDA".equalsIgnoreCase(tipo)) {
            if (atual - quantidade < 0) {
                model.addAttribute("produto", produto);
                model.addAttribute("tipo", "SAIDA");
                model.addAttribute("erro", "Saída maior que o estoque atual. Estoque: " + atual);
                return "movimentar";
            }
            produto.setQuantidade(atual - quantidade);
            tipo = "SAIDA";
        } else {
            // ENTRADA
            produto.setQuantidade(atual + quantidade);
            tipo = "ENTRADA";
        }

        produtoRepository.save(produto);

        Movimentacao mov = new Movimentacao();
        mov.setProduto(produto);
        mov.setTipo(tipo);
        mov.setQuantidade(quantidade);
        mov.setDataHora(LocalDateTime.now());
        mov.setUsuario(principal.getName());
        mov.setObservacao(observacao);

        movimentacaoRepository.save(mov);

        return "redirect:/";
    }

    // Histórico geral
    @GetMapping("/movimentos")
    public String historico(Model model) {
        List<Movimentacao> movs = movimentacaoRepository.findAllByOrderByDataHoraDesc();
        model.addAttribute("movimentos", movs);
        return "movimentos";
    }
}
