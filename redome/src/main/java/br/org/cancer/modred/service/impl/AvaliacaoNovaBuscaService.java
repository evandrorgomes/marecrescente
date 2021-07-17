package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.AvaliacaoNovaBusca;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.StatusBusca;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.StatusAvaliacaoNovaBusca;
import br.org.cancer.modred.model.domain.StatusPacientes;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IAvaliacaoNovaBuscaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoNovaBuscaService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IHistoricoStatusPacienteService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe para de negócios que define comportamento, validações,
 * funcionalidaes envolvendo a entidade AvaliacaoNovaBusca.
 * Esta ocorre quando uma nova busca é solicitada para um paciente
 * que se já teve uma busca criada anteriormente.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AvaliacaoNovaBuscaService extends AbstractLoggingService<AvaliacaoNovaBusca, Long> 
				implements IAvaliacaoNovaBuscaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoNovaBuscaService.class);
	
	@Autowired
	private IAvaliacaoNovaBuscaRepository avaliacaoNovaBuscaRepositorio;
	
	@Autowired
	private IBuscaService buscaService;
	
	@Autowired
	private IEvolucaoService evolucaoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IHistoricoStatusPacienteService historicoStatusPacienteService;
	
	
	@Override
	public IRepository<AvaliacaoNovaBusca, Long> getRepository() {
		return avaliacaoNovaBuscaRepositorio;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(AvaliacaoNovaBusca avaliacaoNovaBusca) {
		final Paciente paciente = avaliacaoNovaBusca.getPaciente();
		final Busca buscaAtiva = 
				buscaService.obterBuscaAtivaPorRmr(paciente.getRmr());
		
		if(buscaAtiva != null){
			LOGGER.error("Paciente está com busca ativa, ou seja, não pode "
					+ "ser solicitada uma nova busca. Possível inconsistência.");
			throw new BusinessException("erro.interno");
		}
		
		if(StatusAvaliacaoNovaBusca.AGUARDANDO_AVALIACAO.equals(avaliacaoNovaBusca.getStatus())){
			boolean dentroPrazo = evolucaoService.verificarSeEvolucaoAtualizada(paciente);
			
			if(!dentroPrazo){
				throw new BusinessException("evolucao.desatualizada.nova.busca.mensagem");
			}
			
			if(verificarAvaliacaoEmAndamento(paciente.getRmr())){
				LOGGER.error("Paciente " + paciente.getRmr() + "já possui avaliação em andamento.");
				throw new BusinessException("erro.interno");
			}
		}
		else if(StatusAvaliacaoNovaBusca.REPROVADA.equals(avaliacaoNovaBusca.getStatus()) && StringUtils.isEmpty(avaliacaoNovaBusca.getJustificativa())){
			throw new BusinessException("nova.busca.justificativa.obrigatoria");
		}
		
		return super.validateEntity(avaliacaoNovaBusca);
	}
	
	@Override
	public Paciente obterPaciente(AvaliacaoNovaBusca avaliacao) {
		return avaliacao.getPaciente();
	}

	@Override
	public String[] obterParametros(AvaliacaoNovaBusca avaliacao) {
		return new String[] { String.valueOf(avaliacao.getPaciente().getRmr()) };
	}

	@Override
	@CreateLog(TipoLogEvolucao.NOVA_BUSCA_SOLICITADA_AVALIACAO_REDOME)
	public AvaliacaoNovaBusca criarNovaAvaliacao(Paciente paciente) {
		AvaliacaoNovaBusca avaliacaoNovaBusca = new AvaliacaoNovaBusca();
		avaliacaoNovaBusca.setPaciente(paciente);
		avaliacaoNovaBusca.setStatus(StatusAvaliacaoNovaBusca.AGUARDANDO_AVALIACAO);
		avaliacaoNovaBusca.setDataCriacao(LocalDateTime.now());
		avaliacaoNovaBusca = save(avaliacaoNovaBusca);
		
		TiposTarefa	.AVALIAR_NOVA_BUSCA.getConfiguracao()
					.criarTarefa()
					.comRmr(paciente.getRmr())
					.comObjetoRelacionado(avaliacaoNovaBusca.getId())
					.apply();
		
		return avaliacaoNovaBusca;
	}

	@Override
	public boolean verificarAvaliacaoEmAndamento(Long rmr) {
		final AvaliacaoNovaBusca ultimaAvaliacao = 
				avaliacaoNovaBuscaRepositorio.obterUltimaAvaliacao(rmr);
		return ultimaAvaliacao != null 
				&& StatusAvaliacaoNovaBusca.AGUARDANDO_AVALIACAO.equals(ultimaAvaliacao.getStatus());
	}

	
	@Override
	public void aprovarAvaliacaoNovaBusca(Long id) {
		
		AvaliacaoNovaBusca avaliacaoLocalizada = this.findById(id);
		avaliacaoLocalizada.setStatus(StatusAvaliacaoNovaBusca.APROVADA);
		avaliacaoLocalizada.setAvaliador(usuarioService.obterUsuarioLogado());
		avaliacaoLocalizada.setDataAvaliado(LocalDateTime.now());
		this.save(avaliacaoLocalizada);
		
		Busca busca = new Busca();
		busca.setPaciente(avaliacaoLocalizada.getPaciente());
		busca.setStatus(new StatusBusca(StatusBusca.LIBERADA));
		busca.setUsuario(usuarioService.obterUsuarioLogado());
		buscaService.save(busca);
		
		historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.APROVADO, avaliacaoLocalizada.getPaciente());
		
		// TODO: Chamar procedure de match.
		
		TiposTarefa.AVALIAR_NOVA_BUSCA.getConfiguracao()
			.fecharTarefa()
			.comObjetoRelacionado(avaliacaoLocalizada.getId())
			.comRmr(avaliacaoLocalizada.getPaciente().getRmr())
			.apply();
	}

	@Override
	public void reprovarAvaliacaoNovaBusca(Long id, String justificativa) {

		AvaliacaoNovaBusca avaliacaoLocalizada = this.findById(id);
		avaliacaoLocalizada.setAvaliador(usuarioService.obterUsuarioLogado());
		avaliacaoLocalizada.setDataAvaliado(LocalDateTime.now());
		avaliacaoLocalizada.setStatus(StatusAvaliacaoNovaBusca.REPROVADA);
		avaliacaoLocalizada.setJustificativa(justificativa);
		this.save(avaliacaoLocalizada);
		
		historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.REPROVADO, avaliacaoLocalizada.getPaciente());
		
		TiposTarefa.AVALIAR_NOVA_BUSCA.getConfiguracao()
			.fecharTarefa()
			.comObjetoRelacionado(avaliacaoLocalizada.getId())
			.comRmr(avaliacaoLocalizada.getPaciente().getRmr())
			.finalizarProcesso()
			.apply();
		
	}

	
	@Override
	public JsonViewPage<TarefaDTO> listarTarefas(PageRequest pageRequest){
		return TiposTarefa.AVALIAR_NOVA_BUSCA.getConfiguracao()
				.listarTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comPaginacao(pageRequest)
				.apply();	
	}
	
}
