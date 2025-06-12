// Essa classe vai servir para implementar todas as funções da classe Produto, deixando funcional

package org.serratec.h2.grupo2.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.domain.Foto;
import org.serratec.h2.grupo2.domain.ItemPedido;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.mapper.ProdutoMapper;
import org.serratec.h2.grupo2.repository.CategoriaRepository;
import org.serratec.h2.grupo2.repository.FotoRepository;
import org.serratec.h2.grupo2.repository.ItemPedidoRepository;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


// Representa uma camada de serviço
@Service
public class ProdutoService {

	// Injetar a interface para procurar no banco de dados
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemRepository;

	// Injetar a interface para procurar no banco de dados
	@Autowired
	private FotoRepository fotoRepository;

	// Injetar a interface para procurar no banco de dados
	@Autowired
	private ProdutoMapper produtoMapper;

	// Injetar a interface para procurar no banco de dados
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private FileStorageService fileStorageService;

	// GET: Ler a lista de produtos
	// Mapper faz a conversão de Produto para ProdutoResponse
	// Chamar apenas service.listar no controller
	@Transactional(readOnly = true)
	public List<ProdutoResponseDTO> listar() {
		return produtoRepository.findAll().stream()
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	// GET: ID
	// Chamar apenas service.pesquisar
	// Mapper faz a conversão do Produto para ProdutoResponse
	// GET: Buscar por ID
	@Transactional(readOnly = true)
	public ProdutoResponseDTO pesquisar(Long id) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
		return toResponseDTO(produto);
	}

	// POST: Inserir
	// Mapper converte o JSON para Produto depois converte para ProdutoResponse
	// Chamar apenas service.inserir(produto)
	@Transactional
	public ProdutoResponseDTO inserir(ProdutoRequestDTO produtoDto, MultipartFile fotoFile) throws IOException {
		Produto produto = toEntity(produtoDto);
		produto = produtoRepository.save(produto); // Salva o produto para obter o ID

		// Salvar a foto
		if (fotoFile != null && !fotoFile.isEmpty()) {
			String filename = fileStorageService.storeFile(fotoFile);
			Foto foto = new Foto();
			foto.setNome(filename); // Guarda o nome único do arquivo no banco
			foto.setProduto(produto);
			fotoRepository.save(foto);
			produto.setFoto(foto); // Associa a foto ao produto
		}
		return toResponseDTO(produto);
	}

	// PUT: Atualizar
	// Mapper pega um JSON transforma em produto depois transforma em Produto Response
	@Transactional
	public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto, MultipartFile fotoFile) throws IOException {
		Produto produtoExistente = produtoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));

		// Atualizar campos do produto
		produtoExistente.setNome(dto.getNome());
		produtoExistente.setDescricao(dto.getDescricao());
		produtoExistente.setPreco(dto.getPreco());
		produtoExistente.setPrecoPromocional(dto.getPrecoPromocional());
		produtoExistente.setEstoque(dto.getEstoque());
		produtoExistente.setFabricante(dto.getFabricante());
		if (dto.getIdCategoria() != null) {
			Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + dto.getIdCategoria()));
			produtoExistente.setCategoria(categoria);
		}

		// Atualizar a foto
		if (fotoFile != null && !fotoFile.isEmpty()) {
			// Se já existe uma foto, apagar o arquivo antigo e o registro no banco
			if (produtoExistente.getFoto() != null) {
				fileStorageService.deleteFile(produtoExistente.getFoto().getNome());
				fotoRepository.delete(produtoExistente.getFoto());
				produtoExistente.setFoto(null); // Desassocia antes de salvar a nova
			}
			String filename = fileStorageService.storeFile(fotoFile);
			Foto novaFoto = new Foto();
			novaFoto.setNome(filename);
			novaFoto.setProduto(produtoExistente);
			fotoRepository.save(novaFoto);
			produtoExistente.setFoto(novaFoto);
		} else if (dto.getRemoveFoto() != null && dto.getRemoveFoto()) { // Se o DTO indicar para remover a foto
			if (produtoExistente.getFoto() != null) {
				fileStorageService.deleteFile(produtoExistente.getFoto().getNome());
				fotoRepository.delete(produtoExistente.getFoto());
				produtoExistente.setFoto(null);
			}
		}

		produtoExistente = produtoRepository.save(produtoExistente);
		return toResponseDTO(produtoExistente);
	}

	// Deletar um item
	// Chamar apenas service.remover(id)
	@Transactional
	public void remover(Long id) {
		Produto produto = produtoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
		
		// Remover o arquivo da foto antes de remover o registro no banco
		if (produto.getFoto() != null) {
			try {
				fileStorageService.deleteFile(produto.getFoto().getNome());
			} catch (IOException e) {
				// Logar o erro, mas não impedir a remoção do produto
				e.printStackTrace(); 
			}
		}
		produtoRepository.delete(produto);
	}

	// Método para listar itens em promoção
	@Transactional(readOnly = true)
	public List<ProdutoResponseDTO> listarPromocoes() {
		return produtoRepository.findAll().stream()
				.filter(p -> p.getPrecoPromocional() != null && p.getPrecoPromocional().compareTo(p.getPreco()) < 0)
				.map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Produto getProdutoById(Long id) {
		return produtoRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + id));
	}

	private Produto toEntity(ProdutoRequestDTO dto) {
		Produto produto = new Produto();
		produto.setNome(dto.getNome());
		produto.setDescricao(dto.getDescricao());
		produto.setPreco(dto.getPreco());
		produto.setPrecoPromocional(dto.getPrecoPromocional());
		produto.setEstoque(dto.getEstoque());
		produto.setFabricante(dto.getFabricante());
		// Se a categoria vier por ID no DTO
		if (dto.getIdCategoria() != null) {
			Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + dto.getIdCategoria()));
			produto.setCategoria(categoria);
		}
		return produto;
	}

	private ProdutoResponseDTO toResponseDTO(Produto produto) {
		ProdutoResponseDTO dto = new ProdutoResponseDTO();
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setDescricao(produto.getDescricao());
		dto.setPreco(produto.getPreco());
		dto.setPrecoPromocional(produto.getPrecoPromocional());
		dto.setEstoque(produto.getEstoque());
		dto.setFabricante(produto.getFabricante());
		dto.setAtivo(produto.getAtivo());
		dto.setDataCadastro(produto.getDataCadastro());
		dto.setDataAtualizacao(produto.getDataAtualizacao());
		if (produto.getCategoria() != null) {
			dto.setIdCategoria(produto.getCategoria().getId());
			dto.setNomeCategoria(produto.getCategoria().getNome());
		}
		if (produto.getFoto() != null && produto.getFoto().getNome() != null) {
			dto.setUrlFoto(produto.getFoto().getNome());
			dto.setUrlFoto("/produtos/" + produto.getId() + "/foto");
		}
		return dto;
	}
}

