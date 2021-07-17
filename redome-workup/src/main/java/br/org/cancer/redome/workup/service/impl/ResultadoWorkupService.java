package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.NotificacaoDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.ResultadoWorkupInternacionalDTO;
import br.org.cancer.redome.workup.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.ArquivoResultadoWorkup;
import br.org.cancer.redome.workup.model.AvaliacaoPrescricao;
import br.org.cancer.redome.workup.model.FonteCelula;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.PrescricaoMedula;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.model.domain.CategoriasNotificacao;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposResultadoWorkup;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.persistence.IResultadoWorkupRepository;
import br.org.cancer.redome.workup.service.IAvaliacaoPrescricaoService;
import br.org.cancer.redome.workup.service.IFonteCelulaService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IPrescricaoService;
import br.org.cancer.redome.workup.service.IResultadoWorkupService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.ArquivoUtil;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade DistribuicaoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ResultadoWorkupService extends AbstractService<ResultadoWorkup, Long> implements IResultadoWorkupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResultadoWorkupService.class);
	
	@Autowired
	private IResultadoWorkupRepository resultadoWorkupRepository;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;
	
	@Autowired
	private IFonteCelulaService fonteCelulaService;
	
	@Autowired
	private IPrescricaoService prescricaoService;
	
	@Autowired
	private IAvaliacaoPrescricaoService avaliacaoPrescricaoService; 
				
	@Override
	public IRepository<ResultadoWorkup, Long> getRepository() {
		return resultadoWorkupRepository;
	}
	
	@Override
	public ResultadoWorkup obterResultadoWorkupPeloPedidoWorkupId(Long idPedidoWorkup) {		
		return resultadoWorkupRepository.findByPedidoWorkupId(idPedidoWorkup).orElse(null);
	}
	
	@Override
	public void salvarResultadoWorkupInternacional(Long idPedidoWorkup, ResultadoWorkup resultadoWorkup) throws JsonProcessingException {
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupEmAberto(idPedidoWorkup);
		ResultadoWorkup resultadoWorkupEncontrado = obterResultadoWorkupPeloPedidoWorkupId(pedidoWorkup.getId());
		if (resultadoWorkupEncontrado != null) { 
			throw new BusinessException("erro.resultado.workup.ja.cadastrado");
		}
		
	//	validarArquivosResultadoWorkup(pedidoWorkup.getId(), resultadoWorkup.getArquivosResultadoWorkup());
	//	removerArquivosStorageQueNaoEstaoNaLista(pedidoWorkup.getId(), resultadoWorkup.getArquivosResultadoWorkup());		
		
		resultadoWorkup.setDataCriacao(LocalDateTime.now());
		resultadoWorkup.setTipo(TiposResultadoWorkup.INTERNACIONAL.getId());		
		resultadoWorkup.setPedidoWorkup(pedidoWorkup);
		resultadoWorkup.setUsuario(usuarioService.obterIdUsuarioLogado());
		
		resultadoWorkup.getArquivosResultadoWorkup().forEach(arquivo -> {
			arquivo.setResultadoWorkup(resultadoWorkup);
		});
		
		save(resultadoWorkup);
		
		if (resultadoWorkup.getColetaInviavel() && resultadoWorkup.getDoadorIndisponivel()) {
			pedidoWorkupService.cancelarWorkupInternacional(pedidoWorkup.getId());
			SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(pedidoWorkup.getSolicitacao());
			fecharTarefaCadastrarResultadoWorkupInternacional(pedidoWorkup.getId(), solicitacao);
		}
		else {
			SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), FasesWorkup.AGUARDANDO_AVALIACAO_RESULTADO_WORKUP.getId());
			fecharTarefaCadastrarResultadoWorkupInternacional(pedidoWorkup.getId(), solicitacao);	
			
			criarTarefaAvaliarResultadoWorkup(resultadoWorkup.getId(), solicitacao, TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL);
			criarNotificacaoParaCentroTransplate(solicitacao);
			criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_CORDAO, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());
		}
	}
	
	@Override
	public void criarResultadoWorkupNacional(Long idPedidoWorkup, ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO) throws JsonProcessingException {
		
		FonteCelula fonteCelula = null;
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupEmAberto(idPedidoWorkup);
		ResultadoWorkup resultadoWorkupEncontrado = obterResultadoWorkupPeloPedidoWorkupId(pedidoWorkup.getId());
		if (resultadoWorkupEncontrado != null) {
			throw new BusinessException("erro.resultado.workup.ja.cadastrado");
		}
		
		if (!resultadoWorkupNacionalDTO.getColetaInviavel()) {
			validarFonteCelula(resultadoWorkupNacionalDTO.getIdFonteCelula(), pedidoWorkup.getSolicitacao());
			fonteCelula = fonteCelulaService.obterFonteCelula(resultadoWorkupNacionalDTO.getIdFonteCelula());
		}
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.dataCriacao(LocalDateTime.now())
				.tipo(TiposResultadoWorkup.NACIONAL.getId())
				.pedidoWorkup(pedidoWorkup)
				.coletaInviavel(resultadoWorkupNacionalDTO.getColetaInviavel())
				.motivoInviabilidade(resultadoWorkupNacionalDTO.getMotivoInviabilidade())
				.fonteCelula(fonteCelula)
				.dataColeta(resultadoWorkupNacionalDTO.getDataColeta())
				.dataGCSF(resultadoWorkupNacionalDTO.getDataGCSF())
				.adeguadoAferese(resultadoWorkupNacionalDTO.getAdeguadoAferese())
				.acessoVenosoCentral(resultadoWorkupNacionalDTO.getAcessoVenosoCentral())
				.sangueAntologoColetado(resultadoWorkupNacionalDTO.getSangueAntologoColetado())
				.motivoSangueAntologoNaoColetado(resultadoWorkupNacionalDTO.getMotivoSangueAntologoNaoColetado())				
				.usuario(usuarioService.obterIdUsuarioLogado())
				.build();
		
		save(resultadoWorkup);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), 
				FasesWorkup.AGUARDANDO_AVALIACAO_RESULTADO_WORKUP.getId());

		fecharTarefaCadastrarResultadoWorkupNacional(pedidoWorkup.getId(), solicitacao);
		criarTarefaAvaliarResultadoWorkup(resultadoWorkup.getId(), solicitacao, TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL);
		
		criarNotificacaoParaCentroTransplate(solicitacao);
		criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_MEDULA, 
				solicitacao.getMatch().getBusca().getPaciente().getRmr(), 
				solicitacao.getMatch().getDoador());
	}
	
	private void validarFonteCelula(Long idFonteCelula, Long idSolicitacao) {
		FonteCelula fonte = fonteCelulaService.obterFonteCelula(idFonteCelula);
		PrescricaoMedula prescricao = (PrescricaoMedula) prescricaoService.obterPrescricaoPorSolicitacao(idSolicitacao);		
		if (prescricao.getFonteCelulaOpcao1() != null && prescricao.getFonteCelulaOpcao2() != null) {
			
			AvaliacaoPrescricao avaliacaoPrescricao = avaliacaoPrescricaoService.obterAvaliacaoPeloIdPrescricao(prescricao.getId());
			if (fonte.equals(avaliacaoPrescricao.getFonteCelula())) {
				throw new BusinessException("erro.resultado.workup.fonte.descartada.avaliacao.prescricao");
			}
			if (!fonte.equals(prescricao.getFonteCelulaOpcao1()) && !fonte.equals(prescricao.getFonteCelulaOpcao2()) ) {
				throw new BusinessException("erro.resultado.workup.fonte.celula.invalida");
			}						
		}
		else {
			if (!fonte.equals(prescricao.getFonteCelulaOpcao1())) {
				throw new BusinessException("erro.resultado.workup.fonte.celula.invalida");
			}
		}
	}

	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}
	
	
	private void criarNotificacaoParaCentroTransplate(SolicitacaoWorkupDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		
		final CategoriasNotificacao categoria = CategoriasNotificacao.AVALIACAO_RESULTADO_WORKUP;
		NotificacaoDTO notificacao = NotificacaoDTO.builder()
				.rmr(rmr)
				.descricao(String.format("Resultado de workup do doador %s para o paciente $d foi cadastrado.", identificacaoDoador, rmr) )
				.categoriaId(categoria.getId())
				.idPerfil(categoria.getPerfil() != null ? categoria.getPerfil().getId() : null)
				.lido(false)
				.build();
		
		notificacaoFeign.criarNotificacao(notificacao);
	}
	
	private void criarTarefaAvaliarResultadoWorkup(Long idResultadoWorkup, SolicitacaoWorkupDTO solicitacao, TiposTarefa tiposTarefa) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Long idCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getId();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(tiposTarefa.getId()))
				.perfilResponsavel(Perfis.MEDICO_TRANSPLANTADOR.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoParceiro(idCentroTransplante)
				.relacaoEntidade(idResultadoWorkup)
				.build(); 
		
		tarefaHelper.criarTarefa(tarefa);
	}
	
	private void fecharTarefaCadastrarResultadoWorkupInternacional(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
	}

	private void fecharTarefaCadastrarResultadoWorkupNacional(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
		
	}
	
	@SuppressWarnings("unchecked")
	private void removerArquivosStorageQueNaoEstaoNaLista(Long idPedidoWorkup, List<ArquivoResultadoWorkup> arquivosResultadoWorkup) {
		String diretorio = ArquivoUtil.obterDiretorioArquivosResultadoWorkup(idPedidoWorkup);		
		List<S3ObjectSummary> arquivos = storageService.localizarArquivosPorDiretorio(diretorio);
		arquivos.forEach(arquivoNoStorage -> {
			if (arquivosResultadoWorkup.stream().noneMatch(arquivoResultadoWorkup -> arquivoNoStorage.getKey().equals(arquivoResultadoWorkup.getCaminho()))) {
				storageService.removerArquivo(arquivoNoStorage.getKey());
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void validarArquivosResultadoWorkup(Long idPedidoWorkup, List<ArquivoResultadoWorkup> arquivosResultadoWorkup) {
		if (CollectionUtils.isEmpty(arquivosResultadoWorkup)) {
			throw new BusinessException("erro.resultado.workup.nenhum.arquivo.informado");
		}
		String diretorio = ArquivoUtil.obterDiretorioArquivosResultadoWorkup(idPedidoWorkup);		
		List<S3ObjectSummary> arquivos = storageService.localizarArquivosPorDiretorio(diretorio);
		if (CollectionUtils.isEmpty(arquivos)) {
			throw new BusinessException("erro.resultado.workup.storage.sem.arquivo");
		}
		Boolean[] valido = {true};
		arquivosResultadoWorkup.forEach(arquivoResultadoWorkup -> {
			if (!arquivos.stream().anyMatch(arquivo -> arquivo.getKey().equals(arquivoResultadoWorkup.getCaminho()))) {
				valido[0]  = false;			
			}
		});
		if (!valido[0]) {
			throw new BusinessException("erro.resultado.workup.nenhum.arquivo.encontrado");
		}
	}
	
	
	private ResultadoWorkup obterResultadoWorkup(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return resultadoWorkupRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Resultado de Workup"));
	}
	
	@Override
	public ResultadoWorkup obterResultadoWorkupInternacional(Long id) {
		ResultadoWorkup resultadoWorkup = obterResultadoWorkup(id);
		if (!resultadoWorkup.getTipo().equals(TiposResultadoWorkup.INTERNACIONAL.getId())) {
			throw new BusinessException("erro.resultado.workup.identificador.invalido", "internacional");
		}
		return resultadoWorkup;
	}
	
	@Override
	public ResultadoWorkupInternacionalDTO obterResultadoWorkupInternacionalDTO(Long id) {
		ResultadoWorkup resultadoWorkup = obterResultadoWorkupInternacional(id);
		return new ResultadoWorkupInternacionalDTO(resultadoWorkup);
	}

	@Override
	public ResultadoWorkup obterResultadoWorkupNacional(Long id) {
		ResultadoWorkup resultadoWorkup = obterResultadoWorkup(id);
		if (!resultadoWorkup.getTipo().equals(TiposResultadoWorkup.NACIONAL.getId())) {
			throw new BusinessException("erro.resultado.workup.identificador.invalido", "nacional");
		}
		return resultadoWorkup;
	}

	@Override
	public ResultadoWorkupNacionalDTO obterResultadoWorkupNacionalDTO(Long id) {
		ResultadoWorkup resultadoWorkup = obterResultadoWorkup(id);
		return new ResultadoWorkupNacionalDTO(resultadoWorkup);
	}
	
	@Override
	public ResultadoWorkup obterResultadoWorkupPelaSolicitacao(Long idSolicitacao) {
		if (idSolicitacao == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return resultadoWorkupRepository.findByPedidoWorkupSolicitacao(idSolicitacao).orElseThrow(() -> new BusinessException("erro.nao.existe", "Resultado de Workup"));
	}

}
