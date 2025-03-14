package com.PI_IV.controller;

import com.PI_IV.model.Produto;
import com.PI_IV.service.ProdutoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        return ResponseEntity.status(201).body(service.salvar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        Produto produtoAtualizado = service.atualizar(id, produto);
        return produtoAtualizado != null ? ResponseEntity.ok(produtoAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Integer id) {
        return service.excluir(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/ativarDesativar/{id}")
    public ResponseEntity<Produto> ativarDesativarProduto(@PathVariable Integer id) {
        Optional<Produto> produtoOpt = service.ativarDesativar(id);
        return produtoOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<Produto> salvarImagem(@PathVariable Integer id, @RequestParam("imagem") MultipartFile imagem) {
        Produto produtoAtualizado = service.salvarImagem(id, imagem);
        return produtoAtualizado != null ? ResponseEntity.ok(produtoAtualizado) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> recuperarImagem(@PathVariable Integer id) {
        byte[] imagem = service.recuperarImagem(id);
        if (imagem == null) return ResponseEntity.notFound().build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");

        return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
    }
}
