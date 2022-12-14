package com.vicenteleonardo.CursoUdemySpringBoot.services;

import org.springframework.mail.SimpleMailMessage;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Pedido;

public interface IEmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
