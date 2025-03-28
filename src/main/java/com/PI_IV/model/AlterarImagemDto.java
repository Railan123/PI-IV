package com.PI_IV.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlterarImagemDto {

    private Produto produto;
    private int principal;
    private List<ImagemProduto> arquivos;
}