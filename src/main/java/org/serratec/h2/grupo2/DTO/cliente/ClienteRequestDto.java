package org.serratec.h2.grupo2.DTO.cliente;

import java.time.LocalDate;
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
public class ClienteRequestDto {

	@NotBlank
	private String nome;
	
	@NotBlank
	@Pattern (regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inválido, esperado xxx.xxx.xxx-xx")
	private String cpf;
	
	@NotNull (message = "A data de nascimento deve ser preenchido")
	private LocalDate dataDeNascimento;
	
	@Pattern(regexp = "^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$", message = "Telefone inválido, formato esperado: (XX) 9XXXX-XXXX")
	private String telefone;
	
	@Pattern(regexp = "^\\d{5}-\\d{3}$",message = "CEP inválido. Use: 99999-999")
	private String cep;

	@Pattern(regexp = "^[1-9]\\d*$|(?i)^s/?n$", message = "Número residencial deve ser um número positivo ou 'S/N'")
	private String numero;
	
	//DADOS DA CONTA
	@Email (message = "Email inválido")
	private String email;
	
	@Pattern (regexp = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$", message = "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, número e caractere especial.")
	private String senha;
}
