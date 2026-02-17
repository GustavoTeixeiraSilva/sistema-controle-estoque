package com.gustavo.produtos.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.gustavo.produtos.Produto; // IMPORT CORRETO DO SEU PRODUTO

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ENTRADA ou SAIDA
    private String tipo;

    private int quantidade;

    private LocalDateTime dataHora;

    // Quem fez a movimentação (username do login)
    private String usuario;

    // Observação opcional
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Movimentacao() {
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
