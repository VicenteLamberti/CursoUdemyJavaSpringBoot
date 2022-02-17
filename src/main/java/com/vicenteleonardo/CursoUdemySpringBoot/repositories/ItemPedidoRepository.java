package com.vicenteleonardo.CursoUdemySpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Integer> {

}
