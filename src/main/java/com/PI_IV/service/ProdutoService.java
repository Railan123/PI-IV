package com.PI_IV.service;

import com.PI_IV.DAO.InterfaceProduto;
import com.PI_IV.model.Produto;
import org.springframework.stereotype.Service;

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
            produto.setNome(produtoAtualizado.getNome());
            produto.setAvaliacao(produtoAtualizado.getAvaliacao());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
            produto.setImagemPadrao(produtoAtualizado.getImagemPadrao());
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
}
