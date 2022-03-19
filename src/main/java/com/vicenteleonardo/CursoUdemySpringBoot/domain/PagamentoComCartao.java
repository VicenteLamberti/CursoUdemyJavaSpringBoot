package com.vicenteleonardo.CursoUdemySpringBoot.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.EstadoPagamento;

@Entity  
@JsonTypeName("pagamentoComCartao") // Utilizado porque na classe mãe pagamento eu disse que ia ter um campo a mais que ia ser referenciado por esse nome
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
	}
	
	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id,estado,pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}


}
