package com.PI_IV.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImagemDto {
    private Produto produto;
    private ArrayList<MultipartFile> arquivos = new ArrayList<>();
    private int principal;
}

