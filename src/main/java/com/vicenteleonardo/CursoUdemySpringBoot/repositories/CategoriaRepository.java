package com.vicenteleonardo.CursoUdemySpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Categoria;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

}
