package com.pi_iv.controller;
import com.pi_iv.model.Usuario;
import com.pi_iv.service.UsuarioService;
import com.pi_iv.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Define que essa classe será um controlador REST
@RequestMapping("/usuarios") // Mapeia a URL base para as requisições de usuários

public class UsuarioController {

    @Autowired // Injeção de dependência, para o Spring gerenciar o serviço
    private UsuarioService usuarioService;

    // Endpoint para cadastrar um usuário (método POST)
    @PostMapping("/cadastrar") 
    public Usuario cadastrarUsuario(
        @RequestParam String cpf,
        @RequestParam String nome,
        @RequestParam String email, 
        @RequestParam  String grupo,
        @RequestParam String senha,
        @RequestParam  Boolean status){


                    // Chama o método do serviço para cadastrar o usuário no banco de dados
            return usuarioService.cadastrarUsuario(cpf, nome, email, grupo, senha, status);

        }
    
}