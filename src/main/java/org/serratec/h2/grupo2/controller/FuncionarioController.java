package org.serratec.h2.grupo2.controller;

import java.util.List;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioCompletUpdateForFuncionarioDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioCompletUpdateForGestorDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioRequestDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioResponseDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioUpdateForGestorDto;
import org.serratec.h2.grupo2.enuns.Cargo;
import org.serratec.h2.grupo2.enuns.NivelAcesso;
import org.serratec.h2.grupo2.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService service;

	@PostMapping("/cadastro")
	public FuncionarioResponseDto cadastrarFuncionario(@Valid @RequestBody FuncionarioRequestDto request) {
		return service.cadastrarFuncionario(request);
	}
	
	@PutMapping("/atualizarCadastro")
	public FuncionarioResponseDto atualizacaoCadastro(@Valid @RequestBody FuncionarioCompletUpdateForFuncionarioDto request) {
		return service.atualizacaoCadastro(request);
	}
	
	@PutMapping("/atualizarFuncionario/{id}")
	public FuncionarioResponseDto atualizacaoCompleta(@PathVariable Long id, @Valid @RequestBody FuncionarioUpdateForGestorDto request) {
		return service.atualizacaoCompleta(id, request);
	}
	
	@PatchMapping("/atualizacaoParcial")
	public FuncionarioResponseDto atualizacaoParcial(@RequestBody FuncionarioCompletUpdateForFuncionarioDto request) {
		return service.atualizacaoParcial(request);
	}
	
	@PatchMapping("/atualizacaoParcialGestor/{id}")
	public FuncionarioResponseDto atualizacaoParcialGestor(@PathVariable Long id, @RequestBody FuncionarioCompletUpdateForGestorDto request) {
		return service.atualizacaoParcialGestor(id, request);
	}
	
	@PatchMapping("/desativarConta/{id}")
	public FuncionarioResponseDto desativarConta(@PathVariable Long id) {
		return service.desativarConta(id);
	}
	
	@PatchMapping("/ativarConta/{id}")
	public FuncionarioResponseDto ativarConta(@PathVariable Long id) {
		return service.ativarConta(id);
	}
	
	@GetMapping("/listarFuncionarios")
	public List<FuncionarioResponseDto> listar() {
		return service.listar();
	}
	
	@GetMapping("/buscarPorId/{id}")
	public FuncionarioResponseDto buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping("/listarPorNome/{nome}")
	public List<FuncionarioResponseDto> listarPorNome(@PathVariable String nome) {
		return service.listarPorNome(nome);
	}
	
	@GetMapping("/listarPeloCargo/{cargo}")
	public List<FuncionarioResponseDto> listarPeloCargo(@PathVariable Cargo cargo) {
		return service.listarPeloCargo(cargo);
	}
	
	@GetMapping("/listarPeloNivelAcesso/{acesso}")
	public List<FuncionarioResponseDto> listarPorNivelDeAcesso(@PathVariable NivelAcesso acesso) {
		return service.listarPorNivelDeAcesso(acesso);
	}
	
	@GetMapping("/listarContasAtivas")
	public List<FuncionarioResponseDto> listarContasAtivas() {
		return service.listarContaAtivaOuDesativada(true);
	}
	
	@GetMapping("/listarContasDesativadas")
	public List<FuncionarioResponseDto> listarContasDesativadas() {
		return service.listarContaAtivaOuDesativada(false);
	}
	
	@DeleteMapping("/deletarFuncionario/{id}")
	public void deletarFuncionario (@PathVariable Long id) {
		service.excluirFuncionario(id);
	}
}
