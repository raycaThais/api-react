package org.serratec.h2.grupo2.DTO.funcionario;

import java.time.LocalDate;
import org.serratec.h2.grupo2.enuns.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioCompletUpdateForFuncionarioDto {

	@NotBlank
	private String nome;
	
	@NotBlank
	@Pattern (regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inválido, esperado xxx.xxx.xxx-xx")
	private String cpf;
	
	@NotNull (message = "A data de nascimento deve ser preenchido")
	private LocalDate dataDeNascimento;
	
	@NotNull (message = "O gênero deve ser preenchido")
	private Genero genero;
	
	@Pattern(regexp = "^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$", message = "Telefone inválido, formato esperado: (XX) 9XXXX-XXXX")
	private String telefone;
	
	@Email (message = "Email inválido")
	private String email;
	
	@Pattern (regexp = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$", message = "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, número e caractere especial.")
	private String senha;
}
