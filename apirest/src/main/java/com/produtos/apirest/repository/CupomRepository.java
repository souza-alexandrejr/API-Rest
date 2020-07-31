package com.produtos.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.produtos.apirest.models.Cupom;

public interface CupomRepository extends JpaRepository<Cupom, Long>{

	Cupom findById(long id);
}
