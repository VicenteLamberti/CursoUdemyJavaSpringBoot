package com.vicenteleonardo.CursoUdemySpringBoot.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cliente;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Pedido;

public interface IEmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
