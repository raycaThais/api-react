package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProdutoResponseDTO {
	private Long id;
	private String nome;
	private String descricao;
	private Long idCategoria;
	private String nomeCategoria;
	private BigDecimal preco;
	private BigDecimal precoPromocional;
	private Integer estoque;
	private String fabricante;
	private Boolean ativo;
	private LocalDate dataCadastro;
	private LocalDate dataAtualizacao;
	private String urlFoto;

	public ProdutoResponseDTO(Long id, String nome, String descricao, Long idCategoria, String nomeCategoria,
			BigDecimal preco, BigDecimal precoPromocional, Integer estoque, String fabricante, Boolean ativo, LocalDate dataCadastro,
			LocalDate dataAtualizacao, String urlFoto) {

		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.idCategoria = idCategoria;
		this.nomeCategoria = nomeCategoria;
		this.preco = preco;
		this.precoPromocional = precoPromocional;
		this.estoque = estoque;
		this.fabricante = fabricante;
		this.ativo = ativo;
		this.dataCadastro = dataCadastro;
		this.dataAtualizacao = dataAtualizacao;
		this.urlFoto = urlFoto;
	}
	public ProdutoResponseDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public BigDecimal getPrecoPromocional() {
		return precoPromocional;
	}

	public void setPrecoPromocional(BigDecimal precoPromocional) {
		this.precoPromocional = precoPromocional;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer integer) {
		this.estoque = integer;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
}
