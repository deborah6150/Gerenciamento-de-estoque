package com.produto.crudprodutothymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produto.crudprodutothymeleaf.entitys.Produto;
import com.produto.crudprodutothymeleaf.service.ProdutoService;

@RestController
@RequestMapping(value="/ap/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping()
	public Produto cadastrarProduto(@RequestBody Produto produto) {
		return produtoService.salvar(produto);
		
	}
	@DeleteMapping(value= "/{id}")
	public void excluirProduto(@PathVariable Long id) {
		produtoService.excluir(id);
	}
	@GetMapping(value= "/{id}")
	public Produto buscarProdutoPorId(@PathVariable Long id){
		return produtoService.buscarPorId(id);
		
	}
	
	@GetMapping()
	public List<Produto> listarTodosProdutos(){
		return produtoService.listarTodosProdutos();
	}
	

}
