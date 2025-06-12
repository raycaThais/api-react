package org.serratec.h2.grupo2.exception;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErroResposta {
	private Integer status;
	private String titulo;
	private LocalDateTime dataHora;
	private List<String> erros;
	private String caminho;
}
