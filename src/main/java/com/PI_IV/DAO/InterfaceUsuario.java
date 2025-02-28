package com.PI_IV.DAO;

import com.PI_IV.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterfaceUsuario extends CrudRepository <Usuario, Integer> {

    // Busca usuário pelo ID
    Optional<Usuario> findById(Integer id);

    // Busca usuário pelo email
    Optional<Usuario> findByEmail(String email);

    // Busca usuário pelo nome
    Optional<Usuario> findByNome(String nome);
}


