package com.vicenteleonardo.CursoUdemySpringBoot.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cidade;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cliente;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Endereco;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.Perfil;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.TipoCliente;
import com.vicenteleonardo.CursoUdemySpringBoot.dto.ClienteDTO;
import com.vicenteleonardo.CursoUdemySpringBoot.dto.ClienteNewDTO;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.ClienteRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.EnderecoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.security.UserSS;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.AuthorizationException;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.DataIntegrityException;
import com.vicenteleonardo.CursoUdemySpringBoot.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private S3Service s3Service;
	
	
	
	public Cliente find(Integer id){
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> cliente =  repo.findById(id);
		return cliente.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName(),null));
		
	}
	
	public Cliente update(Cliente cliente) {
		Cliente clienteParaAtualizar = find(cliente.getId());
		updateData(clienteParaAtualizar, cliente);
		repo.save(clienteParaAtualizar);
		return clienteParaAtualizar;
		
	}
	
	@Transactional
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
			throw new DataIntegrityException("Não é possível excluir um clientes que possui produtos");
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
		return new Cliente(clienteDTO.getId(),clienteDTO.getNome(),clienteDTO.getEmail(),null,null, null);
	}
	
	public Cliente fromDTO( ClienteNewDTO clienteNewDTO) {
		Cliente cliente =  new Cliente(null,clienteNewDTO.getNome(),clienteNewDTO.getEmail(),clienteNewDTO.getCpfOuCnpj(),TipoCliente.toEnum(clienteNewDTO.getTipo()), encoder.encode(clienteNewDTO.getSenha()));
		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(),null,null);
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
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if(user==null) {
			throw new AuthorizationException("Acesso negado");
		
		}
		URI uri = s3Service.uploadFile(multipartFile);
		Cliente cliente = repo.findById(user.getId()).get();
		cliente.setImageUrl(uri.toString());
		repo.save(cliente);
		
		return uri;
	}

	
}

