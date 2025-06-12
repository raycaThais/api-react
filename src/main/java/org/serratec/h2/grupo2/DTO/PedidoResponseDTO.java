package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.serratec.h2.grupo2.domain.Cliente;
import org.serratec.h2.grupo2.domain.ItemPedido;




/**
 * DTO para enviar os dados completos do pedido para o cliente.
 */
public class PedidoResponseDTO {

    private Long id;                      // ID do pedido
    private Cliente cliente;     // Dados resumidos do cliente
    private List<ItemPedido> itens;  // Lista dos itens do pedido
    private String status;                // Status do pedido (ex: PAGO, ENVIADO)
    private LocalDate dataCriacao;        // Data de criação do pedido
    private BigDecimal valorTotal;        // Valor total do pedido (somatório dos itens)
    private String codigoDesconto;
    private BigDecimal valorFinal;
    
    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public String getCodigoDesconto() {
        return codigoDesconto;
    }

    public void setCodigoDesconto(String codigoDesconto) {
        this.codigoDesconto = codigoDesconto;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    
}
