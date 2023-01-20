package com.vicenteleonardo.CursoUdemySpringBoot;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.Categoria;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cidade;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Cliente;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Endereco;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Estado;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.ItemPedido;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Pagamento;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.PagamentoComBoleto;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.PagamentoComCartao;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Pedido;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.Produto;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.EstadoPagamento;
import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.TipoCliente;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.CategoriaRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.CidadeRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.ClienteRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.EnderecoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.EstadoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.ItemPedidoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.PagamentoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.PedidoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.repositories.ProdutoRepository;
import com.vicenteleonardo.CursoUdemySpringBoot.services.S3Service;

@SpringBootApplication
public class CursoUdemySpringBootApplication implements CommandLineRunner {


	@Autowired
	private S3Service s3Service;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoUdemySpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\vicente.lambert\\Pictures\\cao-europeu.jpg");
		
	}

	

}
