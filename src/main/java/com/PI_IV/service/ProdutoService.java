package com.PI_IV.service;

import com.PI_IV.DAO.InterfaceProduto;
import com.PI_IV.model.Produto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final InterfaceProduto repository;

    public ProdutoService(InterfaceProduto repository) {
        this.repository = repository;
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public List<Produto> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto atualizar(Integer id, Produto produtoAtualizado) {
        return repository.findById(id).map(produto -> {
            produto.setNome(Optional.ofNullable(produtoAtualizado.getNome()).orElse(produto.getNome()));
            produto.setAvaliacao(Optional.ofNullable(produtoAtualizado.getAvaliacao()).orElse(produto.getAvaliacao()));
            produto.setDescricao(Optional.ofNullable(produtoAtualizado.getDescricao()).orElse(produto.getDescricao()));
            produto.setPreco(Optional.ofNullable(produtoAtualizado.getPreco()).orElse(produto.getPreco()));
            produto.setQuantidadeEstoque(Optional.ofNullable(produtoAtualizado.getQuantidadeEstoque()).orElse(produto.getQuantidadeEstoque()));
            produto.setImagemPadrao(Optional.ofNullable(produtoAtualizado.getImagemPadrao()).orElse(produto.getImagemPadrao()));
            produto.setAtivo(produtoAtualizado.isAtivo());

            return repository.save(produto);
        }).orElse(null);
    }

    public boolean excluir(Integer id) {
        return repository.findById(id).map(produto -> {
            repository.delete(produto);
            return true;
        }).orElse(false);
    }

    public Optional<Produto> ativarDesativar(Integer id) {
        return repository.findById(id).map(produto -> {
            produto.setAtivo(!produto.isAtivo());
            return repository.save(produto);
        });
    }

    public Produto salvarImagem(Integer id, MultipartFile imagem) {
        return repository.findById(id).map(produto -> {
            try {
                byte[] imagemBytes = imagem.getBytes();
                produto.setImagemPadrao(imagem.getOriginalFilename());
                produto.setImagemBlob(imagemBytes);
                return repository.save(produto);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar a imagem: " + e.getMessage());
            }
        }).orElse(null);
    }

    public byte[] recuperarImagem(Integer id) {
        return repository.findById(id)
                .map(Produto::getImagemBlob)
                .orElse(null);
    }
}
