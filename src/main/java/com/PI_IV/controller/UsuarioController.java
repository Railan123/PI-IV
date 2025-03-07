package com.PI_IV.controller;

import com.PI_IV.DAO.InterfaceUsuario;
import com.PI_IV.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private InterfaceUsuario dao;

    // Injeta o encriptador de senhas
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Método para listar todos os usuários no banco de dados
    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        List<Usuario> lista = (List<Usuario>) dao.findAll();
        return ResponseEntity.status(200).body(lista);
    }

    // Método para criar um novo usuário no banco de dados
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));  // Criptografa a senha
        Usuario usuarioNovo = dao.save(usuario);
        return ResponseEntity.status(201).body(usuarioNovo);
    }

    // Método para alterar um usuário no banco de dados
    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));  // Criptografa a senha
        Usuario usuarioAtualizado = dao.save(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Método de login do usuário
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        // Dados fixos para validação (pode ser ajustado conforme necessário)
        String emailCorreto = "admin@gmail.com";
        String senhaCorreta = "admin123";

        // Se o usuário e senha forem os mesmos fixos, liberar acesso
        if (usuario.getEmail().equals(emailCorreto) && usuario.getSenha().equals(senhaCorreta)) {
            return ResponseEntity.ok("Login bem-sucedido (usuário estático)!");
        }

        // Verificação se o usuário existe no banco de dados pelo EMAIL
        Optional<Usuario> usuarioOpt = dao.findByEmail(usuario.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuario usuarioEncontrado = usuarioOpt.get();

            // Verificar se a senha é a mesma encriptada do banco de dados
            if (passwordEncoder.matches(usuario.getSenha(), usuarioEncontrado.getSenha())) {
                return ResponseEntity.ok("Login bem-sucedido!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
    }

    // Método para buscar um usuário pelo ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = dao.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Método para buscar um usuário pelo EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = dao.findByEmail(email);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Método para buscar um usuário pelo CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Usuario> buscarPorCpf(@PathVariable String cpf) {
        Optional<Usuario> usuario = dao.findByCpf(cpf);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Método para ativar ou desativar um usuário
    @PutMapping("/ativarDesativar/{id}")
    public ResponseEntity<Usuario> ativarDesativarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = dao.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuarioAtual = usuarioExistente.get();
            usuarioAtual.setAtivo(usuario.isAtivo());  // Atualiza o status de ativação/desativação
            dao.save(usuarioAtual);  // Salva no banco de dados
            return ResponseEntity.ok(usuarioAtual);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
