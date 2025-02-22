//package com.PI_IV.service;
//
//
//
//// Importa as classes necessárias para a funcionalidade
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.PI_IV.model.Usuario;
//
//@Service // Indica que esta classe é um serviço
//public class UsuarioService {
//
//    @Autowired // Injeção de dependência do repositório
//    private UsuarioRepository usuarioRepository;
//
//    // Método que vai cadastrar um novo usuário
//    public Usuario cadastrarUsuario(String cpf, String nome, String email, String grupo, String senha, boolean status) {
//
//        // Cria um novo objeto Usuario com os dados fornecidos
//        Usuario usuario = new Usuario(cpf, nome, email, grupo, senha, status);
//
//        // Salva o usuário no banco de dados e retorna o usuário salvo
//        return usuarioRepository.save(usuario); // Salva o usuário no banco de dados
//    }
//}
