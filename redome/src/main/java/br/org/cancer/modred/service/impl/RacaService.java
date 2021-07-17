package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Raca;
import br.org.cancer.modred.persistence.IRacaRepository;
import br.org.cancer.modred.service.IRacaService;



/**
 * Classe para metodos de negocio de raça.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class RacaService implements IRacaService {
	
	@Autowired
	private IRacaRepository racaRepository;

	/**
	 * Método para obter lista ordenada de raças.
	 * @Return List<Raca>
	 * */
	@Override
	public List<Raca> listarRacas() {
		return racaRepository.findByOrderByNomeAsc();
	}

	/**
	 * Método para obter uma raça.
	 * @param Long id da raça 
	 * @return Raca raça localizada
	 */
	@Override
	public Raca obterRaca(Long id) {
		return racaRepository.findById(id).get();
	}
}
