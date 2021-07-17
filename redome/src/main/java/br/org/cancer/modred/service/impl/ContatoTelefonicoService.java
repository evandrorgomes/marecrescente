package br.org.cancer.modred.service.impl;

import java.util.List;

import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.persistence.IContatoTelefonicoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IContatoTelefonicoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe para implementar métodos de negócio para contato telefonico.
 * @param <T> classe que define a implementação em cima do contato telefonico.
 * 
 * @author Filipe Paes
 */
@Service
@Transactional
public class ContatoTelefonicoService<T extends ContatoTelefonico> extends AbstractService<T, Long> implements IContatoTelefonicoService<T>{

	@Autowired
	private IContatoTelefonicoRepository<T> contatoTelefonicoRepository;
	
	@Autowired
	private Validator validator;
	

	@Override
	public IRepository<T, Long> getRepository() {
		return contatoTelefonicoRepository;
	}
	
	@Override
	public void excluir(Long idContatoTelefonico) {
		ContatoTelefonico contatoTelefonico = contatoTelefonicoRepository.findById(idContatoTelefonico).orElse(null);
		if(contatoTelefonico == null){
			throw new BusinessException("erro.contatotelefonico.nao_localizado");
		}
		contatoTelefonicoRepository.excluirLogicamente(contatoTelefonico.getId());
	}

	@Override
	public void validar(ContatoTelefonico contato) {
		List<CampoMensagem> campos = 
				new ConstraintViolationTransformer(validator.validate(contato)).transform();
		
		if(CollectionUtils.isNotEmpty(campos)){
			throw new ValidationException("erro.validacao", campos);
		}
		
	}

	@Override
	public ContatoTelefonico obterContatoTelefonico(Long id) {
		return contatoTelefonicoRepository.findById(id).get();
	}

}
