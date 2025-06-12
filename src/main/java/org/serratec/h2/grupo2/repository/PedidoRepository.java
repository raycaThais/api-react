package org.serratec.h2.grupo2.repository;


import java.util.List;
import java.util.Optional;
import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.enuns.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    
	Optional<Pedido> findTopByClienteContaEmailAndStatus(String email, StatusPedido status);
    
	List<Pedido> findAllByClienteContaEmailAndStatus(String email, StatusPedido status);
	
	List<Pedido> findByStatus (StatusPedido status);
	
	List<Pedido> findByClienteId (Long id);

}


