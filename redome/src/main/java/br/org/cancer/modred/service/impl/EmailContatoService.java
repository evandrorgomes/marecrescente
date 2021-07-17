package br.org.cancer.modred.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.EmailContato;
import br.org.cancer.modred.persistence.IEmailContatoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IEmailContatoService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de métodos de negócio de Email de Contato.
 * @param <T> extendendo de EmailContato.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class EmailContatoService<T extends EmailContato> extends AbstractService<T, Long> implements IEmailContatoService<T>{

	@Autowired
	private IEmailContatoRepository<T> emailContatoRepository;
	
	@Override
	public IRepository<T, Long> getRepository() {
		return emailContatoRepository;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(T entity) {
		return super.validateEntity(entity);
	}
	
	@Override
	public void excluir(Long idEmailContato) {
		EmailContato emailContato = emailContatoRepository.findById(idEmailContato).orElse(null);
		if(emailContato == null){
			throw new BusinessException("erro.emailcontato.nao_localizado");
		}
		
		update(Arrays.asList(new UpdateSet<Boolean>("excluido", true)), Arrays.asList(new Equals<Long>("id", idEmailContato)));
		
	}
	
}
