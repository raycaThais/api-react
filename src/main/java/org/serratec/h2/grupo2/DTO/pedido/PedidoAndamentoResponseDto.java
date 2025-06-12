package org.serratec.h2.grupo2.DTO.pedido;

import java.math.BigDecimal;
import java.util.List;

import org.serratec.h2.grupo2.enuns.StatusPedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoAndamentoResponseDto {

	 private Long codigo; 
	 private List<ItemResponseDto> itens;
	 private StatusPedido statusPedido;
	 private BigDecimal valorFrete;
	 private BigDecimal precoTotal;
}

