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

import com.produtos.apirest.models.Cupom;
import com.produtos.apirest.repository.CupomRepository;

@RestController
@RequestMapping(value="/api")
public class CupomResource {
	
	@Autowired
	CupomRepository cupomRepository;
	
	// Método para listar todos os cupons cadastrados no banco de dados
	@GetMapping("/cupons")
	public List<Cupom> listaCupons() {
		return cupomRepository.findAll();
	}
	
	// Método para retornar um cupom pelo ID 
	@GetMapping("/cupom/{id}")
	public Cupom carregaCupom(@PathVariable(value="id") long id) {
		return cupomRepository.findById(id);
	}
	
	// Método para salvar um novo cupom no banco de dados 
	@PostMapping("/cupom")
	public Cupom salvaCupom(@RequestBody Cupom cupom) {
		return cupomRepository.save(cupom);
	}
	
	// Método para deletar um cupom do banco de dados pelo ID 
	@DeleteMapping("/cupom/{id}")
	public void deletaCupomById(@PathVariable(value="id") long id) {
		cupomRepository.deleteById(id);
	}
	
	// Método para atualizar os dados de um cupom no banco de dados 
	@PutMapping("/cupom")
	public Cupom editaCupom(@RequestBody Cupom cupom) {
		return cupomRepository.save(cupom);
	}

}
