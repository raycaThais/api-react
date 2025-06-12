package org.serratec.h2.grupo2.DTO.cliente;

import java.time.LocalDate;

import org.serratec.h2.grupo2.domain.Endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto {

	private Long codigo;
	private String nome;
	private String cpf;
	private LocalDate dataDeNascimento;
	private String telefone;
	private String email;
	private Endereco endereco;
	
}
