package com.vicenteleonardo.CursoUdemySpringBoot.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vicenteleonardo.CursoUdemySpringBoot.domain.enums.TipoCliente;
import com.vicenteleonardo.CursoUdemySpringBoot.dto.ClienteNewDTO;
import com.vicenteleonardo.CursoUdemySpringBoot.resources.exceptions.FieldMessage;
import com.vicenteleonardo.CursoUdemySpringBoot.services.validation.utils.BR;

/**
 * Classe também responsável por criar uma anotação/validacao personalizada
 * 
 * @author Vicente
 *
 *         ClienteInsert do parametro é o nome da anotacao
 */
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && BR.isValidCPF(objDto.getCpfOuCnpj()) == false) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && BR.isValidCNPJ(objDto.getCpfOuCnpj()) == false) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}
		

		
		//Loop para adicionar os meus erros personalizados, nos erros do framework
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
