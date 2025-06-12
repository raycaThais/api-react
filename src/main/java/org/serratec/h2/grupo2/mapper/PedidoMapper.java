package org.serratec.h2.grupo2.mapper;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.h2.grupo2.DTO.pedido.ItemIndisponivel;
import org.serratec.h2.grupo2.DTO.pedido.ItemResponseDto;
import org.serratec.h2.grupo2.DTO.pedido.PedidoAndamentoResponseDto;
import org.serratec.h2.grupo2.DTO.pedido.PedidoFinalizadoResponseDto;
import org.serratec.h2.grupo2.domain.ItemPedido;
import org.serratec.h2.grupo2.domain.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
	
	public ItemResponseDto itemToResponse(ItemPedido item) {
		ItemResponseDto itemResponse = new ItemResponseDto();
		
		itemResponse.setCodigo(item.getId());
		itemResponse.setNome(item.getProduto().getNome());
		itemResponse.setPrecoTotal(item.getPrecoTotal());
		itemResponse.setPrecoUnitario(item.getPrecoUnitario());
		itemResponse.setQuantidade(item.getQuantidade());
		
		return itemResponse;
	}
	
	public List<ItemResponseDto>  toResponseItensList(List<ItemPedido> listItems) {
		List<ItemResponseDto> listResponse = new ArrayList<>();
		
		for(ItemPedido item : listItems) {
			listResponse.add(itemToResponse(item));
		}
		return listResponse;
	}
	
	public PedidoAndamentoResponseDto toResponse (Pedido pedido) {
		PedidoAndamentoResponseDto response = new PedidoAndamentoResponseDto();
		
		response.setCodigo(pedido.getId());
		response.setItens(toResponseItensList(pedido.getItens()));
		response.setStatusPedido(pedido.getStatus());
		response.setValorFrete(pedido.getValorFrete());
		response.setPrecoTotal(pedido.getPrecoTotal());
		
		return response;
	}
	
	public PedidoFinalizadoResponseDto toResponseFinalizado(Pedido pedido, List<ItemIndisponivel> itensIndisponiveis) {
		PedidoFinalizadoResponseDto response = new PedidoFinalizadoResponseDto();
		
		response.setCodigo(pedido.getId());
		response.setNome(pedido.getCliente().getNome());
		response.setItens(toResponseItensList(pedido.getItens()));
		response.setItensIndisponiveis(itensIndisponiveis);
		response.setDataDeFinalizacao(LocalDate.now());
		response.setStatus(pedido.getStatus());
		response.setEnderecoEntrega(pedido.getCliente().getEndereco());
		response.setValorFrete(pedido.getValorFrete());
		response.setPrecoTotal(pedido.getPrecoTotal());

		return response;
	}
	
	public List<PedidoAndamentoResponseDto> toListResponse(List<Pedido> listPedidos) {
		List<PedidoAndamentoResponseDto> responseList = new ArrayList<>();
		
		for(Pedido pedido: listPedidos) {
			PedidoAndamentoResponseDto response = new PedidoAndamentoResponseDto();
			response = toResponse(pedido);
			responseList.add(response);
		}
		
		return responseList;
	}
}

