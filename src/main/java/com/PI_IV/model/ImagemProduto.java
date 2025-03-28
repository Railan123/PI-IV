package com.PI_IV.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "imagens_produto")
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImg;

    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "caminho", length = 255, nullable = false)
    private String caminho;

    @Column(name = "padrao", nullable = false)
    private boolean padrao;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    @JsonBackReference // Associando a imagem ao produto
    private Produto produto;

    public ImagemProduto(Integer idImg, String nome, boolean padrao, Produto produto, String caminho) {
        this.idImg = idImg;
        this.nome = nome;
        this.padrao = padrao;
        this.produto = produto;
        this.caminho = caminho;
    }

}
