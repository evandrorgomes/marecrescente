package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.correio.Bairro;
import br.org.cancer.modred.model.correio.Localidade;
import br.org.cancer.modred.model.correio.Logradouro;
import br.org.cancer.modred.model.correio.TipoLogradouro;
import br.org.cancer.modred.model.correio.UnidadeFederativa;
import br.org.cancer.modred.persistence.IBairroCorreioRepository;
import br.org.cancer.modred.persistence.ILocalidadeRepository;
import br.org.cancer.modred.persistence.ILogradouroCorreioRepository;
import br.org.cancer.modred.persistence.ITipoLogradouroCorreioRepository;
import br.org.cancer.modred.persistence.IUnidadeFederativaRepository;
import br.org.cancer.modred.service.ICorreioService;

/**
 * Classe que implementa o serviço do correio.
 * 
 * @author Bruno Sousa
 *
 */
@Service
@Transactional
public class CorreioService implements ICorreioService {

	@Autowired
	private IUnidadeFederativaRepository unidadeFederativaCorreioRepository;

	@Autowired
	private ILocalidadeRepository localidadeCorreioRepository;

	@Autowired
	private IBairroCorreioRepository bairroCorreioRepository;

	@Autowired
	private ILogradouroCorreioRepository logradouroCorreioRepository;

	@Autowired
	private ITipoLogradouroCorreioRepository tipoLogradouroCorreioRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UnidadeFederativa> listarUnidadeFederativa(String query) {
		if (StringUtils.isEmpty(query)) {
			return unidadeFederativaCorreioRepository.findAll();
		}
		else {
			return unidadeFederativaCorreioRepository.findBySiglaContaining(query.toUpperCase());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Localidade> listarLocalidadePorUnidadeFederativa(String siglaUnidadeFederativa, String query) {
		if (siglaUnidadeFederativa != null && StringUtils.isEmpty(siglaUnidadeFederativa)) {
			return new ArrayList<Localidade>();
		}
		if (StringUtils.isEmpty(query)) {
			return localidadeCorreioRepository.findByUnidadeFederativaSigla(siglaUnidadeFederativa.toUpperCase());
		}
		else {
			return localidadeCorreioRepository.findByUnidadeFederativaSiglaAndNomeContaining(siglaUnidadeFederativa.toUpperCase(),
					query.toUpperCase());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Bairro> listarBairroPorLocalidade(String sigla, String localidade, String query) {
		if (sigla != null && StringUtils.isEmpty(sigla)) {
			return new ArrayList<Bairro>();
		}
		if (localidade != null && StringUtils.isEmpty(localidade)) {
			return new ArrayList<Bairro>();
		}

		if (StringUtils.isEmpty(query)) {
			return bairroCorreioRepository.findByLocalidadeUnidadeFederativaSiglaAndLocalidadeNome(sigla.toUpperCase(), localidade
					.toUpperCase());
		}
		else {
			return bairroCorreioRepository.findByLocalidadeUnidadeFederativaSiglaAndLocalidadeNomeAndNomeContaining(sigla
					.toUpperCase(), localidade.toUpperCase(), query.toUpperCase());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Logradouro> listarLogradouroPorBairro(String sigla, String localidade, String bairro, String query) {
		if (sigla != null && StringUtils.isEmpty(sigla)) {
			return new ArrayList<Logradouro>();
		}
		if (localidade != null && StringUtils.isEmpty(localidade)) {
			return new ArrayList<Logradouro>();
		}
		if (bairro != null && StringUtils.isEmpty(bairro)) {
			return new ArrayList<Logradouro>();
		}

		if (!StringUtils.isEmpty(query)) {
			return logradouroCorreioRepository
					.findByBairroInicialNomeAndBairroInicialLocalidadeNomeAndBairroInicialLocalidadeUnidadeFederativaSiglaAndNomeContaining(
							bairro.toUpperCase(), localidade.toUpperCase(), sigla.toUpperCase(), query.toUpperCase());
		}

		return new ArrayList<Logradouro>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Logradouro obterCep(String cep) {
		// TODO ao entrar a base correta dos correios deverá exibir mensagem de
		// erro caso o cep digitado seja inválido
		if (cep != null && StringUtils.isEmpty(cep)) {
			return null;
		}
		return logradouroCorreioRepository.findByCep(cep);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TipoLogradouro> listarTipoLogradouro(String query) {
		if (query == null || ( query != null && StringUtils.isEmpty(query) )) {
			return tipoLogradouroCorreioRepository.findAll();
		}
		return tipoLogradouroCorreioRepository.findByNomeContaining(query.toUpperCase());
	}
}
