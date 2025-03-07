package com.PI_IV.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "usuarios")
@NoArgsConstructor //NOTAÇÃO PRA NAÕ PRECISAR USAR CONSTRUTOR VAZIO
@AllArgsConstructor //NOTAÇÃO PRA NAÕ PRECISAR USAR CONSTRUTOR CHEIO
@Data // notação pra não precisar usar getter e setter
public class Usuario {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) //gerar numeros sequencias para o id
    @Column (name = "id")
    private int id;

    @Column (name = "nome_completo", length = 200, nullable = true)
    private String nome;

    @Column(name = "cpf", length = 14, nullable = true)
    private String cpf;

    @Column (name = "email", length = 100, nullable = true)
    private String email;

    @Column (name = "senha", columnDefinition = "TEXT", nullable = true)
    private String senha;

    @Column (name = "grupo", length = 200, nullable = true)
    private String grupo;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;


}
