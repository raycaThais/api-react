package org.serratec.h2.grupo2.repository;

import org.serratec.h2.grupo2.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
