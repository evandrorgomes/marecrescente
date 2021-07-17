package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.TipoTransplante;
import br.org.cancer.modred.persistence.ITipoTransplanteRepository;
import br.org.cancer.modred.service.ITipoTransplanteService;

/**
 * Classe para metodos de negocio do tipo de transplante.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class TipoTransplanteService implements ITipoTransplanteService {

	@Autowired
	private ITipoTransplanteRepository tipoTransplanteRepository;

	/**
	 * Método para trazer todos os tipos de transplante.
	 */
	@Override
	@Cacheable(value = "dominio", key = "#root.methodName")
	public List<TipoTransplante> listarTipoTransplante() {
		return tipoTransplanteRepository.findAll();
	}

	/**
	 * Método para carregar motivo por id.
	 * 
	 * @param id id
	 * @return Motivo buscado por id
	 */
	@Override
	public TipoTransplante obterTipoTransplante(Long id) {
		return tipoTransplanteRepository.findById(id).get();
	}
}
