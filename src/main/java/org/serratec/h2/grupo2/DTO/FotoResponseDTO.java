package org.serratec.h2.grupo2.DTO;

public class FotoResponseDTO {
    private String nome;
    // Sem o campo de dados porque está dando erro no Postman, já que os dados também estavam
    // retornando ao usuário
    
	public FotoResponseDTO(String nome) {
		super();
		this.nome = nome;
	}

	public FotoResponseDTO() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
    
    
}