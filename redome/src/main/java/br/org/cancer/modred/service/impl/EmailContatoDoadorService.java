package br.org.cancer.modred.service.impl;

import java.util.List;

import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.EmailContatoDoador;
import br.org.cancer.modred.persistence.IEmailContatoDoadorRepository;
import br.org.cancer.modred.service.IEmailContatoDoadorService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe de métodos de negócio de Email de Contato.
 * 
 * @author Filipe Paes
 *
 */
@Service
public class EmailContatoDoadorService implements IEmailContatoDoadorService {

	@Autowired
	private IEmailContatoDoadorRepository repository;
	
	@Autowired
	private Validator validator;
	

	@Override
	public boolean salvar(EmailContatoDoador email) {
		validar(email);
		return repository.save(email) != null;
	}

	@Override
	public void validar(EmailContatoDoador email) {
		List<CampoMensagem> campos = new ConstraintViolationTransformer(validator.validate(email)).transform();

		if (CollectionUtils.isNotEmpty(campos)) {
			throw new ValidationException("erro.validacao", campos);
		}

	}

	@Override
	public List<EmailContatoDoador> listarEmails(Long idDoador) {
		return repository.listarEmails(idDoador);
	}

}
