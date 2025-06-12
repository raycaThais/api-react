package org.serratec.h2.grupo2.DTO;

import jakarta.validation.constraints.NotNull;

/**
 * DTO usado para atualizar apenas o status do pedido.
 */
public class PedidoStatusUpdateDTO {

    @NotNull(message = "O status é obrigatório")
    private String status;

    // Getter
    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }

}