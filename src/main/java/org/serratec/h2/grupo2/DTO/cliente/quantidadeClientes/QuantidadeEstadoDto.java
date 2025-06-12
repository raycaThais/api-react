package org.serratec.h2.grupo2.DTO.cliente.quantidadeClientes;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuantidadeEstadoDto {

	private String estado;
    private Long total;
    private List<QuantidadeCidadeDto> cidades;
}
