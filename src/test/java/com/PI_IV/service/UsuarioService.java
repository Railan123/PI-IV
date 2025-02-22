package com.PI_IV.service;



// Importa as classes necessárias para a funcionalidade
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pi_iv.model.Usuario;
import com.pi_iv.repository.UsuarioRepository;

@Service // Indica que esta classe é um serviço
public class UsuarioService {

    @Autowired // Injeção de dependência do repositório
    private UsuarioRepository usuarioRepository;

    // Método que vai cadastrar um novo usuário
    public Usuario cadastrarUsuario(String cpf, String nome, String email, String grupo, String senha, boolean status) {
        
        // Cria um novo objeto Usaario com os dados fornecidos
        Usuario usuario = new Usuario(cpf, nome, email, grupo, senha, status);
        
        // Salva o usuário no banco de dados e retorna o usuário salvo
        return usuarioRepository.save(usuario); // Salva o usuário no banco de dados
    }
}
