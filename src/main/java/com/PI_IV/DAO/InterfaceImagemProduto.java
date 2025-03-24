package com.PI_IV.DAO;

import com.PI_IV.model.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterfaceImagemProduto extends JpaRepository<ImagemProduto, Integer> {
    List<ImagemProduto> findByProdutoId(Integer produtoId);
}
