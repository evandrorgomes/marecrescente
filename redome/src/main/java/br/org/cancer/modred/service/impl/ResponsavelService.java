package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.persistence.IResponsavelRepository;
import br.org.cancer.modred.service.IResponsavelService;

/**
 * Classe para metodos de negocio de responsavel.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ResponsavelService implements IResponsavelService {

	@Autowired
	private IResponsavelRepository responsavelRepository;

	@Override
	public void apagarResponsavel(Long id) {
		if (responsavelRepository.existsById(id)){
			responsavelRepository.deleteById(id);
		}
	}

}
