package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.DistribuicaoWorkupPorUsuarioDTO;
import br.org.cancer.redome.workup.dto.DistribuicoesWorkupDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.MedicoDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.IMedicoFeign;
import br.org.cancer.redome.workup.feign.client.IPerfilFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.feign.client.IUsuarioFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.helper.TarefaComparator;
import br.org.cancer.redome.workup.model.DistribuicaoWorkup;
import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusSolicitacao;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposDistribuicaoWorkup;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.model.security.Usuario;
import br.org.cancer.redome.workup.persistence.IDistribuicaoWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IDistribuicaoWorkupService;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IPrescricaoService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.CustomPageImpl;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade DistribuicaoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class DistribuicaoWorkupService extends AbstractService<DistribuicaoWorkup, Long> implements IDistribuicaoWorkupService {

	@Autowired
	private IDistribuicaoWorkupRepository distribuicaoWorkupRepository;
		
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	private IPrescricaoService prescricaoService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private IMedicoFeign medicoFeign;
	
	@Autowired
	@Lazy(true)
	private IPerfilFeign perfilFeign;
	
	@Autowired
	@Lazy(true)
	private IUsuarioFeign usuarioFeign;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	private IPedidoColetaService pedidoColetaService;

	@Override
	public IRepository<DistribuicaoWorkup, Long> getRepository() {
		return distribuicaoWorkupRepository;
	}
	
	@Override
	public void criarDistribuicao(SolicitacaoDTO solicitacao) {
		if (solicitacao.solicitacaoWorkupNacional()) {
			criarDistribuicaoWorkupNacional(solicitacao);
		}
		else {
			criarDistribuicaoWorkupInternacional(solicitacao);
		}
	}

	private void criarDistribuicaoWorkupNacional(SolicitacaoDTO solicitacao) {
		DistribuicaoWorkup distribuicao = DistribuicaoWorkup
				.builder()
				.tipo(TiposDistribuicaoWorkup.NACIONAL.getId())
				.solicitacao(solicitacao.getId())
				.build();
		
		save(distribuicao);
		
		criarTarefaDistribuicaoWorkupNacional(distribuicao.getId(), solicitacao);		
	}
	
	private void criarDistribuicaoWorkupInternacional(SolicitacaoDTO solicitacao) {
		DistribuicaoWorkup distribuicao = DistribuicaoWorkup
				.builder()
				.tipo(TiposDistribuicaoWorkup.INTERNACIONAL.getId())
				.solicitacao(solicitacao.getId())
				.build();
		
		save(distribuicao);
		
		criarTarefaDistribuicaoWorkupInternacional(distribuicao.getId(), solicitacao);
	}

	private void criarTarefaDistribuicaoWorkupNacional(Long idDistribuicaoWorkup, SolicitacaoDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
				
		TarefaDTO tarefa = criarTarefa(rmr, TiposTarefa.DISTRIBUIR_WORKUP_NACIONAL, Perfis.DISTRIBUIDOR_WORKUP_NACIONAL, idDistribuicaoWorkup); 
		
		tarefaHelper.criarTarefa(tarefa);
		
	}
	
	private void criarTarefaDistribuicaoWorkupInternacional(Long idDistribuicaoWorkup, SolicitacaoDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();		
		
		TarefaDTO tarefa = criarTarefa(rmr, TiposTarefa.DISTRIBUIR_WORKUP_INTERNACIONAL, Perfis.DISTRIBUIDOR_WORKUP_INTERNACIONAL, idDistribuicaoWorkup); 
		
		tarefaHelper.criarTarefa(tarefa);		
	}
	
	private TarefaDTO criarTarefa(Long rmr, TiposTarefa tipoTarefa, Perfis perfil, Long relacaoEntidade) {
		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);
		
		return TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(tipoTarefa.getId()))
				.perfilResponsavel(perfil.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoEntidade(relacaoEntidade)
				.build();
	}
	
	@Override
	public Page<DistribuicoesWorkupDTO> listarTarefasDisribuicoesWorkup(int pagina, int quantidadeRegistros) throws JsonProcessingException {
		
		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
			
		ListaTarefaDTO filtro = ListaTarefaDTO.builder()
				.idsTiposTarefa(listarTiposTarefaPorUsuario(usuarioLogado))
				.perfilResponsavel(listarPerfisPorUsuario(usuarioLogado))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioLogado.getId())
				.build();
		
		List<TarefaDTO> tarefas = tarefaHelper.listarTarefas(filtro);
		if (CollectionUtils.isEmpty(tarefas)) {
			return new CustomPageImpl<>();
		}
		
		List<DistribuicoesWorkupDTO> lista = tarefas.stream()
				.sorted(new TarefaComparator())
				.skip(pagina * quantidadeRegistros)
				.limit(quantidadeRegistros)
				.map(tarefa -> {
					DistribuicaoWorkup distribuicao = obterDistribuicaoWorkup(tarefa.getRelacaoEntidade());
					
					Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(distribuicao.getSolicitacao());
					
					MedicoDTO medico = medicoFeign.obterMedico(prescricao.getMedico());
					
					SolicitacaoDTO solicitacao = solicitacaoFeign.obterSolicitacao(distribuicao.getSolicitacao());
					final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
					final String nomeCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getNome();
					final String tipoPrescricao = prescricao.isPrescricaoMedula() ? "Medula" : "Cordão";
										
					return DistribuicoesWorkupDTO.builder()
							.idTarefaDistribuicaoWorkup(tarefa.getId())
							.statusTarefaDistribuicaoWorkup(tarefa.getStatus())
							.idDistribuicaoWorkup(distribuicao.getId())
							.rmr(rmr)
							.nomeCentroTransplante(nomeCentroTransplante)
							.medicoResponsavelPrescricao(medico.getNome())
							.tipoPrescricao(tipoPrescricao)
							.identificacaoDoador(solicitacao.getMatch().getDoador().getIdentificacao())
							.dataPrescricao(prescricao.getDataCriacao())
							.dataAvaliacaoPrescricao(distribuicao.getDataCriacao())
							.build();
				})
				.collect(Collectors.toList());
	
		return new CustomPageImpl<DistribuicoesWorkupDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), tarefas.size());
	}
	
	private DistribuicaoWorkup obterDistribuicaoWorkup(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		
		return distribuicaoWorkupRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Distribuição Workup"));
	}

	private List<Long> listarTiposTarefaPorUsuario(Usuario usuario) {
		List<Long> tiposTarefa = new ArrayList<Long>();
		if (usuario.getAuthorities().contains(Perfis.DISTRIBUIDOR_WORKUP_NACIONAL.name())) {
			tiposTarefa.add(TiposTarefa.DISTRIBUIR_WORKUP_NACIONAL.getId());		
		}
		if (usuario.getAuthorities().contains(Perfis.DISTRIBUIDOR_WORKUP_INTERNACIONAL.name())) {
			tiposTarefa.add(TiposTarefa.DISTRIBUIR_WORKUP_INTERNACIONAL.getId());		
		}
		if (tiposTarefa.isEmpty()) {
			throw new BusinessException("erro.acesso.negado");
		}
		return tiposTarefa;
		
	}
	
	private List<Long> listarPerfisPorUsuario(Usuario usuario) {
		List<Long> perfis = new ArrayList<Long>();
		if (usuario.getAuthorities().contains(Perfis.DISTRIBUIDOR_WORKUP_NACIONAL.name())) {
			perfis.add(Perfis.DISTRIBUIDOR_WORKUP_NACIONAL.getId());		
		}
		if (usuario.getAuthorities().contains(Perfis.DISTRIBUIDOR_WORKUP_INTERNACIONAL.name())) {
			perfis.add(Perfis.DISTRIBUIDOR_WORKUP_INTERNACIONAL.getId());		
		}
		if (perfis.isEmpty()) {
			throw new BusinessException("erro.acesso.negado");
		}
		return perfis;
		
	}
	
	@Override
	public Map<String, List<DistribuicaoWorkupPorUsuarioDTO>> listarDistribuicoesWorkupPorUsuario() {
		
		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		String[] tiposSolicitacao = (String[]) listarTposSolicitacaoPorUsuario(usuarioLogado).toArray(new String[0]);
		String[] statusSolicitacao = {StatusSolicitacao.ABERTA.getId().toString()};
		
		List<SolicitacaoWorkupDTO> solicitacoes = solicitacaoFeign.listarSolicitacoes(tiposSolicitacao, statusSolicitacao);
		if (CollectionUtils.isEmpty(solicitacoes)) {
			return null;
		}
		
		List<UsuarioDTO> usuarios = listarUsuariosPorPerfil(usuarioLogado);
		
		List<DistribuicaoWorkupPorUsuarioDTO> lista = solicitacoes.stream()
				.filter(solicitacao -> solicitacao.getUsuarioResponsavel() != null)
				.map(solicitacao -> {
			
			DistribuicaoWorkup distribuicao = obterDistribuicaoWorkupPorSolicitacao(solicitacao.getId());
			
			Prescricao prescricao = prescricaoService.obterPrescricaoPorSolicitacao(distribuicao.getSolicitacao());
			
			MedicoDTO medico = medicoFeign.obterMedico(prescricao.getMedico());
						
			final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
			final String nomeCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getNome();
			final String tipoPrescricao = prescricao.isPrescricaoMedula() ? "Medula" : "Cordão";
			
			final DistribuicoesWorkupDTO distribuicaoWorkupDTO = DistribuicoesWorkupDTO.builder()
					.idDistribuicaoWorkup(distribuicao.getId())
					.rmr(rmr)
					.nomeCentroTransplante(nomeCentroTransplante)
					.medicoResponsavelPrescricao(medico.getNome())
					.tipoPrescricao(tipoPrescricao)
					.identificacaoDoador(solicitacao.getMatch().getDoador().getIdentificacao())
					.dataPrescricao(prescricao.getDataCriacao())
					.dataAvaliacaoPrescricao(distribuicao.getDataCriacao())
					.build(); 			
			
			return DistribuicaoWorkupPorUsuarioDTO.builder()
					.nomeUsuario(solicitacao.getUsuarioResponsavel().getNome())
					.distribuicoesWorkup(distribuicaoWorkupDTO)
					.build();
		})
		.collect(Collectors.toList());
		
		usuarios.stream()
			.filter(usuario -> lista.stream()
					.noneMatch(distribuicaoWorkupPorUsuario -> distribuicaoWorkupPorUsuario.getNomeUsuario().equals(usuario.getNome())))
			.forEach(usuario -> {
				lista.add(DistribuicaoWorkupPorUsuarioDTO.builder()
						.nomeUsuario(usuario.getNome())						
						.build());
			});
		
		
		return lista.stream().collect(Collectors.groupingBy(DistribuicaoWorkupPorUsuarioDTO::getNomeUsuario));
	}
	
	private List<UsuarioDTO> listarUsuariosPorPerfil(Usuario usuario) {
		List<UsuarioDTO> usuarios = new ArrayList<>();
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP.name())) {
			usuarios.addAll(perfilFeign.listarUsuarios(Perfis.ANALISTA_WORKUP.getId(), null, null));
		}
		if (usuario.getAuthorities().contains(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())) {
			usuarios.addAll(perfilFeign.listarUsuarios(Perfis.ANALISTA_WORKUP_INTERNACIONAL.getId(), null, null));
		}
		if (usuarios.isEmpty()) {
			throw new BusinessException("erro.acesso.negado");
		}
		return usuarios;
	}

	private DistribuicaoWorkup obterDistribuicaoWorkupPorSolicitacao(Long idSolicitacao) {
		if (idSolicitacao == null) {
			throw new BusinessException("erro.id.nulo");
		}
		
		return distribuicaoWorkupRepository.findBySolicitacao(idSolicitacao).orElseThrow(() -> new BusinessException("erro.nao.existe", "Distribuição Workup"));
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
		if (tiposSolicitacao.isEmpty()) {
			throw new BusinessException("erro.acesso.negado");
		}
		return tiposSolicitacao;
		
	}
	
	@Override
	public void atribuirUsuarioDistribuicaoWorkup(Long id, Long idUsuario) throws JsonProcessingException {
		DistribuicaoWorkup distribuicao = obterDistribuicaoWorkup(id);
		
		//TODO remover essa chamada da solicitação quando for implmentado a solução.
		SolicitacaoWorkupDTO solicitacaoWorkupParaTeste = solicitacaoFeign.obterSolicitacaoWorkup(distribuicao.getSolicitacao());
		if (solicitacaoWorkupParaTeste.solicitacaoWorkupCordao()) {
			throw new BusinessException("erro.nao.implementado");
		}
		//remover até aqui.
		
		validarDistribuicaoParaAtribuicao(distribuicao);		
		validarUsuario(idUsuario);
		
		distribuicao.setUsuarioDistribiu(usuarioService.obterIdUsuarioLogado());
		distribuicao.setUsuarioRecebeu(idUsuario);
		distribuicao.setDataAtualizacao(LocalDateTime.now());
		distribuicaoWorkupRepository.save(distribuicao);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atribuirUsuarioResponsavel(distribuicao.getSolicitacao(), idUsuario);
		solicitacao = alterarFaseWorkup(solicitacao);
		
		fecharTarefaDistribuicaoWorkup(distribuicao, solicitacao);
		
		criarPedido(solicitacao);
		
	}
	
	private SolicitacaoWorkupDTO alterarFaseWorkup(SolicitacaoWorkupDTO solicitacao) {
		if (solicitacao.solicitacaoWorkupMedula() && solicitacao.solicitacaoWorkupNacional()) {
			return solicitacaoFeign.atualizarFaseWorkup(solicitacao.getId(), FasesWorkup.AGUARDANDO_FORMULARIO_DOADOR.getId());			
		}
		else if (solicitacao.solicitacaoWorkupMedula() && solicitacao.solicitacaoWorkupInternacional()) {
			return solicitacaoFeign.atualizarFaseWorkup(solicitacao.getId(), FasesWorkup.AGUARDANDO_PLANO_WORKUP.getId());			
		}
		
		return solicitacaoFeign.atualizarFaseWorkup(solicitacao.getId(), FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA.getId());
	}

	private void criarPedido(SolicitacaoWorkupDTO solicitacao) {
		if (TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId()) || 
				TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			pedidoWorkupService.criarPedidoWorkup(solicitacao);
		}
		else {
			pedidoColetaService.criarPedidoColeta(solicitacao);
		}
		
	}


	private void fecharTarefaDistribuicaoWorkup(DistribuicaoWorkup distribuicao, SolicitacaoDTO solicitacao) throws JsonProcessingException {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr(); 
		if (distribuicao.getTipo().equals(TiposDistribuicaoWorkup.NACIONAL.getId())) {
			fecharTarefa(rmr, TiposTarefa.DISTRIBUIR_WORKUP_NACIONAL,  distribuicao.getId());
		}
		else {
			fecharTarefa(rmr, TiposTarefa.DISTRIBUIR_WORKUP_INTERNACIONAL,  distribuicao.getId());
		}
		
	}
		

	private void fecharTarefa(Long rmr, TiposTarefa tipoTarefa, Long idDistribuicao) throws JsonProcessingException {
		Long idUsuarioLogado = usuarioService.obterIdUsuarioLogado();
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(tipoTarefa.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioResponsavel(idUsuarioLogado)
				.idUsuarioLogado(idUsuarioLogado)
				.rmr(rmr)
				.relacaoEntidadeId(idDistribuicao)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		tarefaHelper.encerrarTarefa(filtroDTO, false);
		
	}

	private void validarUsuario(Long idUsuario) {
		if (usuarioFeign.obterUsuarios(idUsuario) == null) {
			throw new BusinessException("erro.distribuicao.workup.usuario.invalido");
		}
		
		
	}

	private void validarDistribuicaoParaAtribuicao(DistribuicaoWorkup distribuicao) {
		if (distribuicao.getUsuarioDistribiu() != null && distribuicao.getUsuarioRecebeu() != null) {
			throw new BusinessException("erro.distribuicao.workup.ja.realizada");
		}
		
	}
	
	@Override
	public void reatribuirUsuarioDistribuicaoWorkup(Long id, Long idUsuario) throws Exception {
		DistribuicaoWorkup distribuicao = obterDistribuicaoWorkup(id);
		
		validarDistribuicaoParaReatribuicao(distribuicao);		
		validarUsuario(idUsuario);
		
		Long usuarioAnterior = distribuicao.getUsuarioRecebeu();
				
		distribuicao.setUsuarioRecebeu(idUsuario);
		distribuicao.setDataAtualizacao(LocalDateTime.now());
		distribuicaoWorkupRepository.save(distribuicao);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atribuirUsuarioResponsavel(distribuicao.getSolicitacao(), idUsuario);
		
		recriarTarefasAtribuidas(usuarioAnterior, solicitacao);
		
		
	}

	private void recriarTarefasAtribuidas(Long usuarioAnterior, SolicitacaoWorkupDTO solicitacao) throws Exception {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		List<TarefaDTO> tarefasEncontradas = listarTarefas(usuarioAnterior, rmr, TiposTarefa.WORKUP);
		if (CollectionUtils.isNotEmpty(tarefasEncontradas)) {
			tarefasEncontradas.stream().forEach(tarefa -> {
				TarefaDTO tarefaCancelada = tarefaHelper.cancelarTarefa(tarefa.getId(), false);
				TarefaDTO novaTarefa = tarefaCancelada.toBuilder()
						.id(null)
						.dataCriacao(LocalDateTime.now())
						.dataAtualizacao(LocalDateTime.now())
						.status(StatusTarefa.ATRIBUIDA.getId())
						.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId())
						.build();
				tarefaHelper.criarTarefa(novaTarefa);
			});
		}
		
		
	}

	private List<TarefaDTO> listarTarefas(Long usuarioAnterior, Long rmr, TiposTarefa...tipos) throws Exception {
		List<Long> idTiposTarefa = Stream.of(tipos).map(tipo -> tipo.getId()).collect(Collectors.toList());
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
			.idsTiposTarefa(idTiposTarefa)
			.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
			.idUsuarioLogado(usuarioAnterior)
			.idUsuarioResponsavel(usuarioAnterior)
			.build();
		
		
		return tarefaHelper.listarTarefas(filtroDTO);
	}

	private void validarDistribuicaoParaReatribuicao(DistribuicaoWorkup distribuicao) {
		if (distribuicao.getUsuarioDistribiu() == null && distribuicao.getUsuarioRecebeu() == null) {
			throw new BusinessException("erro.distribuicao.workup.ainda.nao.realizada");
		}
		
	}

	
}
