package org.serratec.h2.grupo2.DTO.funcionario;

import java.time.LocalDate;
import org.serratec.h2.grupo2.enuns.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioUpdateForFuncionarioDto {

	private String nome;
	private String cpf;
	private LocalDate dataDeNascimento;
	private Genero genero;
	private String telefone;
	private String email;
	private String senha;
	
}
