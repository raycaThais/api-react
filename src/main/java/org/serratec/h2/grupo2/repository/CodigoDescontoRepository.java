package org.serratec.h2.grupo2.repository;

import java.util.Optional;

import org.serratec.h2.grupo2.domain.CodigoDesconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodigoDescontoRepository extends JpaRepository<CodigoDesconto, Long> {
    
    Optional<CodigoDesconto> findByCodigo(String codigo); // Busca código pelo texto exato
}
