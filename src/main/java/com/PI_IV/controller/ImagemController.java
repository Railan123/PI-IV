package com.PI_IV.controller;

import com.PI_IV.model.AlterarImagemDto;
import com.PI_IV.model.Produto;
import com.PI_IV.model.ImagemProduto;
import com.PI_IV.service.ImagemProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/imagemProduto")
public class ImagemController {

    private final ImagemProdutoService imagemProdutoService;
    private final String UPLOAD_DIR = "C:/Users/Administrador/Desktop/IMAGENS_PI/"; // Local das imagens

    public ImagemController(ImagemProdutoService imagemProdutoService) {
        this.imagemProdutoService = imagemProdutoService;
    }

    @GetMapping
    public List<ImagemProduto> listaImagensProduto() {
        return imagemProdutoService.findAll();
    }

    @PostMapping
    public ResponseEntity<String> registrarImagens(@RequestParam("produto") String produtoJson,
                                                   @RequestParam("nomesImagens") List<String> nomesImagens,
                                                   @RequestParam(value = "principal", required = false) String principalIndexStr) {

        // Processa o JSON do produto recebido
        ObjectMapper mapper = new ObjectMapper();
        Produto produto;
        try {
            produto = mapper.readValue(produtoJson, Produto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar o JSON do produto.");
        }

        if (nomesImagens.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nenhum nome de imagem fornecido.");
        }

        Integer principalIndex = null;
        // Converte a String para Integer, se possível
        if (principalIndexStr != null) {
            try {
                principalIndex = Integer.parseInt(principalIndexStr);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Índice principal inválido.");
            }
        }

        try {
            // Salva as imagens no banco de dados
            for (int i = 0; i < nomesImagens.size(); i++) {
                String nomeImagem = nomesImagens.get(i);
                String caminhoImagem = UPLOAD_DIR + produto.getNome() + "/" + nomeImagem; // Caminho completo

                // A primeira imagem será principal se nenhuma outra for definida
                boolean isPrincipal = (principalIndex == null) ? (i == 0) : (i == principalIndex);

                ImagemProduto img = new ImagemProduto(Integer.valueOf(nomeImagem), caminhoImagem, isPrincipal, produto, caminhoImagem);
                imagemProdutoService.saveImagemProduto(img);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Caminhos das imagens salvos com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar no banco: " + e.getMessage());
        }
    }


    @PutMapping
    public ResponseEntity<String> editarImagemProduto(@RequestBody AlterarImagemDto alterarImagemDto) {
        Produto produto = alterarImagemDto.getProduto();
        int principal = alterarImagemDto.getPrincipal();
        List<ImagemProduto> arquivos = alterarImagemDto.getArquivos();

        try {
            // Atualiza as imagens no banco de dados
            for (int i = 0; i < arquivos.size(); i++) {
                ImagemProduto arquivo = arquivos.get(i);
                String caminhoImagem = UPLOAD_DIR + produto.getNome() + "/" + arquivo.getNome();

                boolean isPrincipal = (i == principal);

                ImagemProduto img = new ImagemProduto(arquivo.getIdImg(),
                        arquivo.getNome(),
                        isPrincipal,
                        produto,
                        caminhoImagem);

                imagemProdutoService.saveImagemProduto(img);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Imagens atualizadas com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar imagens: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirImagemProduto(@PathVariable Integer id) {
        Optional<ImagemProduto> imagemProduto = imagemProdutoService.findById(id);
        if (imagemProduto.isPresent()) {
            imagemProdutoService.deleteImagemProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body("Imagem deletada com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imagem não encontrada.");
        }
    }
}
