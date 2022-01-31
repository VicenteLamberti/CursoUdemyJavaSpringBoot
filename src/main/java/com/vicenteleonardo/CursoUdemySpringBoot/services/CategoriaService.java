package com.vicenteleonardo.CursoUdemySpringBoot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Categoria;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
//		A partir do JAVA 8, essa forma ficou obsoleto, o Optional já faz o tratamento em casos de objetos null;
//		Categoria obj = repo.findOne(id);
//		return obj;
		
		
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
