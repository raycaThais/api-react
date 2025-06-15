package org.serratec.h2.grupo2.esqueciSenha;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.serratec.h2.grupo2.domain.Conta;
import org.serratec.h2.grupo2.repository.ContaRepository;
import org.serratec.h2.grupo2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EsqueciSenhaService {

	  @Autowired
	    CodigoRecuperacaoRepository codigoRecuperacaoRepository;
	    
	    @Autowired
	    ContaRepository contaRepository;
	    
	    @Autowired
	    EmailService enviarEmail;
	    
	    @Autowired //
	    PasswordEncoder encoder;
	    
	    public void solicitarRecuperacao(SolicitarRecuperacaoDTO recuperacaoEmail) {
	    Optional<Conta> contaOpt = contaRepository.findByEmail(recuperacaoEmail.getEmail());
	    if (contaOpt.isEmpty()) {
	    	throw new RuntimeException();
	    }
	    CodigoRecuperacao codigoRecuperacao = new CodigoRecuperacao();
	    String codigo = UUID.randomUUID().toString().substring(0, 8);

		LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(1);


		codigoRecuperacao.setCodigo(codigo);
		codigoRecuperacao.setDataExpiracao(dataExpiracao);
		codigoRecuperacao.setConta(contaOpt.get());
		codigoRecuperacaoRepository.save(codigoRecuperacao);
		
		String email = contaOpt.get().getEmail();
		
		enviarEmail.sendCodigoRecuperacao(email, codigo);	 
	    }
	    
	    public void resetarSenha(RedefinirSenhaDTO redefinirSenha) {
			String codigoInformado = redefinirSenha.getCodigo();
			String novaSenha = redefinirSenha.getNovaSenha();
			
			CodigoRecuperacao codigo = codigoRecuperacaoRepository.findByCodigo(codigoInformado);
			if(codigo == null) {
				throw new RuntimeException();
			}
			if (codigo.getDataExpiracao().isBefore(LocalDateTime.now())){
				throw new RuntimeException();
			}
			Conta conta = codigo.getConta();
			conta.setSenha(encoder.encode(novaSenha));
			
			contaRepository.save(conta);
			codigoRecuperacaoRepository.delete(codigo);

		}
	    
	   
	   
}
