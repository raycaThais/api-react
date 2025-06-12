package org.serratec.h2.grupo2.DTO.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.serratec.h2.grupo2.domain.Endereco;
import org.serratec.h2.grupo2.enuns.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoFinalizadoResponseDto {

	private Long codigo;
	private String nome;
	private List<ItemResponseDto> itens;
	private List<ItemIndisponivel> itensIndisponiveis;
	private LocalDate dataDeFinalizacao;
	private StatusPedido status;
	private Endereco enderecoEntrega;
	private BigDecimal valorFrete;
	private BigDecimal precoTotal;
	
}
