package org.serratec.h2.grupo2.DTO.pedido;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {

	private Long codigo;
	private String nome;
	private BigDecimal precoUnitario;
	private Integer quantidade;
	private BigDecimal precoTotal;
}
