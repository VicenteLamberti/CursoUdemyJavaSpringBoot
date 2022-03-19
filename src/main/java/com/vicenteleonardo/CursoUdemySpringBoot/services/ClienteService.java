package com.vicenteleonardo.CursoUdemySpringBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cidade;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cliente;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Endereco;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.TipoCliente;
import com.vicenteleonardo.CursoUdemySpringBoot.dto.ClienteDTO;
import com.vicenteleonardo.CursoUdemySpringBoot.dto.ClienteNewDTO;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.CidadeRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.ClienteRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.EnderecoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.DataIntegrityException;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id){
		Optional<Cliente> cliente =  repo.findById(id);
		return cliente.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName(),null));
		
	}
	
	public Cliente update(Cliente cliente) {
		Cliente clienteParaAtualizar = find(cliente.getId());
		updateData(clienteParaAtualizar, cliente);
		repo.save(clienteParaAtualizar);
		return clienteParaAtualizar;
		
	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}


	public void delete(Integer id) {
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma clientes que possui produtos");
		}
		
	}
	
	public List<Cliente>findAll(){
		return repo.findAll();
	}
	

	public Page<Cliente>findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO (ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(),clienteDTO.getNome(),clienteDTO.getEmail(),null,null);
	}
	
	public Cliente fromDTO( ClienteNewDTO clienteNewDTO) {
		Cliente cliente =  new Cliente(null,clienteNewDTO.getNome(),clienteNewDTO.getEmail(),clienteNewDTO.getCpfOuCnpj(),TipoCliente.toEnum(clienteNewDTO.getTipo()));
		Cidade cidade = cidadeRepository.getById(clienteNewDTO.getCidadeId());
		Endereco endereco = 
				new Endereco(null,  clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), 
						clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		
		if(clienteNewDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		
		if(clienteNewDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		return cliente;
	}	
	private void updateData(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
		
	}

	
}

