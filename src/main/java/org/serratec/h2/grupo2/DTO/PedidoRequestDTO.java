package org.serratec.h2.grupo2.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.time.LocalDate;
import org.serratec.h2.grupo2.enuns.StatusPedido;

/**
 * DTO usado para receber os dados de criação ou edição de um pedido.
 */
public class PedidoRequestDTO {
	
	private LocalDate dataCriacao;
	private StatusPedido status;
	private Double valorFrete;
	
	public LocalDate getDataCriacao() {
	    return dataCriacao;
	}
	public void setDataCriacao(LocalDate dataCriacao) {
	    this.dataCriacao = dataCriacao;
	}
	
	public StatusPedido getStatus() {
	    return status;
	}

	public void setStatus(StatusPedido status) {
	    this.status = status;
	}

	public Double getValorFrete() {
	    return valorFrete;
	}

	public void setValorFrete(Double valorFrete) {
	    this.valorFrete = valorFrete;
	}

    // O ID do cliente que está fazendo o pedido é obrigatório
    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    // A lista de itens do pedido não pode estar vazia
    @NotEmpty(message = "O pedido deve conter ao menos um item")
    private List<ItemPedidoRequestDTO> itens;

    // Getters e Setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }
    
    private String codigoDesconto; // pode ser null ou vazio se o cliente não enviar

    public String getCodigoDesconto() {
        return codigoDesconto;
    }

    public void setCodigoDesconto(String codigoDesconto) {
        this.codigoDesconto = codigoDesconto;
    }
    
    
}