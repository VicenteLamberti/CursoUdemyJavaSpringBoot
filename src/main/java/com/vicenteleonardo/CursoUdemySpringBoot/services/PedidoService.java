package com.vicenteleonardo.CursoUdemySpringBoot.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cliente;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.ItemPedido;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.PagamentoComBoleto;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Pedido;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.EstadoPagamento;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.ItemPedidoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.PagamentoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.PedidoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.security.UserSS;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.AuthorizationException;
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
		iEmailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido>findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user =UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		Cliente cliente = clienteService.find(user.getId());	
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
