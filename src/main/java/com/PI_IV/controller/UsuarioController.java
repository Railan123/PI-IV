package com.PI_IV.controller;

import com.PI_IV.DAO.InterfaceUsuario;
import com.PI_IV.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private InterfaceUsuario dao;

    //meotodo para procurar todos os usuarios do banco
    @GetMapping("/usuarios")
    public List<Usuario> listaUsuarios (){
        return (List<Usuario>) dao.findAll();
    }

}
