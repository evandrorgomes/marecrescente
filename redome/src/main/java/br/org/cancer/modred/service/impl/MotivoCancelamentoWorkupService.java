package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.MotivoCancelamentoWorkup;
import br.org.cancer.modred.persistence.IMotivoCancelamentoWorkupRepository;
import br.org.cancer.modred.service.IMotivoCancelamentoWorkupService;

/**
 * Classe de implementação dos métodos de negócio de motivo cancelamento workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class MotivoCancelamentoWorkupService implements IMotivoCancelamentoWorkupService {

	@Autowired
	private IMotivoCancelamentoWorkupRepository motivoStatusDoadorRepository;

	@Override
	public List<MotivoCancelamentoWorkup> listarMotivosCancelamentoWorkupSelecionaveis() {
		return motivoStatusDoadorRepository.findBySelecionavelOrderByDescricao(true);
	}

}
