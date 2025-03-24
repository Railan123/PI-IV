package com.PI_IV.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "imagens_produto")
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Produto ao qual a imagem pertence
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Dados da imagem (BLOB)
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagem;
}
