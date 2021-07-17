package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.EstadoCivil;
import br.org.cancer.modred.persistence.IEstadoCivilRepository;
import br.org.cancer.modred.service.IEstadoCivilService;



/**
 * Classe para metodos de negocio do Estado Civil.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class EstadoCivilService implements IEstadoCivilService {
	
	@Autowired
	private IEstadoCivilRepository estadoCivilRepository;

	/**
	 * Método para obter lista ordenada de Estados Civis.
	 * @Return List<EstadoCivil>
	 * */
	@Override
	public List<EstadoCivil> listarEstadosCivis() {
		return estadoCivilRepository.findByOrderByNomeAsc();
	}

	/**
	 * Método para obter um Estado Civil.
	 * @param Long id do Estado Civil
	 * @return EstadoCivil estadoCivil localizada
	 */
	@Override
	public EstadoCivil obterEstadoCivil(Long id) {
		return estadoCivilRepository.findById(id).get();
	}
}
