package com.vicenteleonardo.CursoUdemySpringBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Categoria;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.CategoriaRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.DataIntegrityException;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria find(Integer id) {
//		A partir do JAVA 8, essa forma ficou obsoleto, o Optional já faz o tratamento em casos de objetos null;
//		Categoria obj = repo.findOne(id);
//		return obj;
		
		
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName(), null));
	}
	
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		categoriaRepository.save(categoria);
		return categoria;
		
	}


	public void delete(Integer id) {
		try {
			categoriaRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categorias que possui produtos");
		}
		
	}
	
	public List<Categoria>findAll(){
		return categoriaRepository.findAll();
	}
}
