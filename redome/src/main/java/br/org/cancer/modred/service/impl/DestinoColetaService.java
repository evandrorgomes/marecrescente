package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.DestinoColeta;
import br.org.cancer.modred.persistence.IDestinoColetaRepository;
import br.org.cancer.modred.service.IDestinoColetaService;


/**
 * Implementação dos métodos de negócio de DestinoColeta.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class DestinoColetaService implements IDestinoColetaService {

	@Autowired
	private IDestinoColetaRepository destinoColetaRepository;
	
	
	@Override
	public List<DestinoColeta> listarDestinos() {
		return destinoColetaRepository.findAll();
	}

}
