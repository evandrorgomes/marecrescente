package br.org.cancer.modred.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe de implementação da interface IExamePacienteService.java. O objetivo é
 * fornecer os acessos ao modelo da entidade ExamePaciente.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class ExecutarProcedureMatchService implements IExecutarProcedureMatchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutarProcedureMatchService.class);

	@Autowired
	private IMatchService matchService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ISolicitacaoService solicitacaoService;
	
	/**
	 * Construtor definido para que seja informado as estratégias para os eventos de
	 * criação de notificação.
	 */
	public ExecutarProcedureMatchService() {
		super();
	}

	@Override
	@Async
	@Transactional
	public void gerarMatchPaciente(Long rmr) {
	    //new Thread ( () -> {
//	    	try{
//				matchService.executarProcedureMatchPacientePorRmr(rmr);
//				
//			}catch(Exception e){
//				TiposTarefa.VERIFICAR_GERACAO_MATCH_DOADOR.getConfiguracao().criarTarefa()
//					.comObjetoRelacionado(rmr)
//					.comRmr(rmr)
//					.apply();
//		    }
//	    }).start();
	}
	
	
	@Override
	@Async
	@Transactional(propagation = Propagation.NESTED)
	public void gerarMatchDoador(Doador doador, PedidoDto pedido, Long rmr) {
	    ///new Thread ( () -> {
	    	try{
				matchService.executarProcedureMatchDoadorPorIdDoador(doador.getId());				
			}catch(Exception e) {
				TiposTarefa.VERIFICAR_GERACAO_MATCH_DOADOR.getConfiguracao().criarTarefa()
					.comObjetoRelacionado(doador.getId())
					.comDoadorId(doador.getId())
					.apply();
				
				String descricao = AppUtil.getMensagem(messageSource, "erro.execucao.match.doador", ((IDoador)doador).getIdentificacao().toString() );
				if (pedido != null) {
				  descricao += 	AppUtil.getMensagem(messageSource, "erro.execucao.match.doador.pedido"); 
				
				}
				CategoriasNotificacao.PEDIDO_EXAME.getConfiguracao().criar()
					.comDescricao(descricao)
					.comPaciente(rmr)
					.paraPerfil(Perfis.ANALISTA_BUSCA)
					.apply();
				
		    }
	    	criarPedidoExameCasoSejaSolicitado(doador, pedido, rmr);
	    //}).start();
	}
		
	private void criarPedidoExameCasoSejaSolicitado(Doador doador, PedidoDto pedido, Long rmr) {
		if (rmr != null && pedido != null) {
			Match match = matchService.obterMatchAtivo(rmr, doador.getId());
			if (match == null) {
				LOGGER.error("Paciente " + rmr + " não possui match com o doador (" + ((IDoador) doador).getIdentificacao().toString() + "). ");
				//throw new BusinessException("erro.solicitacao.match_inexistente");
			}
			if (pedido.getTipoExame() != null) {
				if (TiposExame.TIPIFICACAO_HLA_ALTA_RESOLUCAO.getId().equals(pedido.getTipoExame())) {
					solicitacaoService.solicitarFase2Internacional(match.getId(), pedido.getLocus());
				}
				else if (TiposExame.TESTE_CONFIRMATORIO.getId().equals(pedido.getTipoExame())) {
					solicitacaoService.solicitarFase3Internacional(match.getId());
				}
			}	
		}
	}
	

}
