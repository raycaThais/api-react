package org.serratec.h2.grupo2.DTO.conta;

import org.serratec.h2.grupo2.enuns.NivelAcesso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContaUpdate {
	
	private String email;
	private String senha;
	private NivelAcesso nivelAcesso;
	private boolean ativo;
	
}
