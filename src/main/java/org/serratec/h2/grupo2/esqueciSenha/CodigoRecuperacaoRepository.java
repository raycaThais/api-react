package org.serratec.h2.grupo2.esqueciSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodigoRecuperacaoRepository extends JpaRepository<CodigoRecuperacao, Long>{

	CodigoRecuperacao findByCodigo(String codigo);
}
