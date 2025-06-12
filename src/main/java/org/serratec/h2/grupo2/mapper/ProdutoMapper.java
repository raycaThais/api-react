package org.serratec.h2.grupo2.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.h2.grupo2.DTO.FotoResponseDTO;
import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProdutoMapper {

	@Autowired
	// Injeção do banco de dados da Categoria
	private CategoriaRepository categoriaRepository;
	
	public Produto toProduto(ProdutoRequestDTO request) {
		Produto produto = new Produto();

		produto.setNome(request.getNome());
		produto.setDescricao(request.getDescricao());
		Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
	                .orElseThrow(() -> 
	                new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
		produto.setCategoria(categoria);
		produto.setPreco(request.getPreco());
		produto.setPrecoPromocional(request.getPrecoPromocional());
		produto.setEstoque(request.getEstoque());
		produto.setFabricante(request.getFabricante());
		produto.setAtivo(true);
		produto.setDataCadastro(LocalDate.now());
		produto.setDataAtualizacao(LocalDate.now());
		return produto;
	}
	
	public ProdutoResponseDTO toResponse(Produto produto) {
		ProdutoResponseDTO response = new ProdutoResponseDTO();
		response.setId(produto.getId());
		response.setNome(produto.getNome());
		response.setDescricao(produto.getDescricao());
		
		if (produto.getCategoria() != null) {
			response.setIdCategoria(produto.getCategoria().getId());
			response.setNomeCategoria(produto.getCategoria().getNome());
		}
		
		response.setPreco(produto.getPreco());
		response.setPrecoPromocional(produto.getPrecoPromocional());
		response.setEstoque(produto.getEstoque());
		response.setFabricante(produto.getFabricante());
		response.setAtivo(produto.getAtivo());
		response.setDataCadastro(produto.getDataCadastro());
		response.setDataAtualizacao(produto.getDataAtualizacao());
		
		if (produto.getFoto() != null && produto.getFoto().getNome() != null) {
		    response.setUrlFoto("/produtos/" + produto.getId() + "/foto");
		}
		return response;
	}
	
	public List<ProdutoResponseDTO> toListResponse (List<Produto> listaProdutos) {
		List<ProdutoResponseDTO> listResponse = new ArrayList<> ();
			
		for(Produto p: listaProdutos) {
			listResponse.add(toResponse(p));}
			
		return listResponse;
	}
}