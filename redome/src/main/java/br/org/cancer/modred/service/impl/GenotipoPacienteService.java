package br.org.cancer.modred.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.GenotipoComparadoDTO;
import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.controller.dto.GenotipoDoadorComparadoDTO;
import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.BuscaPreliminar;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.GenotipoPaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.ValorGenotipoPaciente;
import br.org.cancer.modred.model.ValorGenotipoPreliminar;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.persistence.IGenotipoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.redomelib.vo.ValorNmdpVo;
import br.org.cancer.modred.service.IBuscaPreliminarService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.ILocusService;
import br.org.cancer.modred.service.IMatchPreliminarService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.ISegurancaService;
import br.org.cancer.modred.service.IValorGenotipoBuscaService;
import br.org.cancer.modred.service.IValorGenotipoDoadorService;
import br.org.cancer.modred.service.IValorGenotipoExpandidoService;
import br.org.cancer.modred.service.IValorGenotipoPacienteService;
import br.org.cancer.modred.service.IValorGenotipoPreliminarService;
import br.org.cancer.modred.service.IValorNmdpService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.DateUtils;
import br.org.cancer.modred.util.GenotipoDoadorComparadoDTOUtil;
import br.org.cancer.modred.vo.report.JasperReportGenerator;

/**
 * Classe que define as funcionalidades que envolvem a geração e consulta do genótipo do paciente.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class GenotipoPacienteService extends AbstractLoggingService<GenotipoPaciente, Long> implements IGenotipoPacienteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenotipoPacienteService.class);
		
	@Autowired
	private IGenotipoRepository genotipoRepository;
	
	@Autowired
	private IValorGenotipoPacienteService valorGenotipoService;

	@Autowired
	private IMatchService matchService;

	@Autowired
	private IValorGenotipoBuscaService valorGenotipoBuscaService;

	@Autowired
	private IValorGenotipoExpandidoService valorGenotipoExpandidoService;

	@Autowired
	private ILocusService locusService;

	@Autowired
	private IPacienteService pacienteService;

	private ISegurancaService segurancaService;
	
	@Autowired
	private IEvolucaoService evolucaoService;
	
	@Autowired
	private IMatchPreliminarService matchPreliminarService;
	
	@Autowired
	private IBuscaPreliminarService buscaPreliminarService;
		
	@Autowired
	private IValorGenotipoPreliminarService genotipoPreliminarService;
	
	@Autowired
	private IExecutarProcedureMatchService executarProcedureMatchService;
	
	@Autowired
	private IBuscaService buscaService;
	
    @Autowired
    private IValorNmdpService valorNmdpService;

	@Autowired
	private IGenotipoDoadorService genotipoDoadorService;

	@Autowired
	private IValorGenotipoDoadorService valorGenotipoDoadorService;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IRepository<GenotipoPaciente, Long> getRepository() {
		return genotipoRepository;
	}
	
	@CreateLog(TipoLogEvolucao.GENOTIPO_ATUALIZADO_PARA_PACIENTE)
	@Override
	public GenotipoPaciente gerarGenotipo(Paciente paciente, Boolean executarProcedureMatch) {
		
		List<ValorGenotipoPaciente> valoresGenotipos = valorGenotipoService.gerarGenotipoPaciente(paciente.getRmr());
		
		deletarGenotipoPorPaciente(paciente.getRmr());

		GenotipoPaciente genotipo = new GenotipoPaciente();
		genotipo.setDataAlteracao(LocalDateTime.now());
		genotipo.setPaciente(paciente);
		genotipo.setValores(valoresGenotipos);
		save(genotipo);

		genotipo.setValores(valorGenotipoService.salvar(valoresGenotipos, genotipo, paciente));
		
		if (executarProcedureMatch) {
			executarProcedureMatchService.gerarMatchPaciente(paciente.getRmr());
		}
			
		return genotipo;
	}
	
	
	/**
	 * SERVIÇO UTILIZADO SOMENTE NA IMPORTAÇÃO DOS REGISTROS PARA O BD. 
	 */
	@Override
	public void gerarGenotipoPacienteImportacao(Paciente paciente) {

		try {
			List<ValorGenotipoPaciente> valoresGenotipos = valorGenotipoService.gerarGenotipoPaciente(paciente.getRmr());
	
			deletarGenotipoPorPaciente(paciente.getRmr());
			
			GenotipoPaciente genotipo = new GenotipoPaciente();
			genotipo.setDataAlteracao(LocalDateTime.now());
			genotipo.setPaciente(paciente);
			genotipo.setValores(valoresGenotipos);
			save(genotipo);
			valorGenotipoService.salvar(valoresGenotipos, genotipo, paciente);
			
		}
		catch (Exception e) {
			LOGGER.info("Erro" + e.getMessage() + " " + paciente.getRmr());
		}
	}
	
	@Override
	public GenotipoPaciente obterGenotipo(Long idGenotipo) {
		return genotipoRepository.findByIdAndExcluido(idGenotipo, false);
	}
	
	@Override
	public void deletarGenotipoPorPaciente(Long rmr) {		
		GenotipoPaciente genotipo = obterGenotipoPorPaciente(rmr);
		if (genotipo != null) {
			valorGenotipoBuscaService.deletarValoresPorGenotipo(genotipo.getId());
			valorGenotipoExpandidoService.deletarValoresPorPaciente(rmr);
			valorGenotipoService.excluirValoresGenotiposPaciente(genotipo.getId());
			genotipoRepository.delete(genotipo);
			genotipoRepository.flush();
		}
	}

	@Override
	public GenotipoPaciente obterGenotipoPorPaciente(Long rmr) {
		return genotipoRepository.obterGenotipoPorPaciente(rmr);
	}

	@Override
	public GenotipoComparadoDTO listarGenotiposComparados(Long rmr, List<Long> listaIdsDoador) {
		if (rmr == null) {
			throw new BusinessException("erro.paciente.obter");
		}
		if (listaIdsDoador == null) {
			throw new BusinessException("erro.doador.invalido");
		}

		List<MatchDTO> matchs = this.matchService.listarMatchsAtivosPorRmrAndListaIdsDoador(rmr, listaIdsDoador);
		GenotipoComparadoDTO genotipoComparadoDTO = null;
		if (matchs != null && !matchs.isEmpty()) {

			Paciente paciente = pacienteService.obterDadosIdentificadaoPorPaciente(rmr);
			Evolucao ultimaEvolucao = evolucaoService.obterUltimaEvolucaoDoPaciente(rmr);

			genotipoComparadoDTO = new GenotipoComparadoDTO();
			genotipoComparadoDTO.setListaLocus(locusService.listarLocus());
			genotipoComparadoDTO.setRmr(rmr);
			if (segurancaService.usuarioLogadoPossuiRecurso(paciente,
					Arrays.asList(
							Recurso.VISUALIZAR_IDENTIFICACAO_COMPLETA,
							Recurso.PACIENTES_PARA_PROCESSO_BUSCA))) {
				genotipoComparadoDTO.setNomePaciente(paciente.getNome());
			}
			else {
				genotipoComparadoDTO.setNomePaciente(paciente.nomeAbreviado());
			}

			genotipoComparadoDTO.setDataNascimento(paciente.getDataNascimento());
			genotipoComparadoDTO.setIdade(DateUtils.calcularIdade(paciente.getDataNascimento()));
			genotipoComparadoDTO.setSexo(paciente.getSexo());
			genotipoComparadoDTO.setAbo(paciente.getAbo());
			genotipoComparadoDTO.setPeso(ultimaEvolucao.getPeso());

			GenotipoPaciente genotipoAtual = obterGenotipoPorPaciente(rmr);
			if (genotipoAtual == null) {
				throw new BusinessException("erro.paciente.sem.genotipo");
			}
			genotipoComparadoDTO.setGenotipoPaciente(
					valorGenotipoService.obterGenotipoDtoPorValorGenotipo(genotipoAtual.getValores()));
			
			List<GenotipoDoadorComparadoDTO> genotiposDoadores = matchs.stream().map(GenotipoDoadorComparadoDTOUtil::montarDtoComGenotipoComparadoDoDoador).collect(Collectors.toList());
			genotipoComparadoDTO.setGenotiposDoadores(genotiposDoadores);

			acrescentarGenotipoDTOQueExistemEmDoadoresEQueNaoTemEmPaciente(genotipoComparadoDTO.getGenotipoPaciente(),
					genotipoComparadoDTO.getGenotiposDoadores());
			acrescentarGenotipoDTOQueExistemNoPacienteEQueNaoExistamNosDoadores(genotipoComparadoDTO.getGenotipoPaciente(),
					genotipoComparadoDTO.getGenotiposDoadores());

		}

		return genotipoComparadoDTO;
	}

	@Override
	@SuppressWarnings("static-access")
	public File impressaoGenotiposComparados(Long rmr, List<Long> listaIdsDoador) {
		
		GenotipoComparadoDTO genotipoComparadoDTO = listarGenotiposComparados(rmr, listaIdsDoador);
		
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		genotipoComparadoDTO.setNomeMedicoResponsavel(buscaAtiva.getPaciente().getMedicoResponsavel().getNome());
		genotipoComparadoDTO.setNomeCentroAvaliador(buscaAtiva.getPaciente().getCentroAvaliador().getNome());

		List<ValorNmdpVo> valoresNmdp = new ArrayList<>();
		genotipoComparadoDTO.getGenotipoPaciente().forEach(genotipo -> {
			composicaoValoresNmdp(valoresNmdp, genotipo);
		});
		genotipoComparadoDTO.getGenotiposDoadores().forEach(genotipoDoador -> {
			genotipoDoador.getGenotipoDoador().forEach(genotipo -> {
				composicaoValoresNmdp(valoresNmdp, genotipo);
			});
		});
		genotipoComparadoDTO.setValoresNmdp(valoresNmdp);
		
		Collection<GenotipoComparadoDTO> dados = new ArrayList<>(); 
        dados.add(genotipoComparadoDTO);
		
		return JasperReportGenerator.gerarRelatorioGenotipoComparado(dados);
	}

	private void composicaoValoresNmdp(List<ValorNmdpVo> valoresNmdp, GenotipoDTO genotipo) {
		String codigo;
		if (genotipo.getTipoPrimeiroAlelo() == ComposicaoAlelo.NMDP.getId()) {
			codigo = genotipo.getPrimeiroAlelo().split(":")[1];
//			ValorNmdpVo valorNmdp = new ValorNmdpVo(codigo,valorNmdpService.obterSubTipos(codigo));
//			valoresNmdp.add(valorNmdp);
		}
		if (genotipo.getTipoSegundoAlelo() == ComposicaoAlelo.NMDP.getId()) {
			codigo = genotipo.getSegundoAlelo().split(":")[1];
//			ValorNmdpVo valorNmdp = new ValorNmdpVo(codigo,valorNmdpService.obterSubTipos(codigo));
//			valoresNmdp.add(valorNmdp);
		}
	}
	
	
	/**
	 * Método que acrescenta GenotipoDTO, caso não exista, na lista de doadores através do genótipo do paciente.
	 * 
	 * @param genotipoPaciente
	 * @param genotiposDoadores
	 */
	private void acrescentarGenotipoDTOQueExistemNoPacienteEQueNaoExistamNosDoadores(List<GenotipoDTO> genotipoPaciente,
			List<GenotipoDoadorComparadoDTO> genotiposDoadores) {
		genotipoPaciente.forEach(genotipoDTO -> 

			genotiposDoadores.forEach(genotipoDoadorComparado -> {

				final Boolean existe = genotipoDoadorComparado.getGenotipoDoador()
						.stream().anyMatch(genotipoDoador -> genotipoDoador.getLocus().equals(genotipoDTO.getLocus()));
				if (!existe) {
					genotipoDoadorComparado.getGenotipoDoador().add(new GenotipoDTO(genotipoDTO.getLocus(), genotipoDTO.getOrdem()));
				}
			})
		);
		
		genotiposDoadores.forEach(genotipoDoadorComparado -> {
			Collections.sort(genotipoDoadorComparado.getGenotipoDoador(), new Comparator<GenotipoDTO>() {
	
				@Override
				public int compare(GenotipoDTO genotipoDTO1, GenotipoDTO genotipoDTO2) {
					return genotipoDTO1.getOrdem().compareTo(genotipoDTO2.getOrdem());
				}
			});
		});
		

	}

	/**
	 * Método que acrescenta genótipoDTO, caso não exista, no paciente através da lista de genótipoDTO dos doadores.
	 * 
	 * @param genotipoPaciente
	 * @param genotiposDoadores
	 */
	private void acrescentarGenotipoDTOQueExistemEmDoadoresEQueNaoTemEmPaciente(List<GenotipoDTO> genotipoPaciente,
			List<GenotipoDoadorComparadoDTO> genotiposDoadores) {

		genotiposDoadores.forEach(genotipoDoadorComparado -> 
			genotipoDoadorComparado.getGenotipoDoador()
					.stream()
					.filter(genotipoDoador ->
			
						genotipoPaciente
								.stream()
								.noneMatch(genotipoDTOPaciente -> 
									genotipoDTOPaciente.getLocus().equals(genotipoDoador.getLocus())
								)
					)
					.map(genotipoDoador ->
						new GenotipoDTO(genotipoDoador.getLocus(),genotipoDoador.getOrdem())
					)
					.forEach(genotipoPaciente::add)
		);
		
		Collections.sort(genotipoPaciente, new Comparator<GenotipoDTO>() {

			@Override
			public int compare(GenotipoDTO genotipoDTO1, GenotipoDTO genotipoDTO2) {
				return genotipoDTO1.getOrdem().compareTo(genotipoDTO2.getOrdem());
			}
		});
	}


	@Override
	public Paciente obterPaciente(GenotipoPaciente genotipo) {
		return genotipo.getPaciente();
	}

	@Override
	public String[] obterParametros(GenotipoPaciente genotipo) {
		return genotipo.getPaciente().getRmr().toString().split(";");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenotipoComparadoDTO listarGenotiposComparadosBuscaPreliminar(Long idBuscaPreliminar, List<Long> listaIdsDoador) {
		if (idBuscaPreliminar == null) {
			throw new BusinessException("erro.buscapreliminar.obter");
		}
		if (listaIdsDoador == null) {
			throw new BusinessException("erro.doador.invalido");
		}

		List<MatchDTO> matchs = this.matchPreliminarService.listarMatchsAtivosPorIDBuscaPreliminarAndListaIdsDoador(idBuscaPreliminar, listaIdsDoador);
		GenotipoComparadoDTO genotipoComparadoDTO = null;
		if (matchs != null && !matchs.isEmpty()) {

			BuscaPreliminar buscaPreliminar = buscaPreliminarService.findById(idBuscaPreliminar);

			genotipoComparadoDTO = new GenotipoComparadoDTO();
			genotipoComparadoDTO.setListaLocus(locusService.listarLocus());
			genotipoComparadoDTO.setRmr(null);
			genotipoComparadoDTO.setNomePaciente(buscaPreliminar.getNomePaciente());
			
			genotipoComparadoDTO.setDataNascimento(buscaPreliminar.getDataNascimento());
			genotipoComparadoDTO.setSexo(null);
			genotipoComparadoDTO.setAbo(buscaPreliminar.getAbo());
			genotipoComparadoDTO.setPeso(buscaPreliminar.getPeso());

			List<ValorGenotipoPreliminar> valores = genotipoPreliminarService.listarPorBuscaPreliminar(idBuscaPreliminar);
			
			genotipoComparadoDTO.setGenotipoPaciente(
					genotipoPreliminarService.obterGenotipoDtoPorGenotipoPreliminar(valores) );

			List<GenotipoDoadorComparadoDTO> genotiposDoadores = matchs.stream().map(GenotipoDoadorComparadoDTOUtil::montarDtoComGenotipoComparadoDoDoador).collect(Collectors.toList());

			genotipoComparadoDTO.setGenotiposDoadores(genotiposDoadores);

			acrescentarGenotipoDTOQueExistemEmDoadoresEQueNaoTemEmPaciente(genotipoComparadoDTO.getGenotipoPaciente(),
					genotipoComparadoDTO.getGenotiposDoadores());
			acrescentarGenotipoDTOQueExistemNoPacienteEQueNaoExistamNosDoadores(genotipoComparadoDTO.getGenotipoPaciente(),
					genotipoComparadoDTO.getGenotiposDoadores());

		}

		return genotipoComparadoDTO;
	}
		
	@Override
	public Boolean verificarSePossuiExamesClasseC(Long rmr) {
		return genotipoRepository.verificarSePossuiExamesClasseC(rmr);
	}

	/**
	 * @param segurancaService the segurancaService to set
	 */
	@Autowired
	public void setSegurancaService(ISegurancaService segurancaService) {
		this.segurancaService = segurancaService;
	}

}
