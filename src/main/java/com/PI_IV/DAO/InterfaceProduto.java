package com.PI_IV.DAO;

import com.PI_IV.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterfaceProduto extends JpaRepository<Produto, Integer> {
    List<Produto> findByAtivoTrue(); // Retorna apenas produtos ativos
}
