package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.AvaliacaoWorkupDoador;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IAvaliacaoWorkupDoadorRepository;
import br.org.cancer.modred.service.IAvaliacaoWorkupDoadorService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Implementacao da funcionalidades envolvendo a entidade RessalvaDoador.
 * 
 * @author Pizão.
 *
 */
@Service
@Transactional
public class AvaliacaoWorkupDoadorService  implements IAvaliacaoWorkupDoadorService {

	@Autowired
	private IAvaliacaoWorkupDoadorRepository avaliacaoWorkupDoadorRepository;

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IAvaliacaoWorkupDoadorService#finalizarAvaliacao(java.lang.Long)
	 */
	@Override
	public void finalizarAvaliacao(Long idAvaliacao) {
		AvaliacaoWorkupDoador avaliacaoWorkupDoador = avaliacaoWorkupDoadorRepository.findById(idAvaliacao).get();
		avaliacaoWorkupDoador.setDataConclusao(LocalDateTime.now());
		avaliacaoWorkupDoador.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(avaliacaoWorkupDoador.getResultadoWorkup().getPedidoWorkup());
		
		final Long rmr = pedidoWorkup.getSolicitacao()
				.getMatch().getBusca().getPaciente().getRmr();
		
		TiposTarefa.ANALISE_MEDICA_DOADOR_COLETA.getConfiguracao().fecharTarefa()
			.comRmr(rmr)
			.comObjetoRelacionado(avaliacaoWorkupDoador.getId())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(avaliacaoWorkupDoador.getUsuarioResponsavel())
			.apply();
	}

	@Override
	public void salvarAvaliacaoWorkup(AvaliacaoWorkupDoador avaliacao) {
		this.avaliacaoWorkupDoadorRepository.save(avaliacao);
	}


}
