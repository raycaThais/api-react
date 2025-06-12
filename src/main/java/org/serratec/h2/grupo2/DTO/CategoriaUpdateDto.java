package org.serratec.h2.grupo2.DTO;

import jakarta.validation.constraints.Size;

public class CategoriaUpdateDto {
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    private String nome;

    public CategoriaUpdateDto() {}

    public CategoriaUpdateDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}