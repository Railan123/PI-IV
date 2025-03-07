package com.PI_IV.service;

import com.PI_IV.DAO.InterfaceUsuario;
import com.PI_IV.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final InterfaceUsuario repository;
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependências
    public UsuarioService(InterfaceUsuario repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Criptografa senhas com BCrypt
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) repository.findAll();
    }

    // Criar um novo usuário
    public Usuario criarUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    // Editar (atualizar) dados de um usuário
    public Usuario editarUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar (caso tenha alterado)
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return repository.save(usuario);
    }

    // Método para ativar ou desativar um usuário
    public Usuario ativarDesativarUsuario(int id, boolean ativo) {
        Optional<Usuario> usuarioExistente = repository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuarioAtual = usuarioExistente.get();
            usuarioAtual.setAtivo(ativo);  // Altera o status de ativo/desativo
            return repository.save(usuarioAtual);  // Atualiza o usuário no banco de dados
        }
        return null;  // Retorna null se o usuário não for encontrado
    }

    // Buscar usuário pelo ID
    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return repository.findById(id);
    }

    // Buscar usuário pelo CPF
    public Optional<Usuario> buscarUsuarioPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    // Buscar usuário pelo Email
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return repository.findByEmail(email);
    }
}
