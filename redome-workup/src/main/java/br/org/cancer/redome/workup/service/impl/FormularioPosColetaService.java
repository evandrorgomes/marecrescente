package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;


import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.FormularioPosColeta;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IFormularioPosColetaRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IFormularioPosColetaService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade FormularioPosColeta.
 * 
 * @author elizabete.poly
 *
 */
@Service
@Transactional
public class FormularioPosColetaService extends AbstractService<FormularioPosColeta, Long> implements IFormularioPosColetaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FormularioPosColetaService.class);
	
	
	@Autowired
	private IFormularioPosColetaRepository formularioPosColetaRepository;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	private IUsuarioService usuarioService;
	
		
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Override
	public IRepository<FormularioPosColeta, Long> getRepository() {
		return formularioPosColetaRepository;
	}
		
	@Override
	public void finalizarFormularioPosColeta(Long idPedidoWorkup) throws JsonProcessingException {
		Long posColeta = 1L;
		
		FormularioPosColeta formularioPosColeta = FormularioPosColeta.builder()
				.dataAtualizacao(LocalDateTime.now())
				.dataCriacao(LocalDateTime.now())
				.usuarioResponsavel(usuarioService.obterIdUsuarioLogado())
				.pedidoWorkup(idPedidoWorkup)
				.build();
				
		save(formularioPosColeta);
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupEmAberto(idPedidoWorkup);				
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(pedidoWorkup.getSolicitacao());		
		solicitacao = solicitacaoFeign.atualizarSolicitacaoPosColeta(pedidoWorkup.getSolicitacao(), posColeta);		
		
		fecharTarefaFormularioPosColeta(idPedidoWorkup, solicitacao); 
	
	}
			
	private void fecharTarefaFormularioPosColeta(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_FORMULARIO_POSCOLETA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);		
	}					
}
