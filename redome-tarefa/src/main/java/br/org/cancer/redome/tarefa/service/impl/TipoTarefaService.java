package br.org.cancer.redome.tarefa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.persistence.ITipoTarefaRepository;
import br.org.cancer.redome.tarefa.service.ITipoTarefaService;

@Service
@Transactional
public class TipoTarefaService implements ITipoTarefaService {
	
	@Autowired
	private ITipoTarefaRepository tipoTarefaRepository;

	@Override
	public TipoTarefa obterTipoTarefa(Long id) {
		return tipoTarefaRepository.findById(id).orElseThrow(() -> new BusinessException(""));
	}

}
