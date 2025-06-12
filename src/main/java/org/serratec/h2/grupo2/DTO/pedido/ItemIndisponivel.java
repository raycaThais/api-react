package org.serratec.h2.grupo2.DTO.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemIndisponivel {

	private String nome;
	private Integer quantidadeFaltante;
	
}
