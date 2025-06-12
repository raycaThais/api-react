package org.serratec.h2.grupo2.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome", nullable=false, length=50)
	private String nome;
	
	@Column(name="descricao", nullable=false, length=255)
	private String descricao;

    @NotNull(message = "Categoria é obrigatória")
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;
	
	@DecimalMin(value="0", message="O preço não pode ser menor que R$ {value}.00")
	private BigDecimal preco;
	
	@DecimalMin(value="0", message="O preço não pode ser menor que R$ {value}.00")
	private BigDecimal precoPromocional;
	
	@DecimalMin(value="0", message="O estoque não pode ser negativo")
	private Integer estoque;
	
	@Column(name="fabricante", nullable=false, length=50)
	private String fabricante;
	
	private Boolean ativo;
	
	private LocalDate dataCadastro;
	
	private LocalDate dataAtualizacao;

	// Relação entre foto e produto
	// OrphanRemoval faz a foto ser deletada junto com o produto
	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
	private Foto foto;
	
	@OneToMany(mappedBy = "produto")
	private List<ItemPedido> itens = new ArrayList<>();

	@PrePersist
	private void oncreate() {
		this.ativo = true;
		this.dataCadastro = LocalDate.now();
		this.dataAtualizacao = LocalDate.now();
	}

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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
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

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

}
