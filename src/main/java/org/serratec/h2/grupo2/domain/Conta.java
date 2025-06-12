package org.serratec.h2.grupo2.domain;

import org.serratec.h2.grupo2.enuns.NivelAcesso;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	 @Column(unique = true)
	private String email;
	
	private String senha;

	@Enumerated(EnumType.STRING)
	private NivelAcesso nivelAcesso;
	
	private boolean ativo;
}
