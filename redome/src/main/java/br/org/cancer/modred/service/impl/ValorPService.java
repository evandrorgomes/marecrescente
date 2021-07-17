package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ValorP;
import br.org.cancer.modred.model.ValorPPK;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorPRepository;
import br.org.cancer.modred.service.IValorPService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe para métodos de negócio de ValorG.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ValorPService extends AbstractService<ValorP, ValorPPK>  implements IValorPService {

	@Autowired
	private IValorPRepository valorPRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String obterGrupo(String locusCodigo, String nomeGrupo) {
		final ValorP valorP = valorPRepository.obterValorP(locusCodigo, nomeGrupo);
		if (valorP == null) {
			throw new BusinessException("erro.grupo.p.nao.encontrado");
		}
		return valorP.getGrupo().replaceAll("/", " ");
	}

	@Override
	public IRepository<ValorP, ValorPPK> getRepository() {		
		return valorPRepository;
	}
	
}
