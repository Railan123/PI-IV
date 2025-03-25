package com.PI_IV.service;

import com.PI_IV.DAO.InterfaceImagemProduto;
import com.PI_IV.DAO.InterfaceProduto;
import com.PI_IV.model.ImagemProduto;
import com.PI_IV.model.Produto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final InterfaceProduto repository;
    private final InterfaceImagemProduto imagemProdutoRepository;

    public ProdutoService(InterfaceProduto repository, InterfaceImagemProduto imagemProdutoRepository) {
        this.repository = repository;
        this.imagemProdutoRepository = imagemProdutoRepository;
    }

    // Lista todos os produtos
    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    // Lista apenas produtos ativos
    public List<Produto> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    // Busca um produto pelo ID
    public Optional<Produto> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    // Salva um novo produto
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    // Atualiza os dados de um produto existente
    public Produto atualizar(Integer id, Produto produtoAtualizado) {
        return repository.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setAvaliacao(produtoAtualizado.getAvaliacao());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
            produto.setAtivo(produtoAtualizado.isAtivo());
            return repository.save(produto);
        }).orElse(null);
    }

    // Ativa ou desativa um produto
    public Optional<Produto> ativarDesativar(Integer id) {
        return repository.findById(id).map(produto -> {
            produto.setAtivo(!produto.isAtivo());
            return repository.save(produto);
        });
    }

    // Salva a imagem principal
    public Produto salvarImagemPrincipal(Integer id, MultipartFile imagem) {
        return repository.findById(id).map(produto -> {
            try {
                produto.setImagemPadrao(imagem.getBytes());
                return repository.save(produto);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).orElse(null);
    }

    // Recupera a imagem principal do produto
    public byte[] recuperarImagem(Integer id) {
        return repository.findById(id)
                .map(Produto::getImagemPadrao)
                .orElse(null);
    }

    // Salva múltiplas imagens adicionais
    public List<ImagemProduto> salvarImagensAdicionais(Integer id, List<MultipartFile> imagens) {
        return repository.findById(id).map(produto -> {
            List<ImagemProduto> listaImagens = new ArrayList<>();
            for (MultipartFile imagem : imagens) {
                try {
                    ImagemProduto imgProduto = new ImagemProduto();
                    imgProduto.setProduto(produto);
                    imgProduto.setImagem(imagem.getBytes());
                    listaImagens.add(imagemProdutoRepository.save(imgProduto));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return listaImagens;
        }).orElse(Collections.emptyList());
    }

    // Recupera todas as imagens de um produto
    public List<byte[]> listarImagensProduto(Integer id) {
        return imagemProdutoRepository.findByProdutoId(id).stream()
                .map(ImagemProduto::getImagem)
                .collect(Collectors.toList());
    }
}

