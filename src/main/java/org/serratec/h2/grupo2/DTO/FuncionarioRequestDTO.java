package org.serratec.h2.grupo2.DTO;

import java.time.LocalDate;

import org.serratec.h2.grupo2.domain.Conta;
import org.serratec.h2.grupo2.enuns.Genero;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class FuncionarioRequestDTO {

	@NotNull(message = "O nome não pode estar em branco")
	private String nome;
	
	@NotNull(message = "O CPF não pode estar em branco")
	private String cpf;
	
	@Past
	@NotNull(message = "O campo não pode estar em branco")
	private LocalDate dataDeNascimento;
	
	@NotNull(message = "O genero não pode estar em branco")
	private Genero genero;
	
	@NotNull(message = "O telefone não pode estar em branco")
	private String telefone;
	
	@NotNull(message = "A conta não pode estar em branco")
	private Conta conta;

	public FuncionarioRequestDTO
	       (@NotNull(message = "O nome não pode estar em branco") String nome,
			@NotNull(message = "O CPF não pode estar em branco") String cpf,
			@Past @NotNull(message = "O campo não pode estar em branco") LocalDate dataDeNascimento,
			@NotNull(message = "O genero não pode estar em branco") Genero genero,
			@NotNull(message = "O telefone não pode estar em branco") String telefone,
			@NotNull(message = "A conta não pode estar em branco") Conta conta) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataDeNascimento = dataDeNascimento;
		this.genero = genero;
		this.telefone = telefone;
		this.conta = conta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	
	
}
