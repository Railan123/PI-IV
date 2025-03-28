package com.PI_IV.service;

import com.PI_IV.DAO.InterfaceImagemProduto;
import com.PI_IV.DAO.InterfaceProduto;
import com.PI_IV.model.ImagemProduto;
import com.PI_IV.model.Produto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final InterfaceProduto produtoRepository;
    private final InterfaceImagemProduto imagemProdutoRepository;

    // Caminho configurado no application.properties
    @Value("${caminho.imagens}")
    private String caminhoImagens;  // Este valor será "C:/Users/Administrador/PI-IV/imagens_produto" quando configurado no application.properties

    public ProdutoService(InterfaceProduto produtoRepository, InterfaceImagemProduto imagemProdutoRepository) {
        this.produtoRepository = produtoRepository;
        this.imagemProdutoRepository = imagemProdutoRepository;
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        for (Produto produto : produtos) {
            if (!produto.getImagensAdicionais().isEmpty()) {
                produto.setImagemPadrao(produto.getImagensAdicionais().get(0).getCaminho());
            }
        }
        return produtos;
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Integer id, Produto produto) {
        return produtoRepository.findById(id)
                .map(existingProduto -> {
                    existingProduto.setNome(produto.getNome());
                    existingProduto.setPreco(produto.getPreco());
                    existingProduto.setDescricao(produto.getDescricao());
                    existingProduto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
                    return produtoRepository.save(existingProduto);
                })
                .orElse(null);
    }

    public Optional<Produto> ativarDesativar(Integer id) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setAtivo(!produto.isAtivo());
                    return produtoRepository.save(produto);
                });
    }

    // Método para salvar as imagens no diretório e registrar o caminho
    public void salvarImagemProduto(MultipartFile file, Integer produtoId) throws IOException {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto != null) {
            // Gerar o nome da imagem
            String nomeImagem = produtoId + "_" + file.getOriginalFilename();
            File imagemFile = new File(caminhoImagens + "/" + nomeImagem);

            // Salvar a imagem no diretório
            file.transferTo(imagemFile);

            // Salvar o caminho da imagem no banco de dados
            ImagemProduto imagemProduto = new ImagemProduto();
            imagemProduto.setProduto(produto);
            imagemProduto.setCaminho(nomeImagem);
            imagemProdutoRepository.save(imagemProduto);
        }
    }

    // Método para recuperar a imagem padrão do produto
    public String recuperarImagem(Integer id) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto != null && produto.getImagemPadrao() != null) {
            return "/imagens_produto/" + produto.getImagemPadrao();
        }
        return null;
    }

    // Listar todas as imagens de um produto
    public List<String> listarImagensProduto(Integer id) {
        List<ImagemProduto> imagens = imagemProdutoRepository.findByProdutoId(id);
        return imagens.stream()
                .map(imagemProduto -> "/imagens_produto/" + imagemProduto.getCaminho())
                .toList();
    }

    // Salvar o produto com suas imagens
    @Transactional
    public Produto salvarProdutoComImagens(Produto produto, List<ImagemProduto> imagens) {
        Produto produtoSalvo = produtoRepository.save(produto);
        for (ImagemProduto imagemProduto : imagens) {
            imagemProduto.setProduto(produtoSalvo);
            imagemProdutoRepository.save(imagemProduto);
        }
        return produtoSalvo;
    }
}
