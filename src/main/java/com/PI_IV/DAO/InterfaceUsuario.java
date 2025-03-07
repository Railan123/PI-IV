package com.PI_IV.DAO;

import com.PI_IV.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface InterfaceUsuario extends CrudRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

}