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
@CrossOrigin(origins = "*") // Permite chamadas de qualquer frontend
public class UsuarioController {

    @Autowired
    private InterfaceUsuario dao;


    @GetMapping
    public List<Usuario> listaUsuarios (){ //meotodo para procurar todos os usuarios do banco
        return (List<Usuario>) dao.findAll();
    }

    @PostMapping // notação para CRIAR um novo usuario
    public Usuario criarUsuario (@RequestBody Usuario usuario){
        Usuario usuarioNovo = dao.save(usuario);
        return usuarioNovo; //retorna o usuario pra mostrar qual usuario foi salvo no mysql
    }

    @PutMapping //serve pra EDITAR um usuario que ja EXISTE no banco
    public Usuario editarUsuario (@RequestBody Usuario usuario){
        Usuario usuarioNovo = dao.save(usuario);
        return usuarioNovo; //retorna o usuario pra mostrar qual usuario foi salvo no mysql
    }

}
