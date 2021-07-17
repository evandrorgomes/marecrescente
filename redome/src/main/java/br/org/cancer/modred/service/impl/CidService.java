package br.org.cancer.modred.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Cid;
import br.org.cancer.modred.persistence.ICidRepository;
import br.org.cancer.modred.service.ICidService;

/**
 * Classe para de negócios para CID.
 * 
 * @author filipe.souza
 *
 */
@Service
@Transactional
public class CidService implements ICidService {

	@Autowired
	private ICidRepository cidRepository;

	/**
	 * Método que faz chamada de Repositório e realiza a consulta de CIDs.
	 * 
	 * @param texto de parametro para pesquisa
	 */
	@Override
	public List<Cid> listarCidPorCodigoOuDescricao(String textoPesquisa) {
		if (StringUtils.isNotBlank(textoPesquisa)) {
			textoPesquisa = "%" + textoPesquisa + "%";
		}
		else {
			throw new BusinessException("erro.cid.consulta.nulo");
		}
		return cidRepository.findByCodigoOrDescricao(textoPesquisa, LocaleContextHolder.getLocale().getLanguage());
	}

	/**
	 * Método para recuperar um CID através do ID.
	 */
	@Override
	public Cid findById(Long id) {
		return cidRepository.findById(id).get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cid obterCidPeloCodigo(String codigo) {
		return cidRepository.findByCodigo(codigo);
	}
}
