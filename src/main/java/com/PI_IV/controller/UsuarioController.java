package com.PI_IV.controller;

import com.PI_IV.DAO.InterfaceUsuario;
import com.PI_IV.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*") // Permite chamadas de qualquer frontend
public class UsuarioController {


    @Autowired
    private InterfaceUsuario dao;

    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios() { //metodo para procurar todos os usuarios do banco
        List<Usuario> lista = (List<Usuario>) dao.findAll();
        return ResponseEntity.status(200).body(lista); //retorna lista de usuarios com o código 200 falando q deu tudo certo
    }

    @PostMapping // notação POST para CRIAR um novo usuario
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioNovo = dao.save(usuario);
        return ResponseEntity.status(201).body(usuarioNovo); //retorna o novo usuario com o codigo 201 falando q deu tudo certo
    }

    @PutMapping //serve pra EDITAR um usuario que ja EXISTE no banco
    public Usuario editarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioNovo = dao.save(usuario);
        return usuarioNovo; //retorna o usuario pra mostrar qual usuario foi salvo no mysql
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        // Defina os dados fixos do admin
        String adminEmail = "admin@senac.com";
        String adminSenha = "admin123";

        if (!usuario.getEmail().equals(adminEmail) || !usuario.getSenha().equals(adminSenha)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos!");
        }

        return ResponseEntity.ok("Login bem-sucedido!");
    }


}
