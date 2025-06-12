package org.serratec.h2.grupo2.DTO;

import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa um item do pedido dentro do PedidoRequestDTO.
 */
public class ItemPedidoRequestDTO {

    // O ID do produto do item do pedido é obrigatório
    @NotNull(message = "O ID do produto é obrigatório")
    private Long produtoId;

    // A quantidade do produto para esse item é obrigatória
    @NotNull(message = "A quantidade é obrigatória")
    private Integer quantidade;

    // Getters e Setters

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}