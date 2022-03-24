package com.vicenteleonardo.CursoUdemySpringBoot.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vicenteleonardo.CursoUdemySpringBoot.services.DBService;

/**
 * Classe criada para o perfil de teste, só é chamada quando está neste perfil.
 * @author Vicente
 *
 */
@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbDbService;

	/**
	 * Invoca o método de instanciar os objetos para testes.
	 * @return true ou false, só retorna porque não pode ser void.
	 * @throws ParseException 
	 */
	@Bean
	public boolean instatiateDatabase() throws ParseException {
		dbDbService.instatiateTestDatabase();
		return true;
	}
}
