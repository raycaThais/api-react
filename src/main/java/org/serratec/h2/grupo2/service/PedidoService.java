package org.serratec.h2.grupo2.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.serratec.h2.grupo2.DTO.pedido.ItemIndisponivel;
import org.serratec.h2.grupo2.DTO.pedido.PedidoAndamentoResponseDto;
import org.serratec.h2.grupo2.DTO.pedido.PedidoFinalizadoResponseDto;
import org.serratec.h2.grupo2.domain.Cliente;
import org.serratec.h2.grupo2.domain.ItemPedido;
import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.enuns.StatusPedido;
import org.serratec.h2.grupo2.mapper.PedidoMapper;
import org.serratec.h2.grupo2.repository.ClienteRepository;
import org.serratec.h2.grupo2.repository.ItemPedidoRepository;
import org.serratec.h2.grupo2.repository.PedidoRepository;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoService {


	@Autowired
	private PedidoRepository pedidoRepository;
	    
	@Autowired
	private ClienteRepository clienteRepository;
	    
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemRepository;
	
	@Autowired
	private PedidoMapper mapper;
	
	@Autowired
	private EmailService emailService;

	//FUNÇÕES
	
		//CRIAR NOVO PEDIDO
		public Pedido criarPedido(Cliente cliente) {
	        log.info("Criando novo pedido para o cliente: {}", cliente.getId());
	        Pedido pedido = new Pedido();
	        
	        pedido.setDataCriacao(LocalDate.now());
	        pedido.setStatus(StatusPedido.EM_ANDAMENTO);
	        pedido.setCliente(cliente);
	        log.debug("Pedido criado: {}", pedido);
	        
	        return pedido;
	    }
		
		//CRIAR UM ITEM PEDIDO
		 public ItemPedido criarItemPedido(Long idProduto, Integer quantidade) {
			 log.info("Criando item de pedido - Produto ID: {}, Quantidade: {}", idProduto, quantidade);
		     Produto produto = produtoRepository.findById(idProduto)
		            .orElseThrow(() -> {
		                log.warn("Produto com ID {} não encontrado", idProduto);
		                return new EntityNotFoundException("Nenhum produto cadastrado neste id.");
		            });

		     ItemPedido item = new ItemPedido();
		        
		     item.setProduto(produto);
		     item.setPrecoUnitario(produto.getPreco());
		     item.setQuantidade(quantidade);
		     item.setPrecoTotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade)));
		     log.debug("Item de pedido criado: {}", item);
		     
		     return item;
		 }
		
		//CALCULO DE TOTAL DO PEDIDO
		 public BigDecimal valorTotal(Pedido pedido) {
			    log.info("Calculando valor total para o pedido ID: {}", pedido.getId());
	
			    BigDecimal total = BigDecimal.ZERO;
	
			    for (ItemPedido item : pedido.getItens()) {
			        total = total.add(item.getPrecoTotal());
			    }
	
			    BigDecimal valorFrete = pedido.getValorFrete() != null ? pedido.getValorFrete() : BigDecimal.ZERO;
			    BigDecimal valorTotal = total.add(valorFrete);
	
			    log.debug("Valor total calculado: {}", valorTotal);
			    return valorTotal;
			}
		
		//CALCULO DE FRETE
		 public BigDecimal calcularFrete(Integer quantidade) {
	        log.info("Calculando frete para {} itens", quantidade);
	        BigDecimal valorBase = new BigDecimal("5.0");
	        
	        BigDecimal adicionalPorItem = new BigDecimal("1.0");
	        BigDecimal valorFrete = valorBase.add(adicionalPorItem.multiply(new BigDecimal(quantidade)));
	        log.debug("Valor do frete calculado: {}", valorFrete);
	        
	        return valorFrete;
		    }

		//CRIAR UM ITEM PEDIDO INDISPONIVEL
		 public ItemIndisponivel criarItemIndisponivel(String nome, Integer quantidadeFaltante) {
	        log.info("Criando item indisponível: {}, faltando: {}", nome, quantidadeFaltante);
	        ItemIndisponivel item = new ItemIndisponivel();
	        
	        item.setNome(nome);
	        item.setQuantidadeFaltante(quantidadeFaltante);
	        
	        return item;
	    }
		
		 
	//CLIENTE FAZ AS REQUISIÇÕES
	//ADICIONAR PRODUTO NO PEDIDO
	public PedidoAndamentoResponseDto adicionarProdutoPedido(Long idProduto, Integer quantidade) {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    log.info("Adicionando produto ID {} ao pedido do cliente {}", idProduto, email);

	    Produto produto = produtoRepository.findById(idProduto)
	        .orElseThrow(() -> new EntityNotFoundException("Não há nenhum produto vinculado a este ID."));

	    //NO CASO DA PESSOA ADICIONAR O PRODUTO SEM PASSAR NENHUM VALOR
	    if (quantidade == null || quantidade <= 0) {
	        log.warn("Quantidade inválida informada ({}). Usando quantidade = 1", quantidade);
	        quantidade = 1;
	    }

	    // BUSCA PEDIDO ATUAL
	    Pedido pedido = pedidoRepository.findTopByClienteContaEmailAndStatus(email, StatusPedido.EM_ANDAMENTO).orElse(null);

	    //NÃO TENDO NENHUM PEDIDO EM ANDAMENTO, EU CRIO UM NOVO
	    if (pedido == null || pedido.getStatus() == StatusPedido.CANCELADO || pedido.getStatus() == StatusPedido.CONCLUIDO) {
	        log.info("Nenhum pedido em andamento. Criando novo pedido para o cliente {}", email);
	        Cliente cliente = clienteRepository.findByContaEmail(email)
	            .orElseThrow(() -> new EntityNotFoundException("Nenhuma conta cadastrada neste e-mail."));
	        pedido = criarPedido(cliente);
	        cliente.getPedidos().add(pedido);
	        pedidoRepository.save(pedido);
	        clienteRepository.save(cliente);
	    }

	    // VERIFICA SE O ITEM JÁ EXISTE NO PEDIDO
	    Optional<ItemPedido> itemExistente = itemRepository.findByPedidoIdAndProdutoId(pedido.getId(), idProduto);

		    if (itemExistente.isPresent()) {
		        ItemPedido item = itemExistente.get();
		        int novaQuantidade = item.getQuantidade() + quantidade;
	
		        if (novaQuantidade > produto.getEstoque()) {
		            throw new IllegalArgumentException("Estoque insuficiente para a quantidade solicitada.");
		        }
	
		        item.setQuantidade(novaQuantidade);
		        item.setPrecoTotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(novaQuantidade)));
		        itemRepository.save(item);
	
		    } else {
		        if (quantidade > produto.getEstoque()) {
		            throw new IllegalArgumentException("Estoque insuficiente para a quantidade solicitada.");
		        }
	
		        ItemPedido item = new ItemPedido();
		        item.setProduto(produto);
		        item.setPedido(pedido);
		        item.setQuantidade(quantidade);
	
		        // BASICAMENTE, PRA NO CASO DE HAVER UM PREÇO PROMOCIONAL, O VALOR SER ATUALIZADO
		        BigDecimal precoUnitario = produto.getPreco();
		        if (produto.getPrecoPromocional() != null && produto.getPrecoPromocional().compareTo(BigDecimal.ZERO) > 0) {
		            precoUnitario = produto.getPrecoPromocional();
		        }
		        item.setPrecoUnitario(precoUnitario);
		        item.setPrecoTotal(precoUnitario.multiply(BigDecimal.valueOf(quantidade)));
		        pedido.getItens().add(item);
		        itemRepository.save(item);
		    }
	
	    // RECALCULO DO FRETE E VALOR TOTAL
	    pedido.setValorFrete(calcularFrete(pedido.getItens().size()));
	    pedido.setPrecoTotal(valorTotal(pedido));
	    pedidoRepository.save(pedido);

	    log.info("Pedido ID {} atualizado. Total: R${}, Frete: R${}", pedido.getId(), pedido.getPrecoTotal(), pedido.getValorFrete());

	    return mapper.toResponse(pedido);
	}

	//FINALIZAR O PEDIDO - ELE ENTRA COMO EM_ENTREGA
	public PedidoFinalizadoResponseDto finalizarPedido() {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    log.info("Finalizando pedido do cliente {}", email);

	    Pedido pedido = pedidoRepository
	        .findTopByClienteContaEmailAndStatus(email, StatusPedido.EM_ANDAMENTO)
	        .orElseThrow(() -> new EntityNotFoundException("Não há nenhum pedido em aberto."));

	    List<ItemPedido> itensDisponiveis = new ArrayList<>();
	    List<ItemIndisponivel> itensIndisponiveis = new ArrayList<>();

	    for (ItemPedido item : pedido.getItens()) {
	        Integer estoqueAtual = item.getProduto().getEstoque();
	        Integer quantidadeSolicitada = item.getQuantidade();
	        Produto produto = item.getProduto();

	        if (estoqueAtual >= quantidadeSolicitada) {
	            log.debug("Produto {} tem estoque suficiente", produto.getId());
	            produto.setEstoque(estoqueAtual - quantidadeSolicitada);
	            itensDisponiveis.add(item);

	        } else if (estoqueAtual > 0) {
	            log.debug("Produto {} com estoque parcial", produto.getId());
	            itensIndisponiveis.add(criarItemIndisponivel(produto.getNome(), quantidadeSolicitada - estoqueAtual));
	            item.setQuantidade(estoqueAtual);
	            produto.setEstoque(0);
	            itensDisponiveis.add(item);

	        } else {
	            log.debug("Produto {} sem estoque, será removido", produto.getId());
	            itensIndisponiveis.add(criarItemIndisponivel(produto.getNome(), quantidadeSolicitada));
	            itemRepository.deleteById(item.getId());
	            continue;
	        }

	        // DEFINIR O PREÇO PROMOCIONAL, CASO HOUVER
	        if (produto.getPrecoPromocional() != null && produto.getPrecoPromocional().compareTo(BigDecimal.ZERO) > 0) {
	            item.setPrecoUnitario(produto.getPrecoPromocional());
	        }

	        // DESATIVAR PRODUTO SE ESTOQUE FICAR ZERADO
	        if (produto.getEstoque().equals(0)) {
	            produto.setAtivo(false);
	        }

	        itemRepository.save(item);
	        produtoRepository.save(produto);
	    }

	    pedido.setItens(itensDisponiveis);
	    pedido.setStatus(StatusPedido.EM_ENTREGA);
	    pedido.setPrecoTotal(valorTotal(pedido));
	    pedidoRepository.save(pedido);

	    log.info("Pedido ID {} finalizado e enviado para entrega", pedido.getId());
	    emailService.emailConfirmacaoDeCompra(email, mapper.toResponseFinalizado(pedido, itensIndisponiveis));

	    return mapper.toResponseFinalizado(pedido, itensIndisponiveis);
	}
	
	//CONFIRMAR ENTREGA DO PEDIDO 

	//CANCELAR PEDIDO
    public ResponseEntity<String> cancelarPedido(Long idPedido) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Cancelando pedido ID {} para cliente {}", idPedido, email);

        Pedido pedido = pedidoRepository.findById(idPedido)
            .orElseThrow(() -> new EntityNotFoundException("Não há nenhum pedido em rota de entrega."));

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
        emailService.emailPedidoCanceladoEmTransporte(email, mapper.toResponseFinalizado(pedido, null));

        log.info("Pedido ID {} cancelado com sucesso", idPedido);
        return ResponseEntity.ok("Pedido em entrega foi cancelado com sucesso!!");
    }
    
	//LISTAR PEDIDOS COM O STATUS EM ENTREGA
    public List<PedidoAndamentoResponseDto> pedidosEmEntrega() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Listando pedidos em entrega para {}", email);

        List<Pedido> pedidos = pedidoRepository.findAllByClienteContaEmailAndStatus(email, StatusPedido.EM_ENTREGA);
        return mapper.toListResponse(pedidos);
    }
	
	//LISTAR UM HISTÓRIOCO DE PEDIDOS CONCLUIDOS
    public List<PedidoAndamentoResponseDto> pedidosFinalizados() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Listando pedidos finalizados para {}", email);

        List<Pedido> pedidos = pedidoRepository.findAllByClienteContaEmailAndStatus(email, StatusPedido.CONCLUIDO);
        return mapper.toListResponse(pedidos);
    }

	
	//LISTAR PEDIDOS CANCELADOS
	public List<PedidoAndamentoResponseDto> pedidosCancelados() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Pedido> pedidosCancelados = pedidoRepository.findAllByClienteContaEmailAndStatus(email, StatusPedido.CANCELADO);
		log.info("Listando pedidos cancelados para {}", email);
		
		return mapper.toListResponse(pedidosCancelados);
	}
	
	//DIMINUIR A QUANTIDADE DE UM PRODUTO NO PEDIDO
	public PedidoAndamentoResponseDto diminuirQuantidade(Long itemId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Usuário {} solicitou diminuir a quantidade do item ID {}", email, itemId);

        ItemPedido item = itemRepository
            .findByIdAndPedidoStatusAndPedidoClienteContaEmail(itemId, StatusPedido.EM_ANDAMENTO, email)
            .orElseThrow(() -> {
                log.warn("Item com ID {} não encontrado para o usuário {}", itemId, email);
                return new EntityNotFoundException("Produto não se encontra em nenhum pedido em andamento.");
            });

        if (item.getQuantidade() > 1) {
            item.setQuantidade(item.getQuantidade() - 1);
            item.setPrecoTotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
            itemRepository.save(item);
            
            Pedido pedido = item.getPedido();
            pedido.setPrecoTotal(valorTotal(pedido));
            pedidoRepository.save(pedido);

            return mapper.toResponse(pedido);

        } else {
            itemRepository.deleteById(itemId);
            log.info("Item {} removido do pedido do usuário {}", itemId, email);
            
            Pedido pedido = item.getPedido();
            pedido.setPrecoTotal(valorTotal(pedido));
            pedidoRepository.save(pedido);
            
            return mapper.toResponse(pedido);
        }
    }
	
	//AUMENTAR A QUANTIDADE DE UM PRODUTO NO PEDIDO
	public PedidoAndamentoResponseDto aumentarQuantidade(Long id) {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    log.info("Usuário '{}' solicitou aumentar a quantidade do item de ID {}", email, id);

	    ItemPedido item = itemRepository
	        .findByIdAndPedidoStatusAndPedidoClienteContaEmail(id, StatusPedido.EM_ANDAMENTO, email)
	        .orElseThrow(() -> {
	            log.warn("Item com ID {} não encontrado para o usuário '{}'", id, email);
	            return new EntityNotFoundException("Produto não se encontra em nenhum pedido em andamento.");});

	    int estoqueDisponivel = item.getProduto().getEstoque();
	    int quantidadeAtual = item.getQuantidade();

	    if (quantidadeAtual < estoqueDisponivel) {
	        item.setQuantidade(quantidadeAtual + 1);
	        item.setPrecoTotal(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
	        itemRepository.save(item);

	        Pedido pedido = item.getPedido();
	        pedido.setPrecoTotal(valorTotal(pedido));
	        pedidoRepository.save(pedido);

	        return mapper.toResponse(pedido);
	    } else {
	        String mensagem = String.format("Não há estoque suficiente do produto '%s' para efetuar o aumento. Estoque disponível: %d", 
	                                        item.getProduto().getNome(), estoqueDisponivel);
	        log.warn(mensagem);
	        throw new EntityNotFoundException(mensagem);
	    }
	}
	
	//EXLUIR UM ITEM DO PEDIDO
	public PedidoAndamentoResponseDto excluirItem(Long itemId) {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    log.info("Usuário '{}' solicitou a exclusão do item de ID {}", email, itemId);

	    ItemPedido item = itemRepository
	        .findByIdAndPedidoStatusAndPedidoClienteContaEmail(itemId, StatusPedido.EM_ANDAMENTO, email)
	        .orElseThrow(() -> {
	            log.warn("Item com ID {} não encontrado no pedido em andamento do usuário '{}'", itemId, email);
	            return new EntityNotFoundException("Produto não se encontra em nenhum pedido em andamento.");
	        });

	    Pedido pedido = item.getPedido();

	    itemRepository.deleteById(itemId);
	    log.info("Item de ID {} removido com sucesso do pedido ID {}", itemId, pedido.getId());

	    pedido.getItens().removeIf(i -> i.getId().equals(itemId));
	    pedido.setValorFrete(calcularFrete(pedido.getItens().size()));
	    pedido.setPrecoTotal(valorTotal(pedido));
	    pedidoRepository.save(pedido);

	    log.info("Pedido ID {} atualizado após exclusão do item. Novo total: R${}, Novo frete: R${}",
	            pedido.getId(), pedido.getPrecoTotal(), pedido.getValorFrete());

	    return mapper.toResponse(pedido);
	}
	
	
	//FUNCIONARIO FAZ AS REQUISIÇÕES
	
	//LISTAR TODOS OS PEDIDOS EM ANDAMENTOS
	public List<PedidoAndamentoResponseDto> pedidoAndamentoFuncionario () {
		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.EM_ANDAMENTO);
		return mapper.toListResponse(pedidos);
	}
	
	//LISTAR TODOS OS PEDIDOS EM ROTA DE ENTREGA
	public List<PedidoAndamentoResponseDto> pedidosEntregaFuncionario () {
		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.EM_ENTREGA);
		return mapper.toListResponse(pedidos);
	}
	
	//LISTAR TODOS OS PEDIDO FINALIZADOS
	public List<PedidoAndamentoResponseDto> pedidosFinalizadosFuncionario () {
		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.CONCLUIDO);
		return mapper.toListResponse(pedidos);
	}
	
	//LISTAR TODOS OS PEDIDOS CANCELADOS
	public List<PedidoAndamentoResponseDto> pedidosCanceladosFuncionario () {
		List<Pedido> pedidos = pedidoRepository.findByStatus(StatusPedido.CANCELADO);
		return mapper.toListResponse(pedidos);
	}
	
	//FUNCIONÁRIO LISTA TODOS OS PEDIDOS DO CLIENTE PELO ID
	public List<PedidoAndamentoResponseDto> pedidosCliente(Long idCliente) {
		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> {
            log.warn("Cliente com o id {} não foi encontrado.", idCliente);
            return new EntityNotFoundException("Nenhum é referênciado por este id.");});
		
		List<Pedido> pedidos = pedidoRepository.findByClienteId(cliente.getId());
		return mapper.toListResponse(pedidos);
	}
}

