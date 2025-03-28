package com.PI_IV.service;

import com.PI_IV.DAO.InterfaceImagemProduto;
import com.PI_IV.model.ImagemProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ImagemProdutoService {

    // Injeção da interface do repositório
    private final InterfaceImagemProduto daoImagemProduto;

    // Salva uma nova imagem de produto
    public ImagemProduto saveImagemProduto(ImagemProduto imagemProduto) {
        return daoImagemProduto.save(imagemProduto);
    }

    // Altera uma imagem de produto existente
    public ImagemProduto alterImagemProduto(ImagemProduto imagemProduto) {
        return daoImagemProduto.save(imagemProduto);
    }

    // Recupera todas as imagens de produtos
    public List<ImagemProduto> findAll() {
        return daoImagemProduto.findAll();
    }

    // Recupera uma imagem de produto pelo ID
    public Optional<ImagemProduto> findById(Integer id) {
        return daoImagemProduto.findById(id);
    }

    // Deleta uma imagem de produto pelo ID
    public void deleteImagemProduto(Integer id) {
        daoImagemProduto.deleteById(id);
    }
}
