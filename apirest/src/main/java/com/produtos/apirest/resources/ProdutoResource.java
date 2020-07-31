package com.produtos.apirest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.produtos.apirest.models.Produto;
import com.produtos.apirest.repository.ProdutoRepository;

@RestController
@RequestMapping(value="/api")
public class ProdutoResource {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	// Método para listar todos os produtos cadastrados no banco de dados 
	@GetMapping("/produtos")
	public List<Produto> listaProdutos() {
		return produtoRepository.findAll();
	}
	
	// Método para retornar um produto pelo ID 
	@GetMapping("/produto/{id}")
	public Produto carregaProduto(@PathVariable(value="id") long id) {
		return produtoRepository.findById(id);
	}
	
	// Método para salvar um novo produto no banco de dados 
	@PostMapping("/produto")
	public Produto salvaProduto(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}
	
	// Método para deletar um produto do banco de dados pelo ID 
	@DeleteMapping("/produto/{id}")
	public void deletaProdutoById(@PathVariable(value="id") long id) {
		produtoRepository.deleteById(id);
	}
	
	// Método atualizar os dados de um produto no banco de dados 
	@PutMapping("/produto")
	public Produto editaProduto(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}
	
}
