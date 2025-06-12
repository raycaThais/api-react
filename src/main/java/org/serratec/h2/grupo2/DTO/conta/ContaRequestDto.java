package org.serratec.h2.grupo2.DTO.conta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestDto {

	private String email;
	private String senha;
	
}
