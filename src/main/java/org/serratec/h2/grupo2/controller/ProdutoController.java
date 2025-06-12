package org.serratec.h2.grupo2.controller;

import java.io.IOException;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Foto;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.serratec.h2.grupo2.service.FileStorageService;
import org.serratec.h2.grupo2.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

// Mapear esse EndPoint
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	// Injetar service
	@Autowired
	private ProdutoService service;
	
	// Injetar service
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	// GET: TODOS
	// ResponseEntity permite customizar o status HTTP
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

	
	// GET: ID
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> pesquisar(@PathVariable Long id) {
		// Chama o método pesquisar e caso exista o ID vai retornar o status HTTP
		// ok com ProdutoResponse no corpo da resposta
        try {
            return ResponseEntity.ok(service.pesquisar(id));
        // Retorna error 404
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<ProdutoResponseDTO> inserir(
		// Cria as chaves do Postman que serão usadas para inserir o produto
	    @RequestPart("produto") ProdutoRequestDTO produtoJson,
	    @RequestPart(name = "foto", required = false) MultipartFile fotoFile) throws IOException {

	    // Chama o método inserir no serviço, passando o MultipartFile
	    ProdutoResponseDTO response = service.inserir(produtoJson, fotoFile);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    
    // PUT: ATUALIZAR
    // Multipart/form-data: aceita file no Postman
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
        @PathVariable Long id,
        @Valid @RequestPart("produto") ProdutoRequestDTO dto,
        @RequestPart(name = "foto", required = false) MultipartFile fotoFile
        // Exceção que representa que pode ocorrer erros de entrado e saída
    ) throws IOException {
    	// Chama o método atualizar no serviço, passando o MultipartFile
        ProdutoResponseDTO atualizado = service.atualizar(id, dto, fotoFile);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE: REMOVER
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
	    try {
	    	// Chama o método remover
	        service.remover(id);
	        return ResponseEntity.noContent().build(); // Retorna 204 No Content ao remover com sucesso
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.notFound().build(); // Retorna 404 caso não encontre o recurso
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 para erros inesperados
	    }
	}
    
    // Apresentar a foto de determinado ID
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) throws IOException {
        Produto produto = service.getProdutoById(id);
        
        if (produto.getFoto() == null || produto.getFoto().getNome() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada para o produto ID: " + id);
        }

        byte[] imageData = fileStorageService.loadFileAsBytes(produto.getFoto().getNome());
        
        HttpHeaders headers = new HttpHeaders();
        String filename = produto.getFoto().getNome();
        String contentType = "application/octet-stream";
        if (filename.endsWith(".png")) {
            contentType = MediaType.IMAGE_PNG_VALUE;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        } else if (filename.endsWith(".gif")) {
            contentType = MediaType.IMAGE_GIF_VALUE;
        }
        
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(imageData.length);
        headers.setContentDisposition(ContentDisposition.inline().filename(filename).build());

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
		
    // GET: Itens em promoção
 	@GetMapping("/promocoes")
 	public ResponseEntity<List<ProdutoResponseDTO>> listarPromocoes() {
 	    List<ProdutoResponseDTO> promocoes = service.listarPromocoes();
 	    return ResponseEntity.ok(promocoes);
 	}
}