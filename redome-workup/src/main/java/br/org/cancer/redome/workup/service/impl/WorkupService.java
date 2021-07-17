package br.org.cancer.redome.workup.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ConsultaTarefasWorkupDTO;
import br.org.cancer.redome.workup.dto.FiltroListaTarefaWorkupDTO;
import br.org.cancer.redome.workup.dto.FiltroListaTarefaWorkupDTO.FiltroListaTarefaWorkupDTOBuilder;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.security.Usuario;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.IWorkupService;
import br.org.cancer.redome.workup.util.CustomPageImpl;

/**
 * Classe de acesso as funcionalidades que agrupam os serviços de workup.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class WorkupService implements IWorkupService {
		
	private static final Long PAPEL_COLETOR = 2L;
	private static final Long PAPEL_TRANSPLANTADOR = 3L; 
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	
//	@Autowired
//	@Lazy(true)
//	private ITarefaHelper tarefaHelper;
	
//	@Autowired
//	private IPrescricaoService prescricaoService;
	
//	@Autowired
//	@Lazy(true)
//	private ISolicitacaoFeign solicitacaoFeign;
	
//	@Autowired
//	@Lazy(true)
//	private IMedicoFeign medicoFeign;
	
//	@Autowired
//	@Lazy(true)
//	private IPerfilFeign perfilFeign;
	
//	@Autowired
//	@Lazy(true)
//	private IUsuarioFeign usuarioFeign;
	
//	@Autowired
//	private IPedidoColetaService pedidoColetaService;
	
//	@Autowired
//	private IPedidoAdicionalWorkupService pedidoAdicionalWorkupService;
	
//	@Autowired
//	private IResultadoWorkupService resultadoWorkupService; 
	
//	@Autowired
//	private IPedidoLogisticaDoadorWorkupService pedidoLogisticaDoadorWorkupService;

//	@Autowired
//	private IPedidoLogisticaDoadorColetaService pedidoLogisticaDoadorColetaService;
	
//	@Autowired
//	private IPedidoLogisticaMaterialColetaInternacionalService pedidoLogisticaColetaService;
	
//	@Autowired
//	private MessageSource messageSource;


	@Override
	public Page<ConsultaTarefasWorkupDTO> listarTarefasWorkupView(Long idCentroSelecionado,
			Long idFuncaoCentro, int pagina, int quantidadeRegistros) throws JsonProcessingException {
	
		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();

		if ((usuarioLogado.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name())
				|| usuarioLogado.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) 
				&& idCentroSelecionado == null  ) {
				return new CustomPageImpl<>();
		}
		
		FiltroListaTarefaWorkupDTOBuilder filtro = FiltroListaTarefaWorkupDTO.builder();
		filtro.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()));
		
		if (PAPEL_TRANSPLANTADOR.equals(idFuncaoCentro)) {
			filtro.idCentroTransplante(idCentroSelecionado);					
		}
		else if (PAPEL_COLETOR.equals(idFuncaoCentro)) {
			filtro.idCentroColeta(idCentroSelecionado);					
		}

		/* ANALISTA DE WORKUP NACIONAL  PRECISA PASSAR COMO PARÂMETRO O USUA_ID E USUA_ID_RESPONSAVEL*/
		if (usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_WORKUP.name())
				|| usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA.name())){
			
			filtro.perfilResponsavel(Arrays.asList(Perfis.ANALISTA_WORKUP.getId(),Perfis.ANALISTA_LOGISTICA.getId()));
			filtro.idUsuario(usuarioLogado.getId());

		/* ANALISTA DE WORKUP INTERNACIONAL PRECISA PASSAR COMO PARÂMETRO O USUA_ID*/
		}else if (usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())
				|| usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.name())) {

			filtro.perfilResponsavel(Arrays.asList(Perfis.ANALISTA_WORKUP_INTERNACIONAL.getId(),Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.getId()));
			filtro.idUsuario(usuarioLogado.getId());
			
		/* MEDICO_TRANSPLANTADOR PRECISA PASSAR COMO PARÂMETRO O CETR_ID_TRANSPLANTE */
		}else if (usuarioLogado.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name())) {

			filtro.perfilResponsavel(Arrays.asList(Perfis.MEDICO_TRANSPLANTADOR.getId()));

		/* MEDICO_CENTRO_COLETA PRECISA PASSAR COMO PARÂMETRO O CETR_ID_COLETA */
		}else if (usuarioLogado.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) {

			filtro.perfilResponsavel(Arrays.asList(Perfis.MEDICO_CENTRO_COLETA.getId()));

		}
	
		
		List<ConsultaTarefasWorkupDTO> tarefas = this.pedidoWorkupService.listarTarefasWorkupView(filtro.build());
		if (CollectionUtils.isEmpty(tarefas)) {
			return new CustomPageImpl<>();
		}
		
		List<ConsultaTarefasWorkupDTO> lista = tarefas.stream()
			.skip(pagina * quantidadeRegistros)
			.limit(quantidadeRegistros)
			.collect(Collectors.toList());
		
		return new CustomPageImpl<ConsultaTarefasWorkupDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), tarefas.size());

	}



	@Override
	public Page<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkupView(Long idCentroSelecionado,
			Long idFuncaoCentro, int pagina, int quantidadeRegistros) throws JsonProcessingException {

		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		if ((usuarioLogado.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name())
			|| usuarioLogado.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) 
			&& idCentroSelecionado == null  ) {
			return new CustomPageImpl<>();
		}

		FiltroListaTarefaWorkupDTOBuilder filtro = FiltroListaTarefaWorkupDTO.builder();
		filtro.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()));
		
		if (PAPEL_TRANSPLANTADOR.equals(idFuncaoCentro)) {
			filtro.idCentroTransplante(idCentroSelecionado);					
		}
		else if (PAPEL_COLETOR.equals(idFuncaoCentro)) {
			filtro.idCentroColeta(idCentroSelecionado);					
		}

		/* ANALISTA DE WORKUP NACIONAL  PRECISA PASSAR COMO PARÂMETRO O USUA_ID E USUA_ID_RESPONSAVEL*/
		if (usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_WORKUP.name())
				|| usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA.name())){
			
			filtro.perfilResponsavel(Arrays.asList(Perfis.ANALISTA_WORKUP.getId(),Perfis.ANALISTA_LOGISTICA.getId()));
			filtro.idUsuario(usuarioLogado.getId());
			filtro.idUsuarioResponsavel(usuarioLogado.getId());

		/* ANALISTA DE WORKUP INTERNACIONAL PRECISA PASSAR COMO PARÂMETRO O USUA_ID*/
		}else if (usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())
				|| usuarioLogado.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.name())) {

			filtro.perfilResponsavel(Arrays.asList(Perfis.ANALISTA_WORKUP_INTERNACIONAL.getId(),Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.getId()));
			filtro.idUsuario(usuarioLogado.getId());
			filtro.idUsuarioResponsavel(usuarioLogado.getId());
			
		/* MEDICO_TRANSPLANTADOR PRECISA PASSAR COMO PARÂMETRO O CETR_ID_TRANSPLANTE */
		}else if (usuarioLogado.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name())) {

			filtro.perfilResponsavel(Arrays.asList(Perfis.MEDICO_TRANSPLANTADOR.getId()));

		/* MEDICO_CENTRO_COLETA PRECISA PASSAR COMO PARÂMETRO O CETR_ID_COLETA */
		}else if (usuarioLogado.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) {

			filtro.perfilResponsavel(Arrays.asList(Perfis.MEDICO_CENTRO_COLETA.getId()));

		}
		
		List<ConsultaTarefasWorkupDTO> solicitacoes = this.pedidoWorkupService.listarSolicitacoesWorkupView(filtro.build());
		if (CollectionUtils.isEmpty(solicitacoes)) {
			return new CustomPageImpl<>();
		}

		List<ConsultaTarefasWorkupDTO> lista = solicitacoes.stream()
				.skip(pagina * quantidadeRegistros)
				.limit(quantidadeRegistros)
				.collect(Collectors.toList());
		
		
		return new CustomPageImpl<ConsultaTarefasWorkupDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), solicitacoes.size());
	}

	
/*	
	
	@Override
	public Page<ConsultaTarefasWorkupDTO> listarTarefasWorkup(Long idCentroTransplante, Long idFuncaoCentro, int pagina, int quantidadeRegistros) throws JsonProcessingException {
		
		
		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();

		List<TarefaDTO> tarefas = CarregaListaTarefas(idCentroTransplante, idFuncaoCentro, usuarioLogado);
		if (CollectionUtils.isEmpty(tarefas)) {
			return new CustomPageImpl<>();
		}
		
		List<ConsultaTarefasWorkupDTO> lista = tarefas.stream()
			.sorted(new TarefaComparator())
			.skip(pagina * quantidadeRegistros)
			.limit(quantidadeRegistros)
			.map(tarefa -> {return popularConsultaTarefasWorkupPorTarefa(tarefa);})
			.collect(Collectors.toList());
	
		return new CustomPageImpl<ConsultaTarefasWorkupDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), tarefas.size());
	}
	
*/	
	/**
	 * @param tarefa
	 * @return
	 */
/*
	
	public ConsultaTarefasWorkupDTO popularConsultaTarefasWorkupPorTarefa(TarefaDTO tarefa) {
		ISolicitacao relacaoEntidade = obterRelacaoEntidade(tarefa.getTipoTarefa(), tarefa.getRelacaoEntidade());
		
		Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(relacaoEntidade.getSolicitacao());
		
		MedicoDTO medico = medicoFeign.obterMedico(prescricao.getMedico());
		
		SolicitacaoDTO solicitacao = solicitacaoFeign.obterSolicitacao(relacaoEntidade.getSolicitacao());
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String nomePaciente = solicitacao.getMatch().getBusca().getPaciente().getNome();
		final LocalDate dataNascimentoPaciente = solicitacao.getMatch().getBusca().getPaciente().getDataNascimento();
		final Long idCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getId();
		final String nomeCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getNome();
		final String tipoPrescricao = prescricao.isPrescricaoMedula() ? "Medula" : "Cordão";
		final Long idTipoPrescricao = prescricao.getTipoPrescricao();
		final String acao = AppUtil.getMensagem(messageSource, FasesWorkup.valueOf(solicitacao.getFaseWorkup()).getMessageId());
		
		String nomeUsuarioResponsavelTarefa = null;
		if (tarefa.getUsuarioResponsavel() != null) {
			final UsuarioDTO usuario = usuarioFeign.obterUsuarios(tarefa.getUsuarioResponsavel());
			nomeUsuarioResponsavelTarefa = usuario.getNome();
		}
		
							
		return ConsultaTarefasWorkupDTO.builder()
				.idTarefa(tarefa.getId())
				.idTipoTarefa(tarefa.getTipoTarefa().getId())
				.statusTarefa(tarefa.getStatus())
			//	.idRelacaoEntidade(tarefa.getRelacaoEntidade())
				.idPrescricao(prescricao.getId())
				.rmr(rmr)
				.idCentroTransplante(idCentroTransplante)
				.nomeCentroTransplante(nomeCentroTransplante)
				.medicoResponsavelPrescricao(medico.getNome())
				.tipoPrescricao(tipoPrescricao)
				.idTipoPrescricao(idTipoPrescricao)
				.idDoador(solicitacao.getMatch().getDoador().getId())
				.identificacaoDoador(solicitacao.getMatch().getDoador().getIdentificacao())
				.registroOrigem(solicitacao.getMatch().getDoador().getRegistroOrigem().getNome())
				.dataPrescricao(prescricao.getDataCriacao())
//							.dataColeta(relacaoEntidade.getDataColeta())
//							.dataWorkup(relacaoEntidade.getDataCriacao())
				.acao(acao)
				.nomeUsuarioResponsavelTarefa(nomeUsuarioResponsavelTarefa)
				.nomePaciente(nomePaciente)
				.dataNascimentoPaciente(dataNascimentoPaciente)
				.build();
	}
		
	private ISolicitacao obterRelacaoEntidade(TipoTarefaDTO tipoTarefa, Long relacaoEntidade) {
		if (TiposTarefa.CADASTRAR_FORMULARIO_DOADOR.getId().equals(tipoTarefa.getId())
			||	TiposTarefa.DEFINIR_CENTRO_COLETA.getId().equals(tipoTarefa.getId())
			||	TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL.getId().equals(tipoTarefa.getId())
			||	TiposTarefa.INFORMAR_PLANO_WORKUP_INTERNACIONAL.getId().equals(tipoTarefa.getId())
			||	TiposTarefa.CONFIRMAR_PLANO_WORKUP.getId().equals(tipoTarefa.getId())
			||	TiposTarefa.INFORMAR_DETALHE_WORKUP_NACIONAL.getId().equals(tipoTarefa.getId())
			||	TiposTarefa.INFORMAR_RESULTADO_WORKUP_INTERNACIONAL.getId().equals(tipoTarefa.getId())
			||  TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getId().equals(tipoTarefa.getId())
			||  TiposTarefa.CADASTRAR_COLETA_CONTAGEM_CELULA.getId().equals(tipoTarefa.getId())
			||  TiposTarefa.INFORMAR_FORMULARIO_POSCOLETA.getId().equals(tipoTarefa.getId())){			
		
			return pedidoWorkupService.obterPedidoWorkup(relacaoEntidade);
		
		}else if (TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP.getId().equals(tipoTarefa.getId())) {
			return pedidoLogisticaDoadorWorkupService.obterPedidoLogisticaEmAberto(relacaoEntidade);
		}else if (TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL.getId().equals(tipoTarefa.getId())){
			return pedidoLogisticaColetaService.obterPedidoLogisticaEmAberto(relacaoEntidade);
		}else if (TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL.getId().equals(tipoTarefa.getId())) {
			ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupNacional(relacaoEntidade);
			return resultadoWorkup.getPedidoWorkup();
		}else if (TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.getId().equals(tipoTarefa.getId())) {
			ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupInternacional(relacaoEntidade);
			return resultadoWorkup.getPedidoWorkup();
		}
		else if (TiposTarefa.INFORMAR_AUTORIZACAO_PACIENTE.getId().equals(tipoTarefa.getId())) {
			return prescricaoService.obterPrescricao(relacaoEntidade);
		}
		else if (TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL.getId().equals(tipoTarefa.getId()) 
				|| TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_INTERNACIONAL.getId().equals(tipoTarefa.getId())) {
			PedidoAdicionalWorkup pedidoAdicionalWorkup = pedidoAdicionalWorkupService.obterPedidoAdicionalWorkup(relacaoEntidade);
			return pedidoWorkupService.obterPedidoWorkup(pedidoAdicionalWorkup.getPedidoWorkup());
		}
		else if (TiposTarefa.AGENDAR_COLETA_NACIONAL.getId().equals(tipoTarefa.getId()) 
			|| 	TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL.getId().equals(tipoTarefa.getId())
			||  TiposTarefa.INFORMAR_RECEBIMENTO_COLETA.getId().equals(tipoTarefa.getId())) {
			PedidoColeta pedidoColeta = pedidoColetaService.obterPedidoColetaPorId(relacaoEntidade);
			return pedidoWorkupService.obterPedidoWorkupPorSolicitacao(pedidoColeta.getSolicitacao());
		}else if(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL.getId().equals(tipoTarefa.getId())
				|| TiposTarefa.INFORMAR_LOGISTICA_DOADOR_COLETA.getId().equals(tipoTarefa.getId())) {
			return pedidoLogisticaDoadorColetaService.obterPedidoLogisticaDoadorColetaEmAberto(relacaoEntidade);
		}
		
		throw new BusinessException("");
	}
	
	@Override
	public Page<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkup(Long idCentroTransplante, Long idFuncaoCentro, int pagina, int quantidadeRegistros) throws JsonProcessingException {
		
		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
						
		List<TarefaDTO> tarefas = CarregaListaTarefas(idCentroTransplante, idFuncaoCentro, usuarioLogado);
		
		String[] tiposSolicitacao = (String[]) listarTposSolicitacaoPorUsuario(usuarioLogado).toArray(new String[0]);
		String[] statusSolicitacao = {StatusSolicitacao.ABERTA.getId().toString()};

		List<SolicitacaoWorkupDTO> solicitacoes = solicitacaoFeign.listarSolicitacoes(tiposSolicitacao, statusSolicitacao);
		if (CollectionUtils.isEmpty(solicitacoes)) {
			return new CustomPageImpl<>();
		}

		List<ConsultaTarefasWorkupDTO> lista = solicitacoes.stream()
			.filter(solicitacao -> {
				Boolean anyMatch = tarefas.stream().anyMatch(tarefa -> {
					ISolicitacao relacaoEntidade = obterRelacaoEntidade(tarefa.getTipoTarefa(), tarefa.getRelacaoEntidade());	
					return solicitacao.getId().equals(relacaoEntidade.getSolicitacao());
				});
				if (anyMatch) {
					return false;
				}
				else {					
					if (PAPEL_TRANSPLANTADOR.equals(idFuncaoCentro)) {
						return idCentroTransplante.equals(solicitacao.getIdCentroTransplante());					
					}
					else if (PAPEL_COLETOR.equals(idFuncaoCentro)) {
						return idCentroTransplante.equals(solicitacao.getIdCentroColeta());					
					}
				}
				return true;
			}) 
			.skip(pagina * quantidadeRegistros)
			.limit(quantidadeRegistros)
			.map(solicitacao -> {return popularConsultaTarefasWorkupPorSolicitacao(solicitacao);}).collect(Collectors.toList());
	
		return new CustomPageImpl<ConsultaTarefasWorkupDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), solicitacoes.size());
	}
*/
	/**
	 * @param solicitacao
	 * @return
	 */
/*
	public ConsultaTarefasWorkupDTO popularConsultaTarefasWorkupPorSolicitacao(SolicitacaoWorkupDTO solicitacao) {
		Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(solicitacao.getId());
		
		MedicoDTO medico = medicoFeign.obterMedico(prescricao.getMedico());

		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String nomeCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getNome();
		final String tipoPrescricao = prescricao.isPrescricaoMedula() ? "Medula" : "Cordão";
		final String acao = AppUtil.getMensagem(messageSource, FasesWorkup.valueOf(solicitacao.getFaseWorkup()).getMessageId());
							
		return ConsultaTarefasWorkupDTO.builder()
				.idPrescricao(prescricao.getId())
				.rmr(rmr)
				.nomeCentroTransplante(nomeCentroTransplante)
				.medicoResponsavelPrescricao(medico.getNome())
				.tipoPrescricao(tipoPrescricao)
				.identificacaoDoador(solicitacao.getMatch().getDoador().getIdentificacao())
				.registroOrigem(solicitacao.getMatch().getDoador().getRegistroOrigem().getNome())
				.dataPrescricao(prescricao.getDataCriacao())
//							.dataColeta(relacaoEntidade.getDataColeta())
//							.dataWorkup(relacaoEntidade.getDataWorkup())
				.acao(acao)
				.build();
	}
	
	private List<String> listarTposSolicitacaoPorUsuario(Usuario usuario) {
		List<String> tiposSolicitacao = new ArrayList<>();
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP.name())) {
			tiposSolicitacao.add(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().toString());		
			tiposSolicitacao.add(TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId().toString());
		}
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())) {
			tiposSolicitacao.add(TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().toString());		
			tiposSolicitacao.add(TiposSolicitacao.CORDAO_INTERNACIONAL.getId().toString());		
		}
		if (usuario.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())
				|| usuario.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name()) ) {
			tiposSolicitacao.add(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().toString());		
			tiposSolicitacao.add(TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().toString());
		}
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA.name())) {
			tiposSolicitacao.add(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().toString());		
		}
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.name())) {
			tiposSolicitacao.add(TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().toString());		
		}
		
		if (tiposSolicitacao.isEmpty()) {
			throw new BusinessException("erro.acesso.negado");
		}
		return tiposSolicitacao;
	}
*/
	
	/**
	 * @param usuarioLogado
	 * @return
	 * @throws JsonProcessingException
	 */

	/*
	public List<TarefaDTO> CarregaListaTarefas(Long idCentroTransplante, Long idFuncaoCentro, Usuario usuarioLogado) throws JsonProcessingException {
		
		ListaTarefaDTOBuilder filtro = ListaTarefaDTO.builder()
		.idUsuarioLogado(usuarioLogado.getId())
		.statusTarefa(listarStatusTarefaPorUsuario(usuarioLogado))
		.perfilResponsavel(listarPerfisPorUsuario(usuarioLogado))
		.idsTiposTarefa(listarTiposTarefaPorFuncaoCentroEUsuario(idFuncaoCentro, usuarioLogado))
		.parceiros(idCentroTransplante != null ? Arrays.asList(idCentroTransplante) : null);

		List<TarefaDTO> tarefas = tarefaHelper.listarTarefas(filtro.build());

		if (CollectionUtils.isEmpty(tarefas)) {
			return new ArrayList<>();
		}
		
		if (usuarioLogado.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name()) || 
				usuarioLogado.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) {
			List<TarefaDTO> listaFiltrada = tarefas.stream()
			.filter(tarefa -> {
				ISolicitacao solicitacao = obterRelacaoEntidade(tarefa.getTipoTarefa(), tarefa.getRelacaoEntidade());
				if(idFuncaoCentro.equals(PAPEL_TRANSPLANTADOR)) {
					return solicitacao.getIdCentroTransplante().equals(idCentroTransplante);
				}else if(idFuncaoCentro.equals(PAPEL_COLETOR)) {
					return solicitacao.getIdCentroColeta().equals(idCentroTransplante);
				}
				return false;
			}).map(tarefa -> {return tarefa;}).collect(Collectors.toList());

			return listaFiltrada;
		}
		
		return tarefas;
	}

	private List<Long> listarTiposTarefaPorFuncaoCentroEUsuario(Long idFuncaoCentro, Usuario usuario) {
		List<Long> idsTiposTarefa = new ArrayList<>();
		
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP.name()) || 
			usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())) {
			idsTiposTarefa.add(TiposTarefa.WORKUP.getId());
		}
		if (usuario.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name()) || 
				usuario.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) {
			if (PAPEL_TRANSPLANTADOR.equals(idFuncaoCentro)) {
				idsTiposTarefa.add(TiposTarefa.WORKUP_CENTRO_TRANSPLANTE.getId());
			}
			if (PAPEL_COLETOR.equals(idFuncaoCentro)) {
				idsTiposTarefa.add(TiposTarefa.WORKUP_CENTRO_COLETA.getId());
			}
		}
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA.name())) {
			idsTiposTarefa.add(TiposTarefa.WORKUP.getId());
		}
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.name())) {
			idsTiposTarefa.add(TiposTarefa.LOGISTICA.getId());
		}
				
		if (idsTiposTarefa.isEmpty()) {
			idsTiposTarefa.add(null);
		}
		return idsTiposTarefa;
	}

	private List<Long> listarPerfisPorUsuario(Usuario usuario) {
		
		final List<Long> perfis = usuario.getAuthorities().stream()
			.filter(authority -> authority.equals(Perfis.ANALISTA_WORKUP.name()) 
					|| authority.equals(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())
					|| authority.equals(Perfis.MEDICO_TRANSPLANTADOR.name())
					|| authority.equals(Perfis.MEDICO_CENTRO_COLETA.name())
					|| authority.equals(Perfis.ANALISTA_LOGISTICA.name())
					|| authority.equals(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.name()))
			.map(authority -> Perfis.valueOf(authority).getId())
			.collect(Collectors.toList());
						
		if (perfis.isEmpty()) {
			throw new BusinessException("erro.acesso.negado");
		}
		return perfis;
	}

	private List<Long> listarStatusTarefaPorUsuario(Usuario usuario) {
		final List<Long> status = new ArrayList<>();
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP.name()) || 
			usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name()) ||
			usuario.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA.name()) ||
			usuario.getAuthorities().contains(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL.name()) ||
			usuario.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name()) || 
			usuario.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) {
			
			status.add(StatusTarefa.ATRIBUIDA.getId());
			
			if (usuario.getAuthorities().contains(Perfis.MEDICO_TRANSPLANTADOR.name()) || 
				usuario.getAuthorities().contains(Perfis.MEDICO_CENTRO_COLETA.name())) {			
				status.add(StatusTarefa.ABERTA.getId());
			}
		}
				
		if (CollectionUtils.isEmpty(status)) {
			throw new BusinessException("erro.acesso.negado");
		}
		
		return status;
		
	}
*/

}
