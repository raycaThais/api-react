package org.serratec.h2.grupo2.security.userDetails;

import java.util.Collection;
import java.util.List;

import org.serratec.h2.grupo2.domain.Conta;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/***********************************************************************************
 * 📌 EXPLICAÇÃO SOBRE ESTA CLASSE:                                                *
 *                                                                                 *
 * ESTA CLASSE IMPLEMENTA A INTERFACE USERDETAILS, QUE FAZ PARTE DO                *
 * SPRING SECURITY. O MOTIVO DISSO É PERMITIR QUE O FRAMEWORK CONSIGA              *
 * RECONHECER E TRABALHAR COM O NOSSO MODELO DE USUÁRIO (`CONTA`)                  *
 * DE FORMA PADRONIZADA E SEGURA.                                                  *
 *                                                                                 *
 * A INTERFACE USERDETAILS DEFINE OS MÉTODOS ESSENCIAIS QUE O SPRING               *
 * PRECISA PARA AUTENTICAÇÃO E AUTORIZAÇÃO, COMO:                                  *
 *  - GETUSERNAME() -> PARA RECUPERAR O LOGIN (EX: E-MAIL);                        *
 *  - GETPASSWORD() -> PARA VERIFICAR A SENHA;                                     *
 *  - GETAUTHORITIES() -> PARA INDICAR O PAPEL (EX: ROLE_USER, ROLE_ADMIN);        *
 *  - ISENABLED(), ISACCOUNTNONLOCKED(), ETC → PARA SABER SE A CONTA ESTÁ ATIVA.   *
 *                                                                                 *
 * COMO A ENTIDADE `CONTA` NÃO IMPLEMENTA DIRETAMENTE ESSE PADRÃO,                 *
 * CRIAMOS ESSA CLASSE ADAPTADORA (`CONTAUSERDETAILS`) QUE TRANSFORMA              *
 * A `CONTA` EM ALGO QUE O SPRING SECURITY SABE LIDAR.                             *
 *                                                                                 *
 * 🔒 EM RESUMO: ESTA CLASSE É UMA PONTE ENTRE SUA ENTIDADE DE                     *
 * DOMÍNIO E O SISTEMA DE SEGURANÇA DO SPRING.                                     *
 ***********************************************************************************/


public class ContaUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Conta conta;
	
	public ContaUserDetails(Conta conta) {
	    this.conta = conta;
	}

	@Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
		//ESTE SIMPLEGRANTEDAUTHORITY É COMO UM MAPPER PRA CRIAR UMA AUTORIDADE VÁLIDA 
        return List.of(new SimpleGrantedAuthority("ROLE_" + conta.getNivelAcesso()));
    }

    @Override
    public String getPassword() {
        return conta.getSenha();
    }

    @Override
    public String getUsername() {
        return conta.getEmail();
    }

    @Override
    public boolean isEnabled() {
    	return conta.isAtivo();
    }
    
}
