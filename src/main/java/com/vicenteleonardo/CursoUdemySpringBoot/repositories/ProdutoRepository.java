package com.vicenteleonardo.CursoUdemySpringBoot.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Categoria;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Integer> {

//	@Transactional(readOnly = true)
//	@Query(value="SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
//	Page<Produto> search(@Param("nome")String nome, @Param("categorias")List<Categoria> categorias, Pageable pageRequest);
	
	
	//Mesmo efeito com padrão do Spring Data
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	
}
