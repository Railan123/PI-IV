package com.PI_IV.controller;

import com.PI_IV.model.Produto;
import com.PI_IV.model.ImagemProduto;
import com.PI_IV.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;
    // Caminho fixo para o diretório de imagens fora de src/main/resources
    private static final String UPLOAD_DIR = "C:/Users/Administrador/PI-IV/imagens_produto/";  // Caminho configurado para imagens

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Integer id) {
        Optional<Produto> produto = service.buscarPorId(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Produto> criarProduto(
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("quantidadeEstoque") Integer quantidadeEstoque,
            @RequestParam("descricao") String descricao,
            @RequestParam("avaliacao") Float avaliacao,
            @RequestParam(value = "imagens", required = false) List<MultipartFile> imagens) {

        if (nome == null || nome.isEmpty() ||
                preco == null || preco <= 0 ||
                quantidadeEstoque == null || quantidadeEstoque < 0 ||
                descricao == null || descricao.isEmpty() ||
                avaliacao == null || avaliacao < 1 || avaliacao > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(quantidadeEstoque);
        produto.setDescricao(descricao);
        produto.setAvaliacao(avaliacao);

        try {
            // Criar diretório fixo se não existir
            File pastaRaiz = new File(UPLOAD_DIR);
            if (!pastaRaiz.exists()) {
                Files.createDirectories(pastaRaiz.toPath());
            }

            // Criar pasta do produto dentro da pasta fixa
            String nomeProdutoFormatado = nome.replaceAll("[^a-zA-Z0-9]", "_");
            File diretorioProduto = new File(UPLOAD_DIR + nomeProdutoFormatado);
            Files.createDirectories(diretorioProduto.toPath());

            List<ImagemProduto> imagensProduto = new ArrayList<>();
            String caminhoImagemPrincipal = "";

            // Processar imagens
            if (imagens != null && !imagens.isEmpty()) {
                int i = 1;
                for (MultipartFile imagem : imagens) {
                    if (!imagem.isEmpty()) {
                        // Garante nomes únicos para as imagens
                        String nomeArquivo = "imagem_" + i + "_" + System.currentTimeMillis() + ".png";
                        File arquivoImagem = new File(diretorioProduto, nomeArquivo);

                        // Transferir arquivo para o diretório correto
                        imagem.transferTo(arquivoImagem);

                        // Criar objeto de ImagemProduto
                        ImagemProduto imagemProduto = new ImagemProduto();
                        imagemProduto.setNome(imagem.getOriginalFilename());
                        imagemProduto.setCaminho("imagens_produto/" + nomeProdutoFormatado + "/" + nomeArquivo);
                        imagemProduto.setPadrao(i == 1);  // A primeira imagem é a padrão

                        imagensProduto.add(imagemProduto);

                        // Definir a primeira imagem como a principal
                        if (i == 1) {
                            caminhoImagemPrincipal = imagemProduto.getCaminho();
                        }

                        i++;
                    }
                }
            }

            // Atualizar a imagem principal do produto no banco
            produto.setImagemPadrao(caminhoImagemPrincipal);

            // Salvar produto com imagens no banco de dados
            Produto produtoSalvo = service.salvarProdutoComImagens(produto, imagensProduto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/imagens/{produtoNome}/{imagemNome}")
    @ResponseBody
    public byte[] exibirImagem(@PathVariable String produtoNome, @PathVariable String imagemNome) throws IOException {
        // Caminho da imagem no diretório correto
        File imagem = new File(UPLOAD_DIR + produtoNome + "/" + imagemNome);
        return java.nio.file.Files.readAllBytes(imagem.toPath());
    }

    @PutMapping("/{id}/ativarDesativar")
    public ResponseEntity<Produto> ativarDesativarProduto(@PathVariable Integer id) {
        Optional<Produto> produtoAtualizado = service.ativarDesativar(id);
        if (produtoAtualizado.isPresent()) {
            return ResponseEntity.ok(produtoAtualizado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/imagens/{produtoId}")
    public ResponseEntity<List<String>> listarImagensProduto(@PathVariable Integer produtoId) {
        List<String> imagens = service.listarImagensProduto(produtoId);
        return ResponseEntity.ok(imagens);
    }


}
