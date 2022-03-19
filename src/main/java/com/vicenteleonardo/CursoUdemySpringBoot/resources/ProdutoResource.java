package com.vicenteleonardo.CursoUdemySpringBoot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Produto;
import com.vicenteleonardo.CursoUdemySpringBoot.dto.ProdutoDTO;
import com.vicenteleonardo.CursoUdemySpringBoot.resources.utils.URL;
import com.vicenteleonardo.CursoUdemySpringBoot.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id){
		Produto produto = produtoService.find(id);
		return ResponseEntity.ok().body(produto);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome",defaultValue = "") String nome,
			@RequestParam(value="categorias",defaultValue = "") String categorias,
			@RequestParam(value="page",defaultValue = "0") Integer page,
			@RequestParam(value="linesPerPage",defaultValue = "24") Integer linesPerPage,
			@RequestParam(value="orderBy",defaultValue = "nome") String orderBy,
			@RequestParam(value="direction",defaultValue = "ASC") String direction){
		
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecod = URL.decodeParam(nome);
		Page<Produto> listPageProduto = produtoService.search(nome,ids,page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listPageProdutoDTO = listPageProduto.map(produto -> new ProdutoDTO(produto));
		return ResponseEntity.ok().body(listPageProdutoDTO);
		
	}
}
