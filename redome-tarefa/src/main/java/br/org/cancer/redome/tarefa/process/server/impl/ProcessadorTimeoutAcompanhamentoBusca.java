package br.org.cancer.redome.tarefa.process.server.impl;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.tarefa.exception.BusinessException;
import br.org.cancer.redome.tarefa.feign.client.IBuscaFeign;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

/**
 * Processador de acompanhamento de busca.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ProcessadorTimeoutAcompanhamentoBusca implements IProcessadorTarefa {

	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	@Lazy(true)
	private IBuscaFeign buscaFeign;

	@Override
	public void processar(Tarefa tarefa) {
		Long tempoExecutando = SECONDS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		if (tempoExecutando >= tarefa.getTipoTarefa().getTempoExecucao()) {
			Tarefa tarefaAVerificar = processoService.obterTarefa(tarefa.getRelacaoEntidade());
			if (tarefaAVerificar == null) {
				throw new BusinessException("erro.mensagem.tarefa.associada.nao.encontrada", tarefa.getId().toString());
			}
			if (tarefaAVerificar.getStatus().equals(StatusTarefa.ATRIBUIDA.getId())) {
				processoService.removerAtribuicaoTarefa(tarefaAVerificar);
			}
			processoService.fecharTarefa(tarefa);
			Processo processo = tarefa.getProcesso();
			ResponseEntity<String> responseStatusBuscaLiberada = buscaFeign.alterarStatusParaLiberado(processo.getRmr());
			if(!responseStatusBuscaLiberada.getStatusCode().equals(HttpStatus.OK)) {
				throw new BusinessException("erro.mensagem.alteracao.status.busca");
			}
			ResponseEntity<String> responseAtribuicaoBusca = buscaFeign.removerAtribuicaoDeBusca(processo.getRmr());
			if(!responseAtribuicaoBusca.getStatusCode().equals(HttpStatus.OK)) {
				throw new BusinessException("erro.mensagem.remover.atribuicao.usuario");
			}
			
		}
	}

}
