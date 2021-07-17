package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ValorNmdp;
import br.org.cancer.modred.persistence.IValorNmdpRepository;
import br.org.cancer.modred.service.IValorNmdpService;

/**
 * Classe para métodos de negócio de ValorNmdp.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ValorNmdpService implements IValorNmdpService {

	@Autowired
	private IValorNmdpRepository valorNmdpRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String obterSubTipos(String codigo) {
		final ValorNmdp valorNmdp = valorNmdpRepository.findByCodigo(codigo);
		if (valorNmdp == null) {
			throw new BusinessException("erro.codigo.nmdp.nao.encontrado");
		}
		return valorNmdp.getSubTipos().replaceAll("/", " ");
	}
	
}
