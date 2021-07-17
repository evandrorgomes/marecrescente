package br.org.cancer.modred.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.MotivoStatusDoador;
import br.org.cancer.modred.persistence.IMotivoStatusDoadorRepository;
import br.org.cancer.modred.service.IMotivoStatusDoadorService;

/**
 * Classe de implementação dos métodos de negócio de motivo status doador.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class MotivoStatusDoadorService implements IMotivoStatusDoadorService {

	@Autowired
	private IMotivoStatusDoadorRepository motivoStatusDoadorRepository;

	@Override
	public List<MotivoStatusDoador> listarMotivosPorRecurso(String siglaRecurso) {
		if(StringUtils.isEmpty(siglaRecurso)){
			return motivoStatusDoadorRepository.findAll();
		}
		return motivoStatusDoadorRepository.findByRecursosSigla(siglaRecurso);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MotivoStatusDoador obterMotivoPorId(Long idMotivo) {
		return motivoStatusDoadorRepository.findById(idMotivo).get();
	}

}
