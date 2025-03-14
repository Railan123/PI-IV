package com.PI_IV.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal avaliacao;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidadeEstoque=0;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String imagemPadrao;

    @Lob
    @Column(name = "imagem_blob")
    private byte[] imagemBlob;

    public void setAvaliacao(BigDecimal avaliacao) {
        if (avaliacao.compareTo(BigDecimal.ONE) < 0 || avaliacao.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("A avaliação deve estar entre 1 e 5.");
        }
        this.avaliacao = avaliacao;
    }
}
