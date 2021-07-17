/**
 * 
 */
package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.CondicaoPaciente;
import br.org.cancer.modred.persistence.ICondicaoPacienteRepository;
import br.org.cancer.modred.service.ICondicaoPacienteService;

/**
 * Implementacao da classe de negocio para CondicaoPaciente.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class CondicaoPacienteService implements ICondicaoPacienteService {

	@Autowired
	private ICondicaoPacienteRepository condicaoPacienteRepository;

	/**
	 * Método para listar condições.
	 * 
	 * @return List<CondicaoPaciente> listagem de condições
	 */
	@Override
	public List<CondicaoPaciente> listarCondicoesPaciente() {
		return condicaoPacienteRepository.findByOrderByDescricaoAsc();
	}

	/**
	 * Buscar condicao paciente por id.
	 * 
	 * @param Long identicacao da condição
	 * @return CondicaoPaciente objeto de condicao paciente localizado
	 */
	@Override
	public CondicaoPaciente obterCondicaoPaciente(Long id) {
		return condicaoPacienteRepository.findById(id).get();
	}
}