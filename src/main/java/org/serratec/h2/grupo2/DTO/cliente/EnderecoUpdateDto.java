package org.serratec.h2.grupo2.DTO.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoUpdateDto {

	 @NotBlank(message = "Rua não pode estar em branco")
	 @Size(min = 2, max = 100, message = "Rua deve ter entre 2 e 100 caracteres")
	 private String rua;

	 @Pattern(regexp = "^[1-9]\\d*$|(?i)^s/?n$", message = "Número residencial deve ser um número positivo ou 'S/N'")
	 private String numero;

	 @NotBlank(message = "Bairro não pode estar em branco")
	 @Size(min = 2, max = 50, message = "Bairro deve ter entre 2 e 50 caracteres")
	 private String bairro;

	 @NotBlank(message = "Cidade não pode estar em branco")
	 @Size(min = 2, max = 50, message = "Cidade deve ter entre 2 e 50 caracteres")
	 private String cidade;

	 @NotBlank(message = "Estado não pode estar em branco")
	 @Size(min = 2, max = 2, message = "Estado deve ser a sigla de 2 letras (ex: RJ, SP)")
	 private String estado;

	 @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido. Use: 99999-999")
	 private String cep;
}
