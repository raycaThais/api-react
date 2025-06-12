package org.serratec.h2.grupo2.repository;

import java.util.Optional;

import org.serratec.h2.grupo2.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	Optional<Conta> findByEmail (String email);
	
	boolean existsByEmail (String email);
	
}
