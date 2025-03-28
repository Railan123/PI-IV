package com.PI_IV.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = true)
    private Float avaliacao;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque;

    @Column(name = "imagem_padrao", nullable = true)
    private String imagemPadrao;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "produto")
    @JsonManagedReference
    private List<ImagemProduto> imagensAdicionais;

}