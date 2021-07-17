package br.org.cancer.redome.tarefa.process.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IMatchFeign;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador de acompanhamento de geração do match associado ao paciente.
 * 
 * @author ergomes
 *
 */
@Service
public class ProcessadorGeracaoMatchPaciente implements IProcessadorTarefa {

	@Autowired
	private IProcessoService processoService;

	@Autowired
	@Lazy(true)
	private IMatchFeign matchFeign;
	
	@Override
	public void processar(Tarefa tarefa) {
		try {
			matchFeign.executarProcedureMatchPaciente(tarefa.getId());
			processoService.fecharTarefa(tarefa);
		}catch (Exception e) {
			throw new BusinessException("erro.mensagem.tarefa.associada.match_paciente_nao_gerado", tarefa.getProcesso().getRmr().toString());
		}
	}

}
