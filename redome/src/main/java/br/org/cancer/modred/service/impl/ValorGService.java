package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ValorG;
import br.org.cancer.modred.model.ValorGPK;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGRepository;
import br.org.cancer.modred.service.IValorGService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe para métodos de negócio de ValorG.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ValorGService extends AbstractService<ValorG, ValorGPK>  implements IValorGService {

	@Autowired
	private IValorGRepository valorGRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String obterGrupo(String locusCodigo, String nomeGrupo) {
		final ValorG valorG = valorGRepository.obterValorG(locusCodigo, nomeGrupo);
		if (valorG == null) {
			throw new BusinessException("erro.grupo.g.nao.encontrado");
		}
		return valorG.getGrupo().replaceAll("/", " ");
	}

	@Override
	public IRepository<ValorG, ValorGPK> getRepository() {		
		return valorGRepository;
	}
	
}
