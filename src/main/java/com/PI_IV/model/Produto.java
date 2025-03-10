package com.PI_IV.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", length = 200, nullable = true)
    private String nome;

    @Column(name = "avaliacao", nullable = true)
    @DecimalMin(value = "1.5", message = "A avaliação mínima permitida é 1.5")
    @DecimalMax(value = "5.0", message = "A avaliação máxima permitida é 5.0")
    private BigDecimal avaliacao;

    @Column(name = "descricao", nullable = true)
    private String descricao;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    @Column(name = "imagem_padrao", length = 255, nullable = true)
    private String imagemPadrao;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;
}
