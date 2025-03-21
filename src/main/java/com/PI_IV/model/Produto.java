package com.PI_IV.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Nome do produto, obrigatório
    @Column(nullable = false, length = 200)
    private String nome;

    // Avaliação do produto, obrigatório
    @Column(nullable = true)
    private Float avaliacao;

    // Descrição do produto, obrigatório
    @Column(nullable = false, length = 255)
    private String descricao;

    // Preço do produto, obrigatório
    @Column(nullable = false)
    private Double preco;

    // Quantidade em estoque, obrigatório
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    // A imagem principal é armazenada como BLOB (longblob)
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagemPadrao;

    // Imagem adicional do produto, armazenada também como BLOB (longblob)
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagemBlob;

    // O produto está ativo ou não. Default é 1 (ativo)
    @Column(nullable = false)
    private boolean ativo = true;
}
