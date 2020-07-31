package com.produtos.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.produtos.apirest.models.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
	
}
