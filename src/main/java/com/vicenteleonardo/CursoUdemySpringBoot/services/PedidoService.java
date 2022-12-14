package com.vicenteleonardo.CursoUdemySpringBoot.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.ItemPedido;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.PagamentoComBoleto;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Pedido;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.EstadoPagamento;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.ItemPedidoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.PagamentoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.PedidoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private IEmailService iEmailService;
	
	public Pedido find(Integer id){
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName(),null));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante());
		}
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido itemPedido : obj.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			itemPedido.setPedido(obj);
			
		}
		itemPedidoRepository.saveAll(obj.getItens());
		iEmailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}
