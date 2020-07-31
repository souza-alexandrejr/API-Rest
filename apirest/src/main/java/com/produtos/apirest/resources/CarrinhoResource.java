package com.produtos.apirest.resources;

import java.util.HashMap;
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
import com.produtos.apirest.models.Carrinho;
import com.produtos.apirest.models.Cupom;
import com.produtos.apirest.models.Item;
import com.produtos.apirest.models.Produto;
import com.produtos.apirest.repository.CarrinhoRepository;
import com.produtos.apirest.repository.ProdutoRepository;

@RestController
@RequestMapping(value="/api")
public class CarrinhoResource {

	Cupom cupom = new Cupom();
	double total;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	CarrinhoRepository carrinhoRepository;
	
	// Método para CARREGAR o carrinho do banco de dados
	@GetMapping("/carrinho")
	public HashMap<String, Object> carregaCarrinho() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		List<Carrinho> listaItens = carrinhoRepository.findAll();
		map.put("Itens", listaItens);
		
		total = 0;
		for (int i = 0; i < listaItens.size(); i++) {
			total += listaItens.get(i).getSubtotal();
		}
		
		// Subtotal dos itens (sem a aplicação de descontos)
		map.put("Subtotal", total);
		
		// Aplicação do desconto progressivo
		if ((total >= 1000) && (total < 5000)) {
			total *= 0.95;
		}
		else if ((total >= 5000) && (total < 10000)) {
			total *= 0.93;
		}
		else {
			total *= 0.90;
		}
		
		// Aplicação do cupom, de maneira global ao valor do carrinho.
		total -= cupom.getValor();
		
		// Total dos itens com a aplicação de descontos.
		map.put("Total", total);		
		
		// Cupom aplicado
		map.put("Cupom", cupom.getValor());
		return map;
	}
	
	// Método para INSERIR um item no carrinho
	@PostMapping("/carrinho")
	public Carrinho insereProduto(@RequestBody Item item) {
		Carrinho carrinho = new Carrinho();
		Produto produto = produtoRepository.findById(item.getProdutoID());
		carrinho.setProdutoID(item.getProdutoID());
		carrinho.setNomeProduto(produto.getNome());
		carrinho.setQuantidade(item.getQuantidade());
		if (item.getQuantidade() < 10) {
			carrinho.setSubtotal(item.getQuantidade() * produto.getPreco());
		}
		else {
			// Desconto de 10% para quantidade de um mesmo produto acima de 10
			carrinho.setSubtotal(item.getQuantidade() * produto.getPreco() * 0.90);
		}
		return carrinhoRepository.save(carrinho);
	}
	
	// Método EDITAR os dados de um item no carrinho
	@PutMapping("/carrinho")
	public Carrinho editaItem(@RequestBody Carrinho carrinho) {
		Produto produto = produtoRepository.findById(carrinho.getProdutoID());
		carrinho.setNomeProduto(carrinho.getNomeProduto());
		carrinho.setQuantidade(carrinho.getQuantidade());
		if (carrinho.getQuantidade() < 10) {
			carrinho.setSubtotal(carrinho.getQuantidade() * produto.getPreco());
		}
		else {
			// Desconto de 10% para compras de 10 ou mais itens de um mesmo produto
			carrinho.setSubtotal(carrinho.getQuantidade() * produto.getPreco() * 0.90);
		}
		return carrinhoRepository.save(carrinho);
	}
	
	// Método para DELETAR um item do carrinho pelo ID
	@DeleteMapping("/carrinho/{id}")
	public void deletaItemById(@PathVariable(value="id") long id) {
		carrinhoRepository.deleteById(id);
	}
	
	// Método para INSERIR um cupom no carrinho
	@PostMapping("/carrinho/cupom")
	public void insereCupom(@RequestBody Cupom cupomInput) {
		// Os cupons não são cumulativos, devendo somente o de maior valor ser considerado.
		if (cupom.getValor() < cupomInput.getValor()) {
			cupom = cupomInput;
		}
		total -= cupom.getValor();
	}
	
	// Método para DELETAR um cupom do carrinho
	@DeleteMapping("/carrinho/cupom")
	public void deletaCupom() {
		cupom = new Cupom();
	}
}
