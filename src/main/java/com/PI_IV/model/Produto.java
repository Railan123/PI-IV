package com.PI_IV.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

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
    @Column(nullable = false)
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

    // A imagem principal do produto é armazenada como BLOB
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagemPadrao;

    // O produto está ativo ou não (valor padrão = true)
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean ativo = true;

    // Relacionamento com a tabela de imagens adicionais do produto
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagemProduto> imagens;
}
