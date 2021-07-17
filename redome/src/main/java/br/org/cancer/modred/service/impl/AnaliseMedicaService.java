package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.AnaliseMedica;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Formulario;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.TentativaContatoDoador;
import br.org.cancer.modred.model.domain.AcaoPedidoContato;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IAnaliseMedicaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAnaliseMedicaService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IFormularioService;
import br.org.cancer.modred.service.IMotivoStatusDoadorService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.ITentativaContatoDoadorService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.vo.AnaliseMedicaFinalizadaVo;
import br.org.cancer.modred.vo.DetalheAnaliseMedicaVo;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade Analise
 * Medica.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class AnaliseMedicaService extends AbstractService<AnaliseMedica, Long> implements IAnaliseMedicaService {

	@Autowired
	private IAnaliseMedicaRepository analiseMedicaRepository;
	
	private ITentativaContatoDoadorService tentativaContatoDoadorService;
	
	@Autowired
	private IMotivoStatusDoadorService motivoStatusDoadorService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IFormularioService formularioService;
	
	@Autowired
	private IDoadorNacionalService doadorService;
	
	@Autowired
	private IPedidoExameService pedidoExameService;

	@Override
	public IRepository<AnaliseMedica, Long> getRepository() {
		return analiseMedicaRepository;
	}

	@Override
	public void criarAnaliseMedica(PedidoContato pedidoContato) {
		AnaliseMedica analiseMedica = new AnaliseMedica(pedidoContato);

		save(analiseMedica);

		criarTarefa(analiseMedica);

	}

	private void criarTarefa(AnaliseMedica analiseMedica) {
		final Long rmr = analiseMedica.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente().getRmr();

		TiposTarefa.ANALISE_MEDICA_DOADOR_CONTATO.getConfiguracao().criarTarefa().comRmr(rmr)
				.comObjetoRelacionado(analiseMedica.getId()).apply();
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefas(PageRequest pageRequest) {
		return TiposTarefa.ANALISE_MEDICA_DOADOR_CONTATO.getConfiguracao().listarTarefa().comPaginacao(pageRequest)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA)).apply();
	}
	
	private AnaliseMedica obterAnaliseMedicaPorId(Long id) {
		AnaliseMedica analiseMedica = findById(id);
		if (analiseMedica == null) {
			throw new BusinessException("");
		}
		return analiseMedica;
	}

	@Override
	public DetalheAnaliseMedicaVo obterDetalheAnaliseMedica(Long id) {
		AnaliseMedica analiseMedica = obterAnaliseMedicaPorId(id);
		
		TentativaContatoDoador ultimaTentativaContato = tentativaContatoDoadorService
				.obterUltimaTentativaContatoPorPedidoContatoId(analiseMedica.getPedidoContato().getId());
		
		
		TarefaDTO ultimaTarefa = tentativaContatoDoadorService
				.obterTarefaAssociadaATentativaContatoEStatusTarefa(ultimaTentativaContato, Arrays.asList(StatusTarefa.FEITA), false, false);
		
		TarefaDTO tarefaAnaliseMedica = obterTarefaAtribuidaAssociadaAnaliseMedica(analiseMedica);
				
		DetalheAnaliseMedicaVo detalhe = new DetalheAnaliseMedicaVo();
		detalhe.setIdAnaliseMedica(analiseMedica.getId());
		detalhe.setTarefaFinalizadaPedidoContato(ultimaTarefa);
		detalhe.setIdTarefaAnaliseMedica(tarefaAnaliseMedica.getId());
		
		return detalhe;
	}
	
	private TarefaDTO obterTarefaAtribuidaAssociadaAnaliseMedica(AnaliseMedica analiseMedica) {
		final Long rmr = analiseMedica.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente().getRmr();		

		return TiposTarefa.ANALISE_MEDICA_DOADOR_CONTATO.getConfiguracao().obterTarefa()
				.comRmr(rmr)
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comObjetoRelacionado(analiseMedica.getId())
				.apply(); 
	}
	
	@Override
	public void finalizar(Long id, AnaliseMedicaFinalizadaVo analiseMedicaFinalizadaVo) {
		
		AnaliseMedica analiseMedica = obterAnaliseMedicaPorId(id);
		analiseMedica.setDataAnalise(LocalDateTime.now());				
		analiseMedica.setProsseguir(analiseMedicaFinalizadaVo.getAcao() == AcaoPedidoContato.PROSSEGUIR);
		if (analiseMedicaFinalizadaVo.getIdMotivoStatusDoador() != null) {
			analiseMedica.setMotivoStatusDoador(motivoStatusDoadorService.obterMotivoPorId(analiseMedicaFinalizadaVo.getIdMotivoStatusDoador()));
		}
		analiseMedica.setTempoInativacaoTemporaria(analiseMedicaFinalizadaVo.getTempoInativacaoTemporaria());
		analiseMedica.setUsuario(usuarioService.obterUsuarioLogado());
		
		save(analiseMedica);
		
		encerrarTarefa(analiseMedica);
		
		salvarFormulario(analiseMedica, analiseMedicaFinalizadaVo.getFormulario());
		
		inativarDoadorCasoNaoProsseguir(analiseMedica);
		
		criarPedidoExameCasoProsseguir(analiseMedica);
		
	}

	private void encerrarTarefa(AnaliseMedica analiseMedica) {
		final Long rmr = analiseMedica.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente().getRmr();		
		//final Processo processo = processoService.obterProcessoAtivo(TipoProcesso.BUSCA, rmr);

		TiposTarefa.ANALISE_MEDICA_DOADOR_CONTATO.getConfiguracao().fecharTarefa()
				.comRmr(rmr)				
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comObjetoRelacionado(analiseMedica.getId())
				.comUsuario(usuarioService.obterUsuarioLogado())
				.apply();		
		
	}
	
	private void salvarFormulario(AnaliseMedica analiseMedica, Formulario formulario) {
	
		if (!analiseMedica.getProsseguir()) {
			formulario.setComValidacao(false);
			formularioService.salvarFormularioComPedidoContato(analiseMedica.getPedidoContato().getId(), formulario);
		}
		else {
			formulario.setComValidacao(true);
			formularioService.salvarFormularioComPedidoContato(analiseMedica.getPedidoContato().getId(), formulario);
		}
	}
	
	private void inativarDoadorCasoNaoProsseguir(AnaliseMedica analiseMedica) {
		if (!analiseMedica.getProsseguir()) {
			final DoadorNacional doador = (DoadorNacional) analiseMedica.getPedidoContato().getSolicitacao().getMatch().getDoador();
			doadorService.inativarDoador(doador.getId(), analiseMedica.getMotivoStatusDoador().getId(), analiseMedica.getTempoInativacaoTemporaria());
		}
	}
	
	private void criarPedidoExameCasoProsseguir(AnaliseMedica analiseMedica) {
		if (analiseMedica.getProsseguir()) {
			pedidoExameService.criarPedidoDoadorNacional(analiseMedica.getPedidoContato().getSolicitacao());
		}	
	}

	/**
	 * @param tentativaContatoDoadorService the tentativaContatoDoadorService to set
	 */
	@Autowired
	public void setTentativaContatoDoadorService(ITentativaContatoDoadorService tentativaContatoDoadorService) {
		this.tentativaContatoDoadorService = tentativaContatoDoadorService;
	}
	
	
	
	
	
	
	

}
