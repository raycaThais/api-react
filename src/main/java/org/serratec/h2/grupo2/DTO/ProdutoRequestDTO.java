package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.serratec.h2.grupo2.domain.Foto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {

	@NotBlank (message = "O nome deve ser preenchido")
	private String nome;
	
	@Size (min = 3, max = 255, message = "O nome do produto deve ter entre 3 e 255 caracteres")
	@NotBlank (message = "O descrição deve ser preenchido")
	private String descricao;
	
	@NotNull(message = "O ID da categoria deve ser informado")

	private Long idCategoria;
	
	@NotNull (message = "O preço maior que zero")
	private BigDecimal preco;
	
	private BigDecimal precoPromocional;
	
	private Integer estoque;
	
	private String fabricante;
	
	private Boolean ativo;
	
	private LocalDate dataCadastro;
	
	private LocalDate dataAtualizacao;

	private Boolean removeFoto;

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

	public Boolean getRemoveFoto() {
		return removeFoto;
	}

	public void setRemoveFoto(Boolean removeFoto) {
		this.removeFoto = removeFoto;
	}

}