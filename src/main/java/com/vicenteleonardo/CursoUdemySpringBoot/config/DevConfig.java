package com.vicenteleonardo.CursoUdemySpringBoot.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vicenteleonardo.CursoUdemySpringBoot.services.DBService;
import com.vicenteleonardo.CursoUdemySpringBoot.services.IEmailService;
import com.vicenteleonardo.CursoUdemySpringBoot.services.MockEmailService;
import com.vicenteleonardo.CursoUdemySpringBoot.services.SmtpEmailService;

/**
 * Classe criada para o perfil de dev
Miniatura da aula
7:21 / 10:46
57. Criando o profile de desenvolvimento, só é chamada quando está neste perfil.
 * @author Vicente
 *
 */
@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbDbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	/**
	 * Invoca o método de instanciar os objetos para testes. Só vai ser executado se o ddlauto estiver como create, senão duplicaria os dados.
	 * @return true ou false, só retorna porque não pode ser void.
	 * @throws ParseException 
	 */
	@Bean
	public boolean instatiateDatabase() throws ParseException {
		if("create".equals(strategy) == false) {
			return false; 
		}
		dbDbService.instatiateTestDatabase();
		return true;
	}
	
	@Bean
	public IEmailService iEmailService() {
		return new SmtpEmailService();
	}
}
