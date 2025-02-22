package com.PI_IV.DAO;

import com.PI_IV.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface InterfaceUsuario extends CrudRepository <Usuario, Integer> {
}
