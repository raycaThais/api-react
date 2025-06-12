package org.serratec.h2.grupo2.service;


import java.util.List;
import java.util.stream.Collectors;
import org.serratec.h2.grupo2.DTO.CategoriaRequestDto;
import org.serratec.h2.grupo2.DTO.CategoriaResponseDto;
import org.serratec.h2.grupo2.DTO.CategoriaUpdateDto;
import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.mapper.CategoriaMapper;
import org.serratec.h2.grupo2.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaResponseDto> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDto criar(CategoriaRequestDto dto) {
        if (categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RuntimeException("Categoria já existente com este nome.");
        }

        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria salva = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDto(salva);
    }

    public CategoriaResponseDto atualizar(Long id, CategoriaUpdateDto dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

        Categoria atualizada = categoriaMapper.toEntity(dto, categoria);
        Categoria salva = categoriaRepository.save(atualizada);
        return categoriaMapper.toResponseDto(salva);
    }

    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada.");
        }
        categoriaRepository.deleteById(id);
    }
}