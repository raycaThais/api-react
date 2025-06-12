package org.serratec.h2.grupo2.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.h2.grupo2.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Optional<Cliente> findByContaEmail (String email);
	
	List<Cliente> findByNomeContainingIgnoreCase (String nome);
	
	List<Cliente> findByEnderecoCidadeContainingIgnoreCase (String cidade);
	
	List<Cliente> findByEnderecoEstadoContainingIgnoreCase (String estado);
	
	long countByEnderecoCidadeContainingIgnoreCase (String cidade);
	

	@Query("SELECT c.endereco.estado, c.endereco.cidade, COUNT(c) " +
		       "FROM Cliente c " +
		       "GROUP BY c.endereco.estado, c.endereco.cidade " +
		       "ORDER BY c.endereco.estado, c.endereco.cidade")
	List<Object[]> buscarQuantidadePorEstadoECidade();
}

