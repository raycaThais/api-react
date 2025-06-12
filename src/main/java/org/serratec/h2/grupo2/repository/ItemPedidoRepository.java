package org.serratec.h2.grupo2.repository;

import java.util.Optional;

import org.serratec.h2.grupo2.domain.ItemPedido;
import org.serratec.h2.grupo2.enuns.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{

	Optional<ItemPedido> findByIdAndPedidoStatusAndPedidoClienteContaEmail (Long id, StatusPedido status, String email);
	
	Optional<ItemPedido> findByProdutoId (Long id);
	
	Optional<ItemPedido> findByPedidoIdAndProdutoId (Long idPedido, Long idItem);
}
