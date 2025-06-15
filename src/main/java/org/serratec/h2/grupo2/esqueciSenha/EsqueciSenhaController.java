package org.serratec.h2.grupo2.esqueciSenha;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/esqueciSenha")
public class EsqueciSenhaController {
	
	@Autowired
	EsqueciSenhaService esqueciService;
	
	@PostMapping("/solicitarRecuperacao")
	public ResponseEntity<String> solicitarRecuperacao(@Valid @RequestBody SolicitarRecuperacaoDTO solicitacao){
		esqueciService.solicitarRecuperacao(solicitacao);
		return ResponseEntity.ok("Código de recuperação enviado por e-mail");
	}
	
	@PostMapping("/redefinirSenha")
	public ResponseEntity<String> redefinirSenha(@Valid @RequestBody RedefinirSenhaDTO redefinirSenha){
		esqueciService.resetarSenha(redefinirSenha);
		return ResponseEntity.ok("Senha redefinida com sucesso.");
	}

}
