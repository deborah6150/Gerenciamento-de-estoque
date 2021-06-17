package com.produto.crudprodutothymeleaf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.produto.crudprodutothymeleaf.entitys.Produto;
import com.produto.crudprodutothymeleaf.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public void excluir(Long id) {
		produtoRepository.deleteById(id);
	}
	public Produto buscarPorId(Long id) {
		return produtoRepository.findById(id).get();
	}
	
	public List<Produto> listarTodosProdutos(){
		return produtoRepository.findAll();
	}
}
