package br.org.cancer.modred.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Municipio;
import br.org.cancer.modred.persistence.IMunicipioRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IMunicipioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.OrderBy;
import br.org.cancer.modred.service.impl.custom.AbstractService;
																					 
/**
 * Classe que implementa a interface de negócias para entidade municipio.
 * 
 * @author brunosousa
 *
 */
@Service
@Transactional
public class MunicipioService extends AbstractService<Municipio, Long> implements IMunicipioService {
	
	@Autowired
	private IMunicipioRepository municipioRepository;
	
	@Override
	public IRepository<Municipio, Long> getRepository() {
		return municipioRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Municipio obterMunicipioPorDescricao(String descricao) {
		Filter<String> filtrarPorDescricao = new Equals<>("descricao", descricao);

		return findOne(filtrarPorDescricao);				
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Municipio> listarMunicipiosOrdenadoPorDescricao() {
		return find(new OrderBy("descricao"));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Municipio> listarMunicipioPorUf(String sigla) {
		Filter<String> filtrarPorSiglaUf = new Equals<>("uf.sigla", sigla);
		return find(Arrays.asList(filtrarPorSiglaUf), new OrderBy("descricao"));
	}


}
