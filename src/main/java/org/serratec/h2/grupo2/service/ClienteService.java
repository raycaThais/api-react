package org.serratec.h2.grupo2.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.serratec.h2.grupo2.DTO.cliente.ClienteRequestDto;
import org.serratec.h2.grupo2.DTO.cliente.ClienteResponseDto;
import org.serratec.h2.grupo2.DTO.cliente.ClienteUpdateDto;
import org.serratec.h2.grupo2.DTO.cliente.EnderecoUpdateDto;
import org.serratec.h2.grupo2.DTO.cliente.quantidadeClientes.MensagemComClienteResponseDto;
import org.serratec.h2.grupo2.DTO.cliente.quantidadeClientes.QuantidadeCidadeDto;
import org.serratec.h2.grupo2.DTO.cliente.quantidadeClientes.QuantidadeEstadoDto;
import org.serratec.h2.grupo2.domain.Cliente;
import org.serratec.h2.grupo2.domain.Conta;
import org.serratec.h2.grupo2.domain.Endereco;
import org.serratec.h2.grupo2.enuns.NivelAcesso;
import org.serratec.h2.grupo2.mapper.ClienteMapper;
import org.serratec.h2.grupo2.repository.ClienteRepository;
import org.serratec.h2.grupo2.security.tokenAcesso.TokenAtivacaoConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ClienteMapper mapper;
	
	@Autowired
	private TokenAtivacaoConta tokenAtivacao;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EnderecoService enderecoService;

	
	//FUNÇÃO PRA ENVIO DO E-MAIL COM TOKEN E ATUALIZAÇÃO DO TOKENATIVAÇÃO
	public void enviarEmailDeAtivacao(Cliente cliente) {
		String token = tokenAtivacao.gerarToken(LocalDateTime.now(ZoneOffset.UTC));
		cliente.setTokenAtivacao(token);
		repository.save(cliente);
		emailService.emailPraAtivacaoDaConta(cliente.getConta().getEmail(), cliente.getNome(), token);
	}

	//POST - CADASTRO DE CLIENTE (CERTO)
	public ResponseEntity<MensagemComClienteResponseDto> cadastrarCliente(ClienteRequestDto request) {
	    Optional<Cliente> clienteExistente = repository.findByContaEmail(request.getEmail());

	    if (clienteExistente.isPresent()) {
	        Cliente existente = clienteExistente.get();

	        if (!existente.getConta().isAtivo()) {
	            enviarEmailDeAtivacao(existente);

	            MensagemComClienteResponseDto resposta = new MensagemComClienteResponseDto(
	                "Conta já cadastrada, mas inativa. Um novo e-mail de ativação foi enviado.",
	                mapper.toResponse(existente)
	            );

	            return ResponseEntity.status(HttpStatus.OK).body(resposta);
	        }

	        throw new IllegalArgumentException("Conta já cadastrada e ativa.");
	    }

	    Cliente cliente = new Cliente();
	    Conta conta = new Conta();

	    conta.setAtivo(false);
	    conta.setEmail(request.getEmail());
	    conta.setNivelAcesso(NivelAcesso.NENHUM);
	    conta.setSenha(encoder.encode(request.getSenha()));

	    cliente.setCpf(request.getCpf());
	    cliente.setConta(conta);
	    cliente.setDataDeNascimento(request.getDataDeNascimento());
	    cliente.setEndereco(enderecoService.preencherEndereco(request.getCep(), request.getNumero()));
	    cliente.setNome(request.getNome());
	    cliente.setTelefone(request.getTelefone());
	    
	    String token = tokenAtivacao.gerarToken(LocalDateTime.now(ZoneOffset.UTC));
	    cliente.setTokenAtivacao(token);

	    repository.save(cliente);
	    emailService.emailPraAtivacaoDaConta(cliente.getConta().getEmail(), cliente.getNome(), token);

	    MensagemComClienteResponseDto resposta = new MensagemComClienteResponseDto(
	        "Conta criada com sucesso! Verifique seu e-mail para ativação.",
	        mapper.toResponse(cliente)
	    );

	    return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
	}
	
	//AUTENTICAR TOKEN E ATIVAR A CONTA
	public ResponseEntity<String> ativarConta(String email, String token) {
	    Cliente cliente = repository.findByContaEmail(email)
	        .orElseThrow(() -> new EntityNotFoundException("Nenhuma conta cadastrada neste e-mail."));

	    if (cliente.getConta().isAtivo()) {
	        throw new IllegalArgumentException("Sua conta já se encontra ativa.");
	    }

	    if (!cliente.getTokenAtivacao().equals(token)) {
	    	String novoToken = tokenAtivacao.gerarToken(LocalDateTime.now(ZoneOffset.UTC));
	    	cliente.setTokenAtivacao(novoToken);
	    	repository.save(cliente);
	    	emailService.emailPraAtivacaoDaConta(cliente.getConta().getEmail(), cliente.getNome(), cliente.getTokenAtivacao());
	    	emailService.emailPraAtivacaoDaConta(cliente.getConta().getEmail(), cliente.getNome(), novoToken);
		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado ou inválido. Um novo foi enviado ao seu e-mail.");
	    }
	    
        cliente.getConta().setAtivo(true);
        repository.save(cliente);
        emailService.emailContaCriadaComSucesso(email, cliente.getNome());
        return ResponseEntity.ok("Conta ativada com sucesso!");
	}
	
	//PUT - ATUALIZAÇÃO TOTAL DOS DADOS

		//CLIENTE ATUALIZA O CADASTRO 
		public ClienteResponseDto atualizacaoCadastro(ClienteRequestDto request) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Cliente cliente = repository.findByContaEmail(email).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID não encontrado."));
			
			cliente.setNome(request.getNome());
			cliente.setCpf(request.getCpf());
			cliente.setDataDeNascimento(request.getDataDeNascimento());
			cliente.setTelefone(request.getTelefone());
			cliente.getConta().setEmail(request.getEmail());
			cliente.getConta().setSenha(request.getSenha());
			
			return mapper.toResponse(repository.save(cliente));
		}
		
		//ATUALIZAÇÃO DO ENDEREÇO - TOTAL
		public Endereco atualizacaoEndereco(EnderecoUpdateDto endereco) {
			Endereco novoEndereco = mapper.toEndereco(endereco);
			return novoEndereco;
		}
		
		//FUNCIONARIO INTERNO ATUALIZA O CADASTRO DE CLIENTE 
		public ClienteResponseDto atualizacaoCadastroFuncionario(Long id, ClienteRequestDto request) {
		    Cliente cliente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));

		    cliente.setNome(request.getNome());
		    cliente.setCpf(request.getCpf());
		    cliente.setDataDeNascimento(request.getDataDeNascimento());
		    cliente.setTelefone(request.getTelefone());
		    cliente.getConta().setEmail(request.getEmail());
		    cliente.getConta().setSenha(request.getSenha());

		    return mapper.toResponse(repository.save(cliente));
		}
		
	//PATCH - ATUALIZAÇÃO PARCIAL DOS DADOS
		
		//ATUALIZAÇÃO DO ENDEREÇO - PARCIAL
		
		
	
		//ATUALIZAÇÃO PARCIAL FEITA PELO CLIENTE
		public ClienteResponseDto atualizacaoParcial(ClienteUpdateDto update) {
		    String email = SecurityContextHolder.getContext().getAuthentication().getName();
		    Cliente cliente = repository.findByContaEmail(email)
		        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

		    boolean foiAtualizado = false;

		    if (update.getNome() != null && !update.getNome().isBlank()) {
		        cliente.setNome(update.getNome());
		        foiAtualizado = true;
		    }

		    if (update.getCpf() != null && update.getCpf().matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
		        cliente.setCpf(update.getCpf());
		        foiAtualizado = true;
		    }

		    if (update.getDataDeNascimento() != null) {
		        cliente.setDataDeNascimento(update.getDataDeNascimento());
		        foiAtualizado = true;
		    }

		    if (update.getTelefone() != null && update.getTelefone().matches("^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$")) {
		        cliente.setTelefone(update.getTelefone());
		        foiAtualizado = true;
		    }

		    if (update.getEmail() != null && update.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
		        cliente.getConta().setEmail(update.getEmail());
		        foiAtualizado = true;
		    }

		    if (update.getSenha() != null &&
		        update.getSenha().matches("^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
		        cliente.getConta().setSenha(update.getSenha());
		        foiAtualizado = true;
		    }

		    if (!foiAtualizado) {
		        throw new IllegalArgumentException("Nenhuma alteração válida foi informada.");
		    }

		    return mapper.toResponse(repository.save(cliente));
		}
		
		//ATUALIZAÇÃO PARCIAL FEITA PELO FUNCIONÁRIO INTERNO
		public ClienteResponseDto atualizacaoParcialClientePorFuncionario(Long id, ClienteUpdateDto update) {
		    Cliente cliente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
		
		    if (update.getNome() != null && !update.getNome().isBlank()) {
		        cliente.setNome(update.getNome());
		    }

		    if (update.getCpf() != null && update.getCpf().matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
		        cliente.setCpf(update.getCpf());
		    }

		    if (update.getDataDeNascimento() != null) {
		        cliente.setDataDeNascimento(update.getDataDeNascimento());
		    }

		    if (update.getTelefone() != null && update.getTelefone().matches("^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$")) {
		        cliente.setTelefone(update.getTelefone());
		    }

		    if (update.getEmail() != null && update.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
		        cliente.getConta().setEmail(update.getEmail());
		    }

		    if (update.getSenha() != null &&
		    		update.getSenha().matches("^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$")) {
		        cliente.getConta().setSenha(update.getSenha());
		    }
		    
		    else {throw new IllegalArgumentException("Nenhuma alteração foi informada.");}
		    
		 return mapper.toResponse(repository.save(cliente));
		}
		    
		//DESATIVAR CONTA DO CLIENTE
		public ClienteResponseDto desativarConta(Long id) {
			Cliente cliente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
			
			if (cliente.getConta().isAtivo()) {
				cliente.getConta().setAtivo(false);
		        repository.save(cliente);
		    } else {throw new EntityNotFoundException("Conta do cliente já está desativada.");}

		    return mapper.toResponse(cliente);
		}
	
		//ATIVAR CONTA DO CLIENTE
		public ClienteResponseDto tivarConta(Long id) {
			Cliente cliente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado."));
			
			if (!cliente.getConta().isAtivo()) {
				cliente.getConta().setAtivo(true);
		        repository.save(cliente);
		    } else {throw new EntityNotFoundException("Conta do cliente já está ativada.");}

		    return mapper.toResponse(cliente);
		}
	
	//GET - PESQUISA
	
		//LISTAR TODOS OS CLIENTE
		public List<ClienteResponseDto> listarTodos(){
			return mapper.toListResponse(repository.findAll());
		}
		
		//LISTA O CLIENTE PELO ID
		public ClienteResponseDto buscarPorId(Long id) {
			Cliente cliente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
			return mapper.toResponse(cliente);
		}
	
		//PELO NOME
		public List<ClienteResponseDto> buscarNome(String nome) {
			List<Cliente> listaClientes = repository.findByNomeContainingIgnoreCase(nome);
			return mapper.toListResponse(listaClientes);
		}
	
		//LISTAR CLIENTE PELA CIDADE PESQUISADA
		public List<ClienteResponseDto> pesquisaPorCidade(String cidade) {
			List<Cliente> listaClientes = repository.findByEnderecoCidadeContainingIgnoreCase(cidade);
			return mapper.toListResponse(listaClientes);
		}
		
		//LISTAR CLIENTES PELO ESTADO PASSADO
		public List<ClienteResponseDto> pesquisaPorEstado(String cidade) {
			List<Cliente> listaClientes = repository.findByEnderecoEstadoContainingIgnoreCase(cidade);
			return mapper.toListResponse(listaClientes);
		}
		
		//LISTAR TODOS OS CLIENTES
		public List<ClienteResponseDto> listarCliente() {
			List<Cliente> clientes = repository.findAll();
			return mapper.toListResponse(clientes);
		}
		
		//LISTAR A QUANTIDADE DE CLIENTES QUE HÁ NO ESTADO E EM CADA CIDADE DE CADA ESTADO
		 public List<QuantidadeEstadoDto> listarQuantidadeClientes() {
        List<Object[]> resultados = repository.buscarQuantidadePorEstadoECidade();

        Map<String, List<QuantidadeCidadeDto>> agrupadoPorEstado = new HashMap<>();

        for (Object[] linha : resultados) {
            String estado = (String) linha[0];
            String cidade = (String) linha[1];
            Long quantidade = (Long) linha[2];

            agrupadoPorEstado
	            .computeIfAbsent(estado, k -> new ArrayList<>())
	            .add(new QuantidadeCidadeDto(cidade, quantidade));
        }

        List<QuantidadeEstadoDto> resposta = new ArrayList<>();

        for (Map.Entry<String, List<QuantidadeCidadeDto>> entry : agrupadoPorEstado.entrySet()) {
            String estado = entry.getKey();
            List<QuantidadeCidadeDto> cidades = entry.getValue();
            Long total = cidades.stream().mapToLong(QuantidadeCidadeDto::getQuantidade).sum();

            resposta.add(new QuantidadeEstadoDto(estado, total, cidades));
        }

        return resposta;
    }
		
	//DELETE
	public ResponseEntity<String> excluirCliente(Long id) {
		repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
		repository.deleteById(id);
		return ResponseEntity.ok("Conta excluída com sucesso!");
	}
	
}
