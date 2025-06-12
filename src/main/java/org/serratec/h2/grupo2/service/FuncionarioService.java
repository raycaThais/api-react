package org.serratec.h2.grupo2.service;

import java.util.List;

import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioCompletUpdateForFuncionarioDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioCompletUpdateForGestorDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioRequestDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioResponseDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioUpdateForGestorDto;
import org.serratec.h2.grupo2.domain.Funcionario;
import org.serratec.h2.grupo2.enuns.Cargo;
import org.serratec.h2.grupo2.enuns.NivelAcesso;
import org.serratec.h2.grupo2.mapper.FuncionarioMapper;
import org.serratec.h2.grupo2.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;
	
	@Autowired
	private FuncionarioMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	//POST - CADASTRO DE FUNCIONÁRIO
	public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto request) {
		Funcionario funcionario = mapper.toFuncionario(request);
		funcionario.getConta().setSenha(encoder.encode(funcionario.getConta().getSenha()));
		return mapper.toResponse(repository.save(funcionario));
	}

	//PUT - FUNCIONARIO ATUALIZA O CADASTRO - ELE NÃO PODE MUDAR O PRÓPRIO CARGO
	public FuncionarioResponseDto atualizacaoCadastro(FuncionarioCompletUpdateForFuncionarioDto request) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Funcionario funcionario = repository.findByContaEmail(email);
		
		funcionario.setNome(request.getNome());
		funcionario.setCpf(request.getCpf());
		funcionario.setDataDeNascimento(request.getDataDeNascimento());
		funcionario.setGenero(request.getGenero());
		funcionario.setTelefone(request.getTelefone());
		funcionario.getConta().setEmail(request.getEmail());
		funcionario.getConta().setSenha(encoder.encode(request.getSenha()));
		
		return mapper.toResponse(repository.save(funcionario));
	}
	
	//FUNCIONARIO DE ALTO NÍVEL DE ACESSO PODE MUDAR O CARGO
	public FuncionarioResponseDto atualizacaoCompleta(Long id, FuncionarioUpdateForGestorDto request) {
	    Funcionario funcionario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));

	    funcionario.setNome(request.getNome());
	    funcionario.setCpf(request.getCpf());
	    funcionario.setDataDeNascimento(request.getDataDeNascimento());
	    funcionario.setCargo(request.getCargo());
	    funcionario.setGenero(request.getGenero());
	    funcionario.setTelefone(request.getTelefone());

	    funcionario.getConta().setEmail(request.getEmail());
	    funcionario.getConta().setSenha(encoder.encode(request.getSenha()));
	    funcionario.getConta().setNivelAcesso(Cargo.getNivelAcesso(request.getCargo()));

	    return mapper.toResponse(repository.save(funcionario));
	}
	
	//PATCH - ATUALIZAÇÃO PARCIAL DOS DADOS
	
	//ATUALIZAÇÃO PARCIAL FEITA PELO FUNCIONÁRIO
	public FuncionarioResponseDto atualizacaoParcial(FuncionarioCompletUpdateForFuncionarioDto request) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Funcionario funcionario = repository.findByContaEmail(email);
		
		if (request.getNome() != null && !request.getNome().isBlank()) {
	        funcionario.setNome(request.getNome());
	    }

	    if (request.getCpf() != null && !request.getCpf().isBlank()) {
	        if (!request.getCpf().matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
	            throw new IllegalArgumentException("CPF em formato inválido. Esperado: xxx.xxx.xxx-xx");
	        }
	        funcionario.setCpf(request.getCpf());
	    }

	    if (request.getDataDeNascimento() != null) {
	        funcionario.setDataDeNascimento(request.getDataDeNascimento());
	    }

	    if (request.getGenero() != null) {
	        funcionario.setGenero(request.getGenero());
	    }

	    if (request.getTelefone() != null && !request.getTelefone().isBlank()) {
	        if (!request.getTelefone().matches("^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$")) {
	            throw new IllegalArgumentException("Telefone em formato inválido. Esperado: (XX) 9XXXX-XXXX");
	        }
	        funcionario.setTelefone(request.getTelefone());
	    }

	    if (request.getEmail() != null && !request.getEmail().isBlank()) {
	        if (!request.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	            throw new IllegalArgumentException("Email inválido.");
	        }
	        funcionario.getConta().setEmail(request.getEmail());
	    }

	    if (request.getSenha() != null && !request.getSenha().isBlank()) {
	        if (!request.getSenha().matches("^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
	            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, número e caractere especial.");
	        }
	        funcionario.getConta().setSenha(encoder.encode(request.getSenha()));
	    }

		
		return mapper.toResponse(repository.save(funcionario));
	}
	
	//ATUALIZAÇÃO PARCIAL FEITA PELO GESTOR
	public FuncionarioResponseDto atualizacaoParcialGestor(Long id, FuncionarioCompletUpdateForGestorDto request) {
	    Funcionario funcionario = repository.findById(id)
	        .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));

	    if (request.getNome() != null && !request.getNome().isBlank()) {
	        funcionario.setNome(request.getNome());
	    }

	    if (request.getCpf() != null && !request.getCpf().isBlank()) {
	        if (!request.getCpf().matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
	            throw new IllegalArgumentException("CPF em formato inválido. Esperado: xxx.xxx.xxx-xx");
	        }
	        funcionario.setCpf(request.getCpf());
	    }

	    if (request.getDataDeNascimento() != null) {
	        funcionario.setDataDeNascimento(request.getDataDeNascimento());
	    }

	    if (request.getGenero() != null) {
	        funcionario.setGenero(request.getGenero());
	    }

	    if (request.getTelefone() != null && !request.getTelefone().isBlank()) {
	        if (!request.getTelefone().matches("^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$")) {
	            throw new IllegalArgumentException("Telefone em formato inválido. Esperado: (XX) 9XXXX-XXXX");
	        }
	        funcionario.setTelefone(request.getTelefone());
	    }

	    if (request.getCargo() != null) {
	        funcionario.setCargo(request.getCargo());
	        funcionario.getConta().setNivelAcesso(Cargo.getNivelAcesso(request.getCargo()));
	    }

	    if (request.getEmail() != null && !request.getEmail().isBlank()) {
	        if (!request.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	            throw new IllegalArgumentException("Email inválido.");
	        }
	        funcionario.getConta().setEmail(request.getEmail());
	    }

	    if (request.getSenha() != null && !request.getSenha().isBlank()) {
	        if (!request.getSenha().matches("^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
	            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, número e caractere especial.");
	        }
	        funcionario.getConta().setSenha(encoder.encode(request.getSenha()));
	    }

	    return mapper.toResponse(repository.save(funcionario));
	}
	
	//DESATIVAR CONTA DO FUNCIONÁRIO
	public FuncionarioResponseDto desativarConta(Long id) {
		Funcionario funcionario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
		
		if (funcionario.getConta().isAtivo()) {
	        funcionario.getConta().setAtivo(false);
	        repository.save(funcionario);
	    } else {throw new EntityNotFoundException("Conta do funcionário já está desativada.");}

	    return mapper.toResponse(funcionario);
	}
	
	//ATIVAR CONTA DO FUNCIONÁRIO
	public FuncionarioResponseDto ativarConta(Long id) {
		Funcionario funcionario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
		
		if (!funcionario.getConta().isAtivo()) {
	        funcionario.getConta().setAtivo(true);
	        repository.save(funcionario);
	    } else {throw new EntityNotFoundException("Conta do funcionário já está ativada.");}

	    return mapper.toResponse(funcionario);
	}
	
	//GET - LISTAR OS FUNCIONARIOS
	
	//LISTAR TODOS OS FUNCIONÁRIOS
	public List<FuncionarioResponseDto> listar() {
		List<Funcionario> listaFuncionarios = repository.findAll();
		return mapper.toListResponse(listaFuncionarios);
	}	
	
	//PELO NOME
	public List<FuncionarioResponseDto> listarPorNome(String nome) {
		List<Funcionario> listaFuncionarios = repository.findByNomeContainingIgnoreCase(nome);
		return mapper.toListResponse(listaFuncionarios);
	}
	
	//PELO CARGO
	public List<FuncionarioResponseDto> listarPeloCargo(Cargo cargo) {
		List<Funcionario> listaFuncionarios = repository.findByCargo(cargo);
		return mapper.toListResponse(listaFuncionarios);
	}
	
	//PELO ID
	public FuncionarioResponseDto buscarPorId(Long id) {
		Funcionario funcionario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
		return mapper.toResponse(funcionario);
	}
	
	//PELO NIVEL DE ACESSO
	public List<FuncionarioResponseDto> listarPorNivelDeAcesso(NivelAcesso acesso) {
		List<Funcionario> listaFuncionarios = repository.findByContaNivelAcesso(acesso);
		return mapper.toListResponse(listaFuncionarios);
	}
	
	//LISTAR FUNCIONÁRIOS SE ELES TIVEREM A CONTA ATIVA OU DESATIVADA
	public List<FuncionarioResponseDto> listarContaAtivaOuDesativada(Boolean ativo) {
		List<Funcionario> listaFuncionarios = repository.findByContaAtivo(ativo);
		return mapper.toListResponse(listaFuncionarios);
	}
	
	//DELETE PELO ID
	public void excluirFuncionario(Long id) {
		repository.deleteById(id);
	}
}
