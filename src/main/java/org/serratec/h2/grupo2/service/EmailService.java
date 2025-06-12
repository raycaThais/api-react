package org.serratec.h2.grupo2.service;

import org.serratec.h2.grupo2.DTO.pedido.ItemIndisponivel;
import org.serratec.h2.grupo2.DTO.pedido.ItemResponseDto;
import org.serratec.h2.grupo2.DTO.pedido.PedidoFinalizadoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender mailSender;
	
	public void emailPraAtivacaoDaConta(String email, String nomeUsuario, String token) {
	    String link = "http://localhost:8080/cliente/ativarConta?email=" + email + "&token=" + token;

	    String corpo = String.format(
	        """
	        Olá %s, tudo bem?

	        Seja muito bem-vindo(a) ao nosso sistema! 🎉

	        Para concluir seu cadastro e ativar sua conta, por favor clique no link abaixo:

	        ➤ %s

	        Caso não consiga clicar diretamente no link, você também pode copiar e colar o endereço no seu navegador.

	        Se preferir ativar manualmente, acesse:
	        ➤ http://localhost:8080/cliente/ativarConta/seuemail/seuToken
	        e utilize este código de ativação:%s

	        Se você não realizou esse cadastro, pode ignorar este e-mail com segurança.

	        Atenciosamente,
	        Equipe de Suporte
	        """, nomeUsuario, link, token
	    );

	    SimpleMailMessage mensagem = new SimpleMailMessage();
	    mensagem.setTo(email);
	    mensagem.setSubject("Ativação da Conta - Confirme seu cadastro");
	    mensagem.setText(corpo);
	    mensagem.setFrom("ecomerce.grupo2@gmail.com");

	    mailSender.send(mensagem);
	}
	
	public void emailContaCriadaComSucesso(String email, String nomeUsuario) {
	    String corpo = String.format(
	        """
	        Olá %s! 💖

	        Sua conta foi criada com sucesso e já está prontinha para ser usada! ✨

	        Estamos muito felizes em ter você com a gente. 🎉🎉🎉

	        Agora é só aproveitar todos os recursos disponíveis na plataforma. Se tiver qualquer dúvida, estaremos por aqui para ajudar. 😊

	        Seja bem-vindo(a) de coração! 💫

	        Com carinho,  
	        Equipe de Suporte 🤗
	        """, nomeUsuario
	    );

	    SimpleMailMessage mensagem = new SimpleMailMessage();
	    mensagem.setTo(email);
	    mensagem.setSubject("Conta criada com sucesso! 🥳");
	    mensagem.setText(corpo);
	    mensagem.setFrom("ecomerce.grupo2@gmail.com");

	    mailSender.send(mensagem);
	}
	
	public void emailConfirmacaoDeCompra(String email, PedidoFinalizadoResponseDto pedido) {
	    StringBuilder corpo = new StringBuilder();

	    corpo.append(String.format("""
	        Olá %s! ✨

	        Parabéns pela sua compra! 🛍️ Seu pedido foi finalizado com sucesso em %s.

	        Aqui estão os detalhes do seu pedido:
	        
	        📦 Itens Comprados:
	        """, pedido.getNome(), pedido.getDataDeFinalizacao()));

	    for (ItemResponseDto item : pedido.getItens()) {
	        corpo.append(String.format("- %s (Qtd: %d) - R$ %.2f\n", 
	            item.getNome(), item.getQuantidade(), item.getPrecoTotal().doubleValue()));
	    }

	    if (pedido.getItensIndisponiveis() != null && !pedido.getItensIndisponiveis().isEmpty()) {
	        corpo.append("\n⚠️ Itens Indisponíveis (não incluídos no pedido):\n");
	        for (ItemIndisponivel item : pedido.getItensIndisponiveis()) {
	            corpo.append(String.format("- %s (Faltando: %d unidades)\n", 
	                item.getNome(), item.getQuantidadeFaltante()));
	        }
	    }

	    corpo.append(String.format("""

	        📍 Endereço de Entrega:
	        Rua: %s, Nº: %s
	        Bairro: %s
	        Cidade: %s - %s
	        CEP: %s

	        💰 Valor do Frete: R$ %.2f
	        💳 Total do Pedido: R$ %.2f

	        Status do Pedido: %s

	        Em breve você receberá atualizações sobre o envio! 📦🚚

	        Qualquer dúvida, estamos aqui para ajudar!  
	        Agradecemos a sua confiança e preferência. 💖

	        Com carinho,  
	        Equipe de Suporte ✉️
	        """,
	        pedido.getEnderecoEntrega().getRua(),
	        pedido.getEnderecoEntrega().getNumero(),
	        pedido.getEnderecoEntrega().getBairro(),
	        pedido.getEnderecoEntrega().getCidade(),
	        pedido.getEnderecoEntrega().getEstado(),
	        pedido.getEnderecoEntrega().getCep(),
	        pedido.getValorFrete().doubleValue(),
	        pedido.getPrecoTotal().doubleValue(),
	        pedido.getStatus().name().replace("_", " ")
	    ));

	    SimpleMailMessage mensagem = new SimpleMailMessage();
	    mensagem.setTo(email);
	    mensagem.setSubject("🎉 Pedido Finalizado com Sucesso!");
	    mensagem.setText(corpo.toString());
	    mensagem.setFrom("ecomerce.grupo2@gmail.com");

	    mailSender.send(mensagem);
	}
	
	public void emailPedidoCanceladoEmTransporte(String email, PedidoFinalizadoResponseDto pedido) {
	    StringBuilder corpo = new StringBuilder();

	    corpo.append(String.format("""
	        Olá %s! 😕

	        Recebemos sua solicitação de cancelamento de pedido, e confirmamos que o processo foi concluído com sucesso.

	        Sabemos que o seu pedido já estava em rota de envio, então entendemos que essa decisão foi importante pra você. Esperamos ter a chance de te atender novamente em breve. 💖

	        Aqui estão os detalhes do pedido cancelado:
	        
	        📆 Data do Pedido: %s

	        📦 Itens Cancelados:
	        """, pedido.getNome(), pedido.getDataDeFinalizacao()));

	    for (ItemResponseDto item : pedido.getItens()) {
	        corpo.append(String.format("- %s (Qtd: %d) - R$ %.2f\n", 
	            item.getNome(), item.getQuantidade(), item.getPrecoTotal().doubleValue()));
	    }

	    corpo.append(String.format("""

	        📍 Endereço de Entrega (cancelado):
	        Rua: %s, Nº: %s
	        Bairro: %s
	        Cidade: %s - %s
	        CEP: %s

	        💰 Valor do Frete: R$ %.2f
	        💳 Total do Pedido: R$ %.2f

	        Status Atual do Pedido: %s ❌

	        Caso esse cancelamento tenha sido um engano, entre em contato conosco o mais rápido possível.

	        Agradecemos pela sua compreensão e estamos sempre aqui se precisar de ajuda! 🤗

	        Com carinho,  
	        Equipe de Suporte ✉️
	        """,
	        pedido.getEnderecoEntrega().getRua(),
	        pedido.getEnderecoEntrega().getNumero(),
	        pedido.getEnderecoEntrega().getBairro(),
	        pedido.getEnderecoEntrega().getCidade(),
	        pedido.getEnderecoEntrega().getEstado(),
	        pedido.getEnderecoEntrega().getCep(),
	        pedido.getValorFrete().doubleValue(),
	        pedido.getPrecoTotal().doubleValue(),
	        pedido.getStatus().name().replace("_", " ")
	    ));

	    SimpleMailMessage mensagem = new SimpleMailMessage();
	    mensagem.setTo(email);
	    mensagem.setSubject("Cancelamento de Pedido Confirmado 😕");
	    mensagem.setText(corpo.toString());
	    mensagem.setFrom("ecomerce.grupo2@gmail.com");

	    mailSender.send(mensagem);
	}
	
}
