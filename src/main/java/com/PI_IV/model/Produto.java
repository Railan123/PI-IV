package com.PI_IV.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produtos")
@Getter
@Setter
public class Produto {

    // Chave primária auto-incrementável
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Nome do produto
    @Column(nullable = false, length = 200)
    private String nome;

    // Avaliação do produto (de 1 a 5)
    @Column(nullable = false, precision = 2)
    private Double avaliacao;

    // Descrição do produto
    @Column(nullable = false, length = 255)
    private String descricao;

    // Preço do produto
    @Column(nullable = false, precision = 10)
    private Double preco;

    // Quantidade disponível no estoque
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    // Nome do arquivo da imagem
    @Column(columnDefinition = "LONGTEXT")
    private String imagemPadrao;

    // Armazena a imagem como BLOB
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagemBlob;

    // Indica se o produto está ativo
    @Column(nullable = false)
    private boolean ativo = true;
}
