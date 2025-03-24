package com.PI_IV.service;

import com.PI_IV.DAO.InterfaceProduto;
import com.PI_IV.DAO.InterfaceImagemProduto;
import com.PI_IV.model.ImagemProduto;
import com.PI_IV.model.Produto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final InterfaceProduto produtoRepository;
    private final InterfaceImagemProduto imagemRepository;

    public ProdutoService(InterfaceProduto produtoRepository, InterfaceImagemProduto imagemRepository) {
        this.produtoRepository = produtoRepository;
        this.imagemRepository = imagemRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
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

    public Optional<Produto> atualizar(Integer id, Produto produtoAtualizado) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setAvaliacao(produtoAtualizado.getAvaliacao());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
            produto.setAtivo(produtoAtualizado.isAtivo());
            return produtoRepository.save(produto);
        });
    }

    public Optional<Produto> ativarDesativar(Integer id) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setAtivo(!produto.isAtivo());
            return produtoRepository.save(produto);
        });
    }

    public Optional<Produto> salvarImagemPrincipal(Integer id, MultipartFile imagem) {
        return produtoRepository.findById(id).map(produto -> {
            try {
                produto.setImagemPadrao(imagem.getBytes());
                return produtoRepository.save(produto);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public void salvarImagemAdicional(Integer id, MultipartFile imagem) throws IOException {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        ImagemProduto img = new ImagemProduto();
        img.setProduto(produto);
        img.setImagem(imagem.getBytes());
        imagemRepository.save(img);
    }

    public List<ImagemProduto> recuperarImagens(Integer id) {
        return imagemRepository.findByProdutoId(id);
    }

    public Optional<Produto> atualizarQuantidade(Integer id, Integer novaQuantidade) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setQuantidadeEstoque(novaQuantidade);
            produtoRepository.save(produto);
            return Optional.of(produto);
        }

        return Optional.empty();
    }

}
