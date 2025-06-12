package org.serratec.h2.grupo2.repository;

import java.util.Optional;
import org.serratec.h2.grupo2.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNome(String nome);
    
    boolean existsByNomeIgnoreCase(String nome);
}