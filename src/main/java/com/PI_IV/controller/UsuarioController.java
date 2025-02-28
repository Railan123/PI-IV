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

    //injeta o encriptador de semhas
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //metodo para LISTAR todo os usuarios no banco de dados
    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios() {
        List<Usuario> lista = (List<Usuario>) dao.findAll();
        return ResponseEntity.status(200).body(lista);
    }

    //metodo para CRIAR um novo usuario no banco de dados
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioNovo = dao.save(usuario);
        return ResponseEntity.status(201).body(usuarioNovo);
    }

    //metodo para ALTERAR um usuario no banco de dados
    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioAtualizado = dao.save(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }


    //metodo para login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        //dados fixos
        String emailCorreto = "admin@gmail.com";
        String senhaCorreta = "admin123";


        //SE o usuario e senha forem os mesmos fixos, liberar acesso
        if (usuario.getEmail().equals(emailCorreto) && usuario.getSenha().equals(senhaCorreta)) {
            return ResponseEntity.ok("Login bem-sucedido (usuário estático)!");
        }

        // Verificação se o usuario existe no banco de dados pelo EMAIL
        Optional<Usuario> usuarioOpt = dao.findByEmail(usuario.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuario usuarioEncontrado = usuarioOpt.get();

            //verificar se a senha é a mesma encriptada do banco de dados
            if (passwordEncoder.matches(usuario.getSenha(), usuarioEncontrado.getSenha())) {
                return ResponseEntity.ok("Login bem-sucedido!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
    }
}
