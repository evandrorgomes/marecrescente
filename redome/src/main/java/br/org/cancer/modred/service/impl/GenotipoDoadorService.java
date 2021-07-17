package br.org.cancer.modred.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.GenotipoDivergenteDTO;
import br.org.cancer.modred.controller.dto.ResultadoDivergenciaDTO;
import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo.Resolucao;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.GenotipoDoador;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.ValorGenotipoDoador;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.MotivoDivergenciaLocusExame;
import br.org.cancer.modred.model.domain.Relatorios;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.persistence.IGenotipoDoadorRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.ILocusExameService;
import br.org.cancer.modred.service.ILocusService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IValorGenotipoBuscaDoadorService;
import br.org.cancer.modred.service.IValorGenotipoDoadorService;
import br.org.cancer.modred.service.IValorGenotipoExpandidoDoadorService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe que guarda as funcionalidades envolvendo a entidade Genotipo.
 * 
 * @author Fillipe Queiroz
 * 
 * @param <T> Qualquer classe do tipo exame.
 *
 */
@Service
@Transactional
public class GenotipoDoadorService<T extends Exame> extends AbstractService<GenotipoDoador, Long> implements IGenotipoDoadorService<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenotipoDoadorService.class);
	
	@Autowired
	private IGenotipoDoadorRepository genotipoRepository;
	
	private IValorGenotipoDoadorService<T> valorGenotipoDoadorService;
	
	@Autowired
	private IValorGenotipoBuscaDoadorService valorGenotipoBuscaService;
	
	@Autowired
	private IValorGenotipoExpandidoDoadorService valorGenotipoExpandidoService;
	
	@Autowired
	private IDoadorService doadorService;
	
	@Autowired
	private ILocusService locusService;
	
	@Autowired
	private IExameDoadorService<T> exameService;
		
	@Autowired
	private ILocusExameService locusExameService;
	
	@Autowired
	private IBuscaService buscaService;
		
	private IPedidoExameService pedidoExameService;
	
	@Autowired
	private ISolicitacaoService solicitacaoService;
	
	@Autowired
	private IRelatorioService relatorioService;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@Autowired
	private IExecutarProcedureMatchService executarProcedureMatchService;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRepository<GenotipoDoador, Long> getRepository() {
		return genotipoRepository;
	}

	
	private GenotipoDoador salvar(List<ValorGenotipoDoador> valoresGenotipos, Doador doador) {
		doador.setGenotipo(null);
		doadorService.save(doador);
		deletarGenotipoPorDoador(doador.getId());

		GenotipoDoador genotipo = new GenotipoDoador();
		genotipo.setDataAlteracao(LocalDateTime.now());		
		genotipo.setDoador(doador);		
		genotipo.setProprietario(doador);
		genotipo.setTipoDoador(doador.getTipoDoador());
		save(genotipo);
		
		genotipo.setValores(valorGenotipoDoadorService.salvar(valoresGenotipos, genotipo, doador));
		
		return genotipo;
	}

	@Override
	public GenotipoDoador obterGenotipo(Long idGenotipo) {
		return genotipoRepository.findByIdAndExcluido(idGenotipo, false);
	}

	private void deletarGenotipoPorDoador(Long idDoador) {
		GenotipoDoador genotipo = obterGenotipoPorDoador(idDoador);
		if (genotipo != null) {
			valorGenotipoBuscaService.deletarValoresPorGenotipo(genotipo.getId());
			valorGenotipoExpandidoService.deletarValoresPorGenotipo(genotipo.getId());
			valorGenotipoDoadorService.deletarValoresPorGenotipo(genotipo.getId());
			genotipoRepository.deleteById(genotipo.getId());
			genotipoRepository.flush();
		}
	}

	@Override
	public GenotipoDoador obterGenotipoPorDoador(Long idDoador) {
		return genotipoRepository.obterGenotipoPorDoador(idDoador);
	}
	
	@Override
	public GenotipoDoador gerarGenotipo(Doador doador) {
		return gerarGenotipo(doador, true);
	}
	

	@Override	
	public GenotipoDoador gerarGenotipo(Doador doador, Boolean executarProcedureMatch) {
		
		List<ValorGenotipoDoador> valoresGenotipoDoador = valorGenotipoDoadorService.gerarGenotipo(doador.getId());
		
		GenotipoDoador genotipo = salvar(valoresGenotipoDoador, doador);
		
		doador.setGenotipo(genotipo);
		doador.setFase(obterFaseDoador(genotipo).getId());
		doadorService.save(doador);
		
		if (executarProcedureMatch) {
			executarProcedureMatchService.gerarMatchDoador(doador, null, null);
		}
		
		return genotipo;
	}
	
	/**
	 * SERVIÇO UTILIZADO SOMENTE NA IMPORTAÇÃO DOS REGISTROS PARA O BD. 
	 */
	@Override
	public void gerarGenotipoDoadorImportacao(Doador doador) {

		try {
			List<ValorGenotipoDoador> valoresGenotipoDoador = valorGenotipoDoadorService.gerarGenotipo(doador.getId());
			salvar(valoresGenotipoDoador, doador);
		}
		catch (Exception e) {
			LOGGER.info("Erro" + e.getMessage() + " " + doador.getId());
		}
	}

	
	@Override
	public void atualizarFaseDoador(Long idDoador) {
		Doador doador = doadorService.findById(idDoador);
		if (doador == null) {
			throw new BusinessException("");
		}
		doador.setFase(obterFaseDoador(doador.getGenotipo()).getId());
		doadorService.save(doador);		
	}
	
	/**
	 * A partir do genótipo informado, define em qual fase o doador
	 * deve ser posicionado no carrousel de matchs.
	 * Os critérios são:
	 * 		Fase 1: Qualquer match inicial.
	 * 		Fase 2: Locus A, B, C, DRB1 e DQB1 informados (em média ou alta {@link ComposicaoAlelo}).
	 * 
	 * @param genotipoDoador genótipo a ser considerado para a definição (normalmente, último genótipo do doador).
	 * @return fase que o doador está.
	 */
	private FasesMatch obterFaseDoador(GenotipoDoador genotipoDoador){
		// TODO: Verificar se existe exame de CT para doador, caso exista, é Fase 3.
		final Doador doador = genotipoDoador.getDoador();
		List<? extends Exame> listarExamesCT = 
				exameService.listarExamesConferidos(doador.getId(), TiposExame.TESTE_CONFIRMATORIO);
		
		if(CollectionUtils.isNotEmpty(listarExamesCT)){
			return FasesMatch.FASE_3;
		}
		else {
			BigDecimal[] somatorioParaFase2 = {BigDecimal.ZERO};
			
			List<ValorGenotipoDoador> valoresResolucaoMediaOuAlta = 
					genotipoDoador.getValores().stream().filter(valorGenotipo -> {
						final ComposicaoAlelo composicaoAlelo = ComposicaoAlelo.valueOf(valorGenotipo.getTipo());
						return Resolucao.MEDIA.equals(composicaoAlelo.getResolucao()) || Resolucao.ALTA.equals(composicaoAlelo.getResolucao());
					}).collect(Collectors.toList());
			
			valoresResolucaoMediaOuAlta.forEach(valorGenotipo -> {
				Locus locus = locusService.findById(valorGenotipo.getId().getLocus().getCodigo());
				somatorioParaFase2[0] = somatorioParaFase2[0].add(locus.getPesoFase2());
			});
			somatorioParaFase2[0] = somatorioParaFase2[0].setScale(1, RoundingMode.HALF_EVEN);
			
			final BigDecimal somatorioTotalParaIdentificarFase2 = new BigDecimal(configuracaoService.obterConfiguracao(Configuracao.SOMATORIO_TOTAL_PARA_IDENTIFICAR_FASE2).getValor())
					.setScale(1, RoundingMode.HALF_EVEN);
							
			return somatorioParaFase2[0].compareTo(somatorioTotalParaIdentificarFase2) == -1 ? FasesMatch.FASE_1 : FasesMatch.FASE_2;
		}
	}
	
	@Override
	public void resolverDivergencia(Long idDoador, ResultadoDivergenciaDTO resultadoDivergenciaDTO) {
		
		if (resultadoDivergenciaDTO == null || resultadoDivergenciaDTO.getDivergencia() == null || 
				resultadoDivergenciaDTO.getIdBusca() == null ||
				resultadoDivergenciaDTO.getLocus() == null ||
				resultadoDivergenciaDTO.getIdDemaisExames() == null ||
				resultadoDivergenciaDTO.getIdDemaisExames().isEmpty() ||
				resultadoDivergenciaDTO.getIdExamesSelecionados() == null || 
				resultadoDivergenciaDTO.getIdExamesSelecionados().isEmpty()) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		
		Busca busca = buscaService.obterBusca(resultadoDivergenciaDTO.getIdBusca());
		Doador doador = doadorService.obterDoador(idDoador);
		
		resultadoDivergenciaDTO.getIdExamesSelecionados().forEach(idExame -> {
			locusExameService.marcarSelecionado(busca.getId(), idExame, resultadoDivergenciaDTO.getLocus(),
					resultadoDivergenciaDTO.getDivergencia() ? MotivoDivergenciaLocusExame.DISCREPANTE : null);			
		});
		
		resultadoDivergenciaDTO.getIdDemaisExames().forEach(idExame -> {
			locusExameService.inativar(busca.getId(), idExame, resultadoDivergenciaDTO.getLocus(),
					resultadoDivergenciaDTO.getDivergencia() ? MotivoDivergenciaLocusExame.DISCREPANTE : null);			
		});
		CompletableFuture future = new CompletableFuture(); 
		gerarGenotipo(doador);
	}

	@Override
	public GenotipoDivergenteDTO obterGenotipoDoadorDivergenteDto(Long idDoador) {
		Doador doador = doadorService.obterDoador(idDoador);
		
		GenotipoDivergenteDTO genotipoDivergente = new GenotipoDivergenteDTO();
		genotipoDivergente.setTipoDoador(doador.getTipoDoador());
		genotipoDivergente.setGenotiposDto(valorGenotipoDoadorService.obterGenotipoDoadorDto(doador.getId()));
		genotipoDivergente.setExames(exameService.listarExamesPorDoadorOrdernadoPorDataCriacaoDecrescente(doador.getId()));
		genotipoDivergente.setExisteSolicitacaoFase3EmAberto(false);
		if (TiposDoador.NACIONAL.equals(doador.getTipoDoador())) {
			genotipoDivergente.setPedidos(pedidoExameService.listarPedidosExameParaResolverDivergencia(doador.getId()));
			
			final Solicitacao solicitacao = solicitacaoService.obterSolicitacaoEmAbertoPor(doador.getId(), TiposSolicitacao.FASE_3.getId());
			genotipoDivergente.setExisteSolicitacaoFase3EmAberto(solicitacao != null);
			
			final Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(Relatorios.EMAIL_DIVERGENCIA_LABORATORIO.getCodigo());
			genotipoDivergente.setTextoEmailDivergencia(relatorio.getHtml());
		}
		
		return genotipoDivergente;
	}

	/**
	 * @param valorGenotipoDoadorService the valorGenotipoDoadorService to set
	 */
	@Autowired
	public void setValorGenotipoDoadorService(IValorGenotipoDoadorService<T> valorGenotipoDoadorService) {
		this.valorGenotipoDoadorService = valorGenotipoDoadorService;
	}

	/**
	 * @param pedidoExameService the pedidoExameService to set
	 */
	@Autowired
	public void setPedidoExameService(IPedidoExameService pedidoExameService) {
		this.pedidoExameService = pedidoExameService;
	}
	
	
	
}
