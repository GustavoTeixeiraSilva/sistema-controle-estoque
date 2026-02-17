package com.gustavo.produtos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.produtos.model.Movimentacao;
import com.gustavo.produtos.Produto; // IMPORT CORRETO

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByProdutoOrderByDataHoraDesc(Produto produto);

    List<Movimentacao> findAllByOrderByDataHoraDesc();
}
