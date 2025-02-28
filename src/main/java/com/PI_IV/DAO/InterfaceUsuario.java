package com.PI_IV.DAO;

import com.PI_IV.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface InterfaceUsuario extends CrudRepository<Usuario, Integer> {

    // Método para buscar por nome
    Optional<Usuario> findByNome(String nome);

    // Método para buscar por email
    Optional<Usuario> findByEmail(String email);
}
