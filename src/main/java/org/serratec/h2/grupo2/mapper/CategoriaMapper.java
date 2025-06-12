package org.serratec.h2.grupo2.mapper;


import org.serratec.h2.grupo2.DTO.CategoriaRequestDto;
import org.serratec.h2.grupo2.DTO.CategoriaResponseDto;
import org.serratec.h2.grupo2.DTO.CategoriaUpdateDto;
import org.serratec.h2.grupo2.domain.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public Categoria toEntity(CategoriaRequestDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        return categoria;
    }

    public Categoria toEntity(CategoriaUpdateDto dto, Categoria categoriaExistente) {
        if (dto.getNome() != null) {
            categoriaExistente.setNome(dto.getNome());
        }
        return categoriaExistente;
    }

    public CategoriaResponseDto toResponseDto(Categoria categoria) {
        return new CategoriaResponseDto(categoria.getId(), categoria.getNome());
    }
}