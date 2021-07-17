package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Etnia;
import br.org.cancer.modred.persistence.IEtniaRepository;
import br.org.cancer.modred.service.IEtniaService;

/**
 * Classe para metodos de negocio de etnia.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class EtniaService implements IEtniaService {

	@Autowired
	private IEtniaRepository etniaRepository;
	
	/**
	 * Método para obter lista ordenada de etnias.
	 * @Return List<Etnia>
	 * */	
	@Override
	public List<Etnia> listarEtnias() {
		return etniaRepository.findByOrderByNomeAsc();
	}

	/**
	 * Método para obter Etnia por ID.
	 * @param id
	 * @return Etnia etnia localiza
	 */
	@Override
	public Etnia getEtnia(Long id) {
		return etniaRepository.findById(id).get();
	}
}
