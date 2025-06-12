package org.serratec.h2.grupo2.controller;

import java.util.List;

import org.serratec.h2.grupo2.DTO.pedido.PedidoAndamentoResponseDto;
import org.serratec.h2.grupo2.DTO.pedido.PedidoFinalizadoResponseDto;
import org.serratec.h2.grupo2.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired

    private PedidoService service;

    //ADICIONA UM PRODUTO NO PEDIDO
    @PostMapping("/adicionar")
    public PedidoAndamentoResponseDto adicionarProdutoPedido(@RequestParam Long idProduto, @RequestParam Integer quantidade) {
    	return service.adicionarProdutoPedido(idProduto, quantidade);
    }
   
    //FINALIZAR PEDIDO, ELE VAI PRA ROTA DE ENTREGA + E-MAIL
    @PostMapping("/finalizar")
    public PedidoFinalizadoResponseDto finalizarPedido() {
    	return service.finalizarPedido();
    }
    
    //DIMINUIR A QUANTIDADE DE UM PRODUTO NO PEDIDO
    @PatchMapping("/diminuir/{itemId}")
    public PedidoAndamentoResponseDto diminuirQuantidade(@PathVariable Long itemId) {
    	return service.diminuirQuantidade(itemId);
    }
    
    //AUMENTAR A QUANTIDADE DE UM PRODUTO NO PEDIDO
    @PatchMapping("/aumentar/{itemId}")
    public PedidoAndamentoResponseDto aumentarQuantidade(@PathVariable Long itemId) {
    	return service.aumentarQuantidade(itemId);
    }
    
    //EXLUIR UM ITEM DO PEDIDO
    @DeleteMapping("/excluir/item/{itemId}")
    public PedidoAndamentoResponseDto excluirItem(@PathVariable Long itemId) {
    	return service.excluirItem(itemId);
    }
    
    //CANCELAMENTO DE UM PEDIDO QUE ESTAVA EM ROTA DE ENTREGA + E-MAIL
    @PatchMapping("/cancelar/{idPedido}")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long idPedido) {
    	return service.cancelarPedido(idPedido);
    }
    
    //LISTAR PEDIDOS EM ENTREGA DO PRÓPRIO CLIENTE
    @GetMapping("/em-entrega")
    public List<PedidoAndamentoResponseDto> pedidosEmEntrega() {
    	return service.pedidosEmEntrega();
    }
    
    //LISTAR PEDIDOS FINALIZADOS DO PRÓPRIO CLIENTE
    @GetMapping("/finalizados")
    public List<PedidoAndamentoResponseDto> pedidosFinalizados() {
    	return service.pedidosFinalizados();
    }
    
    //LISTAR PEDIDOS CANCELADOS DO PRÓRPRIO CLIENTE
    @GetMapping("/cancelados")
    public List<PedidoAndamentoResponseDto> pedidosCancelados() {
    	return service.pedidosCancelados();
    }
    
    //FUNCINÁRIO
    
    //LISTAR TODOS OS PEDIDOS EM ANDAMENTOS
    @GetMapping("/listar-pedidos-andamento")
    public List<PedidoAndamentoResponseDto> pedidoAndamentoFuncionario () {
    	return service.pedidoAndamentoFuncionario();
    }
    
    //LISTAR TODOS OS PEDIDOS EM ROTA DE ENTREGA
    @GetMapping("/listar-pedidos-entregues")
    public List<PedidoAndamentoResponseDto> pedidosEntregaFuncionario () {
    	return service.pedidosEntregaFuncionario();
    }
    
    //LISTAR TODOS OS PEDIDO FINALIZADOS
    @GetMapping("/listar-pedidos-finalizados")
    public List<PedidoAndamentoResponseDto> pedidosFinalizadosFuncionario () {
    	return service.pedidosFinalizadosFuncionario();
    }
    
    //LISTAR TODOS OS PEDIDOS CANCELADOS
    @GetMapping("/listar-pedidos-cancelados")
    public List<PedidoAndamentoResponseDto> pedidosCanceladosFuncionario () {
    	return service.pedidosCanceladosFuncionario();
    }
    
    //FUNCIONÁRIO LISTA TODOS OS PEDIDOS DO CLIENTE PELO ID
    @GetMapping("/pedidos-por-cliente/{idCliente}")
    public List<PedidoAndamentoResponseDto> pedidosCliente(@PathVariable Long idCliente) {
    	return service.pedidosCliente(idCliente);
    }

}



