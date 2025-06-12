package org.serratec.h2.grupo2.DTO.cliente.quantidadeClientes;

import org.serratec.h2.grupo2.DTO.cliente.ClienteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MensagemComClienteResponseDto {
    private String mensagem;
    private ClienteResponseDto cliente;
}
