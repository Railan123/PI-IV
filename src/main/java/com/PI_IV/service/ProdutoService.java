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

    // Salva uma imagem associada ao produto (principal ou adicional)
    public Produto salvarImagem(Integer id, MultipartFile imagem, boolean isPrincipal) {
        return repository.findById(id).map(produto -> {
            try {
                if (isPrincipal) {
                    produto.setImagemPadrao(imagem.getBytes());
                } else {
                    produto.setImagemBlob(imagem.getBytes());
                }
                return repository.save(produto);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).orElse(null);
    }

    // Recupera a imagem de um produto
    public byte[] recuperarImagem(Integer id) {
        return repository.findById(id)
                .map(Produto::getImagemPadrao)
                .orElse(null);
    }
}
