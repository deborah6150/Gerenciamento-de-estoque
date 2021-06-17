package com.produto.crudprodutothymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.produto.crudprodutothymeleaf.entitys.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	

}
