package com.vicenteleonardo.CursoUdemySpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	
	Cliente findByEmail(String email);

}
