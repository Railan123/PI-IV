package com.PI_IV.controller;

import com.PI_IV.model.ImagemProduto;
import com.PI_IV.model.Produto;
import com.PI_IV.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Produto>> listarAtivos() {
        return ResponseEntity.ok(service.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Integer id) {
        Optional<Produto> produto = service.buscarPorId(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        Optional<Produto> produtoAtualizado = service.atualizar(id, produto);
        return produtoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<Produto> salvarImagemPrincipal(@PathVariable Integer id, @RequestParam("imagem") MultipartFile imagem) {
        Optional<Produto> produtoAtualizado = service.salvarImagemPrincipal(id, imagem);
        return produtoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/imagens-adicionais")
    public ResponseEntity<Void> salvarImagemAdicional(@PathVariable Integer id, @RequestParam("imagem") MultipartFile imagem) throws IOException {
        service.salvarImagemAdicional(id, imagem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/imagens")
    public ResponseEntity<List<ImagemProduto>> recuperarImagens(@PathVariable Integer id) {
        return ResponseEntity.ok(service.recuperarImagens(id));
    }

    // Endpoint para ativar ou desativar um produto
    @PutMapping("/{id}/ativarDesativar")
    public ResponseEntity<Produto> ativarDesativarProduto(@PathVariable Integer id) {
        Optional<Produto> produtoAtualizado = service.ativarDesativar(id);
        return produtoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para atualizar a quantidade em estoque
    @PutMapping("/{id}/atualizarQuantidade")
    public ResponseEntity<Produto> atualizarQuantidade(@PathVariable Integer id, @RequestBody Produto produto) {
        Optional<Produto> produtoAtualizado = service.atualizarQuantidade(id, produto.getQuantidadeEstoque());
        return produtoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
