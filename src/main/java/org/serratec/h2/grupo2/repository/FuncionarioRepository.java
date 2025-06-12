package org.serratec.h2.grupo2.repository;

import java.util.List;

import org.serratec.h2.grupo2.domain.Funcionario;
import org.serratec.h2.grupo2.enuns.Cargo;
import org.serratec.h2.grupo2.enuns.NivelAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Funcionario findByContaEmail (String email);
	
	List<Funcionario> findByNomeContainingIgnoreCase (String nome);
	
	List<Funcionario> findByContaNivelAcesso (NivelAcesso acesso);
	
	List<Funcionario> findByCargo (Cargo cargo);
	
	List<Funcionario> findByContaAtivo (Boolean ativo);

}
