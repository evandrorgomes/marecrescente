package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.controller.dto.genotipo.ValorAlelo;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.ExameCordaoNacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.GenotipoDoador;
import br.org.cancer.modred.model.IGenotipo;
import br.org.cancer.modred.model.IProprietarioHla;
import br.org.cancer.modred.model.IValorGenotipo;
import br.org.cancer.modred.model.IValorGenotipoBusca;
import br.org.cancer.modred.model.IValorGenotipoExpandido;
import br.org.cancer.modred.model.IValorGenotipoPK;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.interfaces.IExameMetodologia;
import br.org.cancer.modred.persistence.IValorGRepository;
import br.org.cancer.modred.persistence.IValorPRepository;
import br.org.cancer.modred.service.IDnaService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.ILocusExameService;
import br.org.cancer.modred.service.IValorGenotipoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.GenotipoUtil;

/**
 * Classe que guarda as funcionalidades envolvendo a entidade Genotipo.
 * @param <T> tipo do valor genótipo para esta classe.
 * @param <I> tipo do ID composto do valor genótipo.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public abstract class ValorGenotipoService<T extends IValorGenotipo, I extends IValorGenotipoPK> extends AbstractService<T, I> implements IValorGenotipoService<T> {
	
	private IGenotipoPacienteService genotipoPacienteService;
	
	@Autowired
	private IGenotipoDoadorService<ExameDoadorNacional> genotipoDoadorNacionalService;
	
	@Autowired
	private IGenotipoDoadorService<ExameDoadorInternacional> genotipoDoadorInternacionalService;
	
	@Autowired
	private IGenotipoDoadorService<ExameCordaoNacional> genotipoCordaoNacionalService;
	
	@Autowired
	private IGenotipoDoadorService<ExameCordaoInternacional> genotipoCordaoInternacionalService;
	
	@Autowired
	private IDnaService dnaService;

	@Autowired
	private ILocusExameService locusExameService;
	
	@Autowired
	private IValorGRepository valorGRepository;
	
	@Autowired
	private IValorPRepository valorPRepository;
	
	/**
	 * Define um factory para criar as instâncias necessárias nessa classe.
	 * 
	 * @param alelo valor que compõe o ID do valor genótipo.
	 * @return uma instância da classe genérica.
	 */
	public abstract T criarInstanciaValorGenotipo(ValorAlelo alelo);
	public abstract IValorGenotipoBusca criarInstanciaValorGenotipoBusca();
	public abstract IValorGenotipoExpandido criarInstanciaValorGenotipoExpandido();
	

	/**
	 * Gera o genótipo a partir dos exames informados.
	 * O genótipo segue as seguintes regras:
	 * São considerados do pior para a melhor resolução, nesta ordem: 
	 * - SOROLOGICO(0), ANTIGENO(1), NMDP(2), GRUPO_G(3), GRUPO_P(4), ALELO(5).
	 * Caso os valores comparados sejam do mesmo tipo, conforme classificação acima, com mesmo peso
	 * a regra é:
	 * O que tiver menor quantidade de valores possíveis na sua lista de compatíveis, em caso de alelos
	 * agrupadores como NMDP, Grupo P e G, ou com maior quantidade de grupos, se for do tipo alelo.
	 * Caso persista o empate, será considera o alelo relativo ao exame mais recente.
	 * 
	 * @param exames lista de exames que originaram o genótipo.
	 * @return lista de valores genótipo.
	 */
	protected List<T> gerarGenotipo(List<? extends Exame> exames) {
		dnaService.criarCacheTemporarioSeNecessario();
		
		List<T> genotipoAtual = new ArrayList<T>();
		
		if (CollectionUtils.isNotEmpty(exames)) {
			boolean isExameUnico = exames.size() == 1;

			
			Exame exameThis = exames.get(0);
			Set<String> codigosLocus = listarTodosCodigosLocus(exameThis);
			

			exames.forEach(exameRef -> {

				codigosLocus.addAll(listarTodosCodigosLocus(exameRef));

				codigosLocus.forEach(codigoLocus -> {
					List<T> genotipoAtualizadoPorLocus = new ArrayList<T>();

					LocusExame locusExameThis = exameThis.obterLocusExame(codigoLocus);
					LocusExame locusExameRef = exameRef.obterLocusExame(codigoLocus);
					boolean isAleloUnico = ( locusExameThis == null || locusExameRef == null );
					boolean naoEstaNoGenotipo = genotipoNaoContemLocus(genotipoAtual, codigoLocus);
					boolean estaNoGenotipo = !naoEstaNoGenotipo;

					if (isExameUnico || naoEstaNoGenotipo) {
						Exame exame = locusExameThis == null ? exameRef : exameThis;
						LocusExame locusExame = locusExameThis == null ? locusExameRef : locusExameThis;
						String primeiroValorAlelo = locusExame.getPrimeiroAlelo();
						String segundoValorAlelo = locusExame.getSegundoAlelo();
						primeiroValorAlelo = GenotipoUtil.normalizarValoresBlank(primeiroValorAlelo, segundoValorAlelo);
						segundoValorAlelo = GenotipoUtil.normalizarValoresBlank(segundoValorAlelo, primeiroValorAlelo);

						ValorAlelo primeiroAlelo = criarInstancia(exame, codigoLocus, primeiroValorAlelo, 1);
						genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAlelo));
						ValorAlelo segundoAlelo = criarInstancia(exame, codigoLocus, segundoValorAlelo, 2);
						genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAlelo));

						genotipoAtual.addAll(genotipoAtualizadoPorLocus);
					}
					else
						if (!isAleloUnico) {
							String primeiroValorAleloThis = locusExameThis.getPrimeiroAlelo();
							String segundoValorAleloThis = locusExameThis.getSegundoAlelo();
							primeiroValorAleloThis = GenotipoUtil.normalizarValoresBlank(primeiroValorAleloThis, segundoValorAleloThis);
							segundoValorAleloThis = GenotipoUtil.normalizarValoresBlank(segundoValorAleloThis, primeiroValorAleloThis);

							String primeiroValorAleloRef = locusExameRef.getPrimeiroAlelo();
							String segundoValorAleloRef = locusExameRef.getSegundoAlelo();
							primeiroValorAleloRef = GenotipoUtil.normalizarValoresBlank(primeiroValorAleloRef, segundoValorAleloRef);
							segundoValorAleloRef = GenotipoUtil.normalizarValoresBlank(segundoValorAleloRef, primeiroValorAleloRef);

							ValorAlelo primeiroAleloThis = criarInstancia(exameThis, codigoLocus, primeiroValorAleloThis, 1);
							ValorAlelo segundoAleloThis = criarInstancia(exameThis, codigoLocus, segundoValorAleloThis, 2);

							ValorAlelo primeiroAleloRef = criarInstancia(exameRef, codigoLocus, primeiroValorAleloRef, 1);
							ValorAlelo segundoAleloRef = criarInstancia(exameRef, codigoLocus, segundoValorAleloRef, 2);

							if (!primeiroAleloThis.discrepante(primeiroAleloRef) && !segundoAleloThis.discrepante(
									segundoAleloRef)) {
								ValorAlelo primeiroAlelo = primeiroAleloThis.melhorResolucao(primeiroAleloRef);
								genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAlelo));
								ValorAlelo segundoAlelo = segundoAleloThis.melhorResolucao(segundoAleloRef);
								genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAlelo));
							}
							else
								if (!primeiroAleloThis.discrepante(segundoAleloRef) && !segundoAleloThis.discrepante(
										primeiroAleloRef)) {
									ValorAlelo primeiroAlelo = primeiroAleloThis.melhorResolucao(segundoAleloRef);
									genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAlelo));
									ValorAlelo segundoAlelo = segundoAleloThis.melhorResolucao(primeiroAleloRef);
									genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAlelo));
								}
								else {
									if(exameThis instanceof IExameMetodologia){
										IExameMetodologia examePacienteThis = (IExameMetodologia) exameThis;
										IExameMetodologia examePacienteRef = (IExameMetodologia) exameRef;

										if(examePacienteThis.obterMaiorPesoMetodologia()> examePacienteRef.obterMaiorPesoMetodologia()){
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAleloThis));
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAleloThis));
										}
										else if(examePacienteThis.obterMaiorPesoMetodologia() < examePacienteRef.obterMaiorPesoMetodologia()){
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAleloRef));
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAleloRef));
										}
										else{
											if (exameThis.getDataCriacao().isAfter(exameRef.getDataCriacao())) {
												genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAleloThis));
												genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAleloThis));
											}
											else {
												genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAleloRef));
												genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAleloRef));
											}		
										}
									}
									else{
										if (exameThis.getDataCriacao().isAfter(exameRef.getDataCriacao())) {
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAleloThis));
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAleloThis));
										}
										else {
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAleloRef));
											genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAleloRef));
										}																				
									}
									
									
									locusExameRef.setPrimeiroAleloDivergente(primeiroAleloThis.discrepante(segundoAleloRef));
									locusExameRef.setSegundoAleloDivergente(segundoAleloThis.discrepante(primeiroAleloRef));
									locusExameService.save(locusExameRef);
								}

							atualizarGenotipoSeNecessario(genotipoAtual, genotipoAtualizadoPorLocus);
						}
						else
							if (locusExameThis == null && locusExameRef != null && estaNoGenotipo) {
								String primeiroValorAleloRef = locusExameRef.getPrimeiroAlelo();
								String segundoValorAleloRef = locusExameRef.getSegundoAlelo();
								primeiroValorAleloRef = GenotipoUtil.normalizarValoresBlank(primeiroValorAleloRef, segundoValorAleloRef);
								segundoValorAleloRef = GenotipoUtil.normalizarValoresBlank(segundoValorAleloRef, primeiroValorAleloRef);

								ValorAlelo primeiroAleloRef = criarInstancia(exameRef, codigoLocus, primeiroValorAleloRef, 1);
								ValorAlelo segundoAleloRef = criarInstancia(exameRef, codigoLocus, segundoValorAleloRef, 2);

								genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(primeiroAleloRef));
								genotipoAtualizadoPorLocus.add(criarInstanciaValorGenotipo(segundoAleloRef));

								atualizarGenotipoSeNecessario(genotipoAtual, genotipoAtualizadoPorLocus);
							}
				});
			});
		}
		
		dnaService.clearCacheTemporario();
		
		return genotipoAtual;
	}

	/**
	 * Lista todos os códigos de locus para os exames informados.
	 * 
	 * @param exameA exame para ter código extraído.
	 * @param exame exame para ter código extraído.
	 * @return lista de códigos exame.
	 */
	private Set<String> listarTodosCodigosLocus(Exame exame) {
		Set<String> codigos = new HashSet<String>();

		if (CollectionUtils.isNotEmpty(exame.getLocusExames())) {
			codigos.addAll(exame.getLocusExames().stream().filter(locusExame -> !locusExame.getInativo())
					.map(locusExame -> locusExame.getId().getLocus().getCodigo()).collect(Collectors.toList()));
		}

		return codigos;
	}

	/**
	 * Atualiza o genótipo se os valores informados forem de melhor resolução que o genótipo atual.
	 * 
	 * @param genotipoAtual genótipo atual do paciente (e que sofrerá as atualizações, caso existam).
	 * @param genotipoAtualizadoPorLocus genótipo com as atualizações.
	 * @return genótipo mais atualizado (maior resolução).
	 */
	private void atualizarGenotipoSeNecessario(List<T> genotipoAtual, List<T> genotipoAtualizadoPorLocus) {
		if (CollectionUtils.isNotEmpty(genotipoAtualizadoPorLocus)) {
			T novoGenotipoPrimeiroAlelo = genotipoAtualizadoPorLocus.get(0);
			T novoGenotipoSegundoAlelo = genotipoAtualizadoPorLocus.get(1);

			String codigoLocus = novoGenotipoPrimeiroAlelo.getId().getLocus().getCodigo();

			T genotipoAtualPrimeiroAlelo = obterGenotipo(genotipoAtual, codigoLocus, 1);
			T genotipoAtualSegundoAlelo = obterGenotipo(genotipoAtual, codigoLocus, 2);

			ValorAlelo primeiroAleloNovo = criarInstanciaValorAlelo(novoGenotipoPrimeiroAlelo, codigoLocus);
			ValorAlelo segundoAleloNovo = criarInstanciaValorAlelo(novoGenotipoSegundoAlelo, codigoLocus);

			ValorAlelo primeiroAleloAtual = criarInstanciaValorAlelo(genotipoAtualPrimeiroAlelo, codigoLocus);
			ValorAlelo segundoAleloAtual = criarInstanciaValorAlelo(genotipoAtualSegundoAlelo, codigoLocus);

			ValorAlelo primeiroAleloAtualizado = null;
			ValorAlelo segundoAleloAtualizado = null;

			if (!primeiroAleloAtual.discrepante(primeiroAleloNovo) && !segundoAleloAtual.discrepante(segundoAleloNovo)) {
				primeiroAleloAtualizado = primeiroAleloAtual.melhorResolucao(primeiroAleloNovo);
				segundoAleloAtualizado = segundoAleloAtual.melhorResolucao(segundoAleloNovo);
			}
			else {
				primeiroAleloAtualizado = primeiroAleloAtual.melhorResolucao(segundoAleloNovo);
				segundoAleloAtualizado = segundoAleloAtual.melhorResolucao(primeiroAleloNovo);
				primeiroAleloAtualizado.setDivergente(true);
				segundoAleloAtualizado.setDivergente(true);
			}

			atualizarGenotipo(genotipoAtual, primeiroAleloAtualizado, segundoAleloAtualizado);
		}
	}

	private ValorAlelo criarInstanciaValorAlelo(T novoGenotipoPrimeiroAlelo, String codigoLocus) {
		ValorAlelo primeiroAleloAtualizado = criarInstancia(novoGenotipoPrimeiroAlelo.getId().getExame(), codigoLocus,
				novoGenotipoPrimeiroAlelo.getAlelo(), novoGenotipoPrimeiroAlelo.getId().getPosicaoAlelo());
		return primeiroAleloAtualizado;
	}

	/**
	 * Atualiza o genótipo com os valores mais atualizados de alelos.
	 * 
	 * @param genotipo a ser atualizado.
	 * @param primeiroAleloAtualizado primeiro valor de alelo atualizado.
	 * @param segundoAleloAtualizado segundo valor de alelo atualizado.
	 */
	private void atualizarGenotipo(List<T> genotipo, ValorAlelo primeiroAleloAtualizado,
			ValorAlelo segundoAleloAtualizado) {
		List<T> genotipoAtualizado = genotipo.stream().filter(gen -> {
			return !primeiroAleloAtualizado.getCodigoLocus().equals(gen.getId().getLocus().getCodigo());
		}).collect(Collectors.toList());

		genotipoAtualizado.add(criarInstanciaValorGenotipo(primeiroAleloAtualizado));
		genotipoAtualizado.add(criarInstanciaValorGenotipo(segundoAleloAtualizado));

		genotipo.clear();
		genotipo.addAll(genotipoAtualizado);
	}

	private boolean genotipoNaoContemLocus(List<T> genotipo, String codigoLocus) {
		return obterGenotipo(genotipo, codigoLocus, 1) == null && obterGenotipo(genotipo, codigoLocus, 2) == null;
	}

	private T obterGenotipo(List<T> genotipo, String codigoLocus, Integer posicaoAlelo) {
		Optional<T> encontrado = genotipo.stream().filter(gen -> {
			return codigoLocus.equals(gen.getId().getLocus().getCodigo()) && posicaoAlelo.equals(gen.getId().getPosicaoAlelo());
		}).findFirst();
		return encontrado.isPresent() ? encontrado.get() : null;
	}

	/**
	 * Cria uma instância do objeto a partir do formato do valorAlelico informado.
	 * 
	 * @param exame exame a que o objeto pertence.
	 * @param codigoLocus código do lócus a ser associado.
	 * @param valorAlelico valor do alelo.
	 * @param posicao posição do alelo (1 ou 2)
	 * @return uma nova instância que extende de ValorAlelo e representa um valor alélico na montagem do genótipo.
	 */
	private ValorAlelo criarInstancia(Exame exame, String codigoLocus, String valorAlelico, Integer posicao) {
		ValorAlelo alelo = dnaService.obterAleloTipado(codigoLocus, valorAlelico);
		alelo.setPosicao(posicao);
		alelo.setExame(exame);
		return alelo;
	}

	/**
	 * @fixme corrigir onde for chamado passando o genótipo a partir do paciente.
	 */
	@Override
	public List<GenotipoDTO> obterGenotipoDto(IGenotipo genotipo) {
		List<GenotipoDTO> genotiposRetornoDTO = new ArrayList<GenotipoDTO>();

		if (genotipo != null) {
			List<T> genotipos = obterValoresGenotipo(genotipo);
			List<String> codigoLocusSemDuplicados = recuperaListaComTodosOsLocus(genotipos);

			codigoLocusSemDuplicados.forEach(codigo -> {
				List<T> genotiposAgrupados = genotipos.stream().filter(g -> {
					return g.getId().getLocus().getCodigo().equals(codigo);
				}).collect(Collectors.toList());

				genotiposRetornoDTO.add(criaGenotipoDTO(genotiposAgrupados));

			});
		}

		return genotiposRetornoDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GenotipoDTO> obterGenotipoDtoPorValorGenotipo(List<T> valoresGenotipo) {
		List<GenotipoDTO> genotiposRetornoDTO = new ArrayList<GenotipoDTO>();

		List<String> codigoLocusSemDuplicados = recuperaListaComTodosOsLocus(valoresGenotipo);

		codigoLocusSemDuplicados.forEach(codigo -> {
			List<T> genotiposAgrupados = valoresGenotipo.stream().filter(genotipo -> {
				return genotipo.getId().getLocus().getCodigo().equals(codigo);
			}).collect(Collectors.toList());

			genotiposRetornoDTO.add(criaGenotipoDTO(genotiposAgrupados));

		});

		Collections.sort(genotiposRetornoDTO, new Comparator<GenotipoDTO>() {

			@Override
			public int compare(GenotipoDTO genotipoDTO1, GenotipoDTO genotipoDTO2) {
				return genotipoDTO1.getOrdem().compareTo(genotipoDTO2.getOrdem());
			}
		});

		return genotiposRetornoDTO;
	}

	private GenotipoDTO criaGenotipoDTO(List<T> genotiposAgrupados) {

		GenotipoDTO genotipoDTO = new GenotipoDTO();

		for (int i = 0; i < genotiposAgrupados.size(); i++) {
			genotipoDTO.setLocus(genotiposAgrupados.get(i).getId().getLocus().getCodigo());
			String alelo = genotiposAgrupados.get(i).getAlelo();
			Integer tipo = genotiposAgrupados.get(i).getTipo();
			if (i == 0) {
				genotipoDTO.setPrimeiroAlelo(alelo);
				genotipoDTO.setExamePrimeiroAlelo(genotiposAgrupados.get(i).getId().getExame().getId().toString());
				genotipoDTO.setTipoPrimeiroAlelo(tipo);
				genotipoDTO.setDivergentePrimeiroAlelo(genotiposAgrupados.get(i).getDivergente());
			}
			else {
				genotipoDTO.setSegundoAlelo(alelo);
				genotipoDTO.setExameSegundoAlelo(genotiposAgrupados.get(i).getId().getExame().getId().toString());
				genotipoDTO.setTipoSegundoAlelo(tipo);
				genotipoDTO.setDivergenteSegundoAlelo(genotiposAgrupados.get(i).getDivergente());
			}
			genotipoDTO.setOrdem(genotiposAgrupados.get(i).getId().getLocus().getOrdem());

		}
		return genotipoDTO;
	}

	private List<String> recuperaListaComTodosOsLocus(List<T> genotipos) {
		return genotipos.stream().map(genotipo -> {
			return genotipo.getId().getLocus().getCodigo();
		}).distinct().collect(Collectors.toList());
	}

	/**
	 * Gera os valores genótipo busca de acordo com o parâmetro.
	 * 
	 * @param valorGenotipo valor genótipo a ser associado.
	 * @return lista de valores genótipo busca instanciados.
	 */
	protected List<? extends IValorGenotipoBusca> gerarGenotipoBusca(T valorGenotipo) {
		ValorAlelo alelo = dnaService.obterAleloTipado(valorGenotipo.getId().getLocus().getCodigo(), valorGenotipo.getAlelo()); 
		alelo.setPosicao(valorGenotipo.getId().getPosicaoAlelo());

		final int posicaoAlelo = alelo.getPosicao();
		final int tipoAlelo = alelo.getComposicaoAlelo().getPeso();
		valorGenotipo.setTipo(tipoAlelo);
		List<IValorGenotipoBusca> genotipos = new ArrayList<>();
		if (alelo.getValoresCompativeis() != null) {
			genotipos.addAll(alelo.getValoresCompativeis().stream().map(valorCompativel -> {
				IValorGenotipoBusca valorGenotipoBusca = criarInstanciaValorGenotipoBusca();
				valorGenotipoBusca.setGenotipo(valorGenotipo.getGenotipo());
				valorGenotipoBusca.setLocus(valorGenotipo.getId().getLocus());
				valorGenotipoBusca.setPosicao(posicaoAlelo);
				valorGenotipoBusca.setTipo(tipoAlelo);
				valorGenotipoBusca.setValor(GenotipoUtil.extrairAleloParaGenotipoBusca(valorCompativel));				
				return valorGenotipoBusca;
			})//.filter(GenotipoUtil.distinctByKey(IValorGenotipoBusca::getValor))
					.collect(Collectors.toList()));
		}
		if ((alelo.getComposicaoAlelo().equals(ComposicaoAlelo.ALELO) || alelo.getComposicaoAlelo().equals(ComposicaoAlelo.NMDP))
				&& alelo.getGrupos() != null && !alelo.getGrupos().isEmpty()  ) {
		
			List<List<IValorGenotipoBusca>> listaGrupo = alelo.getGrupos().stream().map(nomeGrupo -> {
				String grupo = "";
				if (GenotipoUtil.obterTipoGrupo(nomeGrupo).equals(GenotipoUtil.GRUPO_G)) {
					grupo = valorGRepository.obterValorG(alelo.getCodigoLocus(), nomeGrupo).getGrupo();
				}
				else {
					grupo = valorPRepository.obterValorP(alelo.getCodigoLocus(), nomeGrupo).getGrupo();
				}
				String[] splitGrupo = grupo.split("/");
				List<IValorGenotipoBusca> retornoMap = new ArrayList<>();
				for (String valor: splitGrupo) {
					IValorGenotipoBusca valorGenotipoBusca = criarInstanciaValorGenotipoBusca();
					valorGenotipoBusca.setGenotipo(valorGenotipo.getGenotipo());
					valorGenotipoBusca.setLocus(valorGenotipo.getId().getLocus());
					valorGenotipoBusca.setPosicao(posicaoAlelo);
					valorGenotipoBusca.setTipo(tipoAlelo);
					valorGenotipoBusca.setValor(GenotipoUtil.extrairAleloParaGenotipoBusca(valor));										
					retornoMap.add(valorGenotipoBusca);
				}
				return retornoMap;
			})
			.collect(Collectors.toList());
			
			listaGrupo.stream().forEach(li -> genotipos.addAll(li));
			
		}
		
		return genotipos.stream().filter(GenotipoUtil.distinctByKey(IValorGenotipoBusca::getValor))
					.collect(Collectors.toList());
	}

	/**
	 * Gera o genótipo expandido de acordo com os parâmetros informados.
	 * 
	 * @param valorGenotipo valor genótipo a ser associado.
	 * @param proprietario proprietário do valor (paciente ou doador).
	 * @return lista de valores instanciados.
	 */
	protected List<? extends IValorGenotipoExpandido> gerarGenotipoExpandido(T valorGenotipo, IProprietarioHla proprietario) {
		ValorAlelo alelo = dnaService.obterAleloTipado(valorGenotipo.getId().getLocus().getCodigo(), valorGenotipo.getAlelo());
		alelo.setPosicao(valorGenotipo.getId().getPosicaoAlelo());

		final int posicaoAlelo = alelo.getPosicao();
		List<IValorGenotipoExpandido> genotipos = new ArrayList<>();
		if (alelo.getValoresCompativeis() != null) {
			genotipos.addAll(alelo.getValoresCompativeis().stream().map(valorCompativel -> {
				IValorGenotipoExpandido valorGenotipoExpandido = criarInstanciaValorGenotipoExpandido();
				valorGenotipoExpandido.setGenotipo(valorGenotipo.getGenotipo());
				valorGenotipoExpandido.setLocus(valorGenotipo.getId().getLocus());
				valorGenotipoExpandido.setPosicao(posicaoAlelo);
				valorGenotipoExpandido.setValor(Long.valueOf(GenotipoUtil.extrairAleloParaGenotipoExpandido(valorCompativel)));
				valorGenotipoExpandido.setProprietario(proprietario);
				
				return valorGenotipoExpandido;
				
			})//.filter(GenotipoUtil.distinctByKey(IValorGenotipoExpandido::getValor))
					.collect(Collectors.toList()));
		}
		if ((alelo.getComposicaoAlelo().equals(ComposicaoAlelo.ALELO) || alelo.getComposicaoAlelo().equals(ComposicaoAlelo.NMDP))
				&& alelo.getGrupos() != null && !alelo.getGrupos().isEmpty()) {
			
			genotipos.addAll(alelo.getGrupos().stream().map(nomeGrupo -> {
				String grupo = "";
				if (GenotipoUtil.obterTipoGrupo(nomeGrupo).equals(GenotipoUtil.GRUPO_G)) {
					grupo = valorGRepository.obterValorG(alelo.getCodigoLocus(), nomeGrupo).getGrupo();
				}
				else {
					grupo = valorPRepository.obterValorP(alelo.getCodigoLocus(), nomeGrupo).getGrupo();
				}
				String[] splitGrupo = grupo.split("/");
				List<IValorGenotipoExpandido> retornoMap = new ArrayList<>();
				for (String valor: splitGrupo) {
					IValorGenotipoExpandido valorGenotipoExpandido = criarInstanciaValorGenotipoExpandido();
					valorGenotipoExpandido.setGenotipo(valorGenotipo.getGenotipo());
					valorGenotipoExpandido.setLocus(valorGenotipo.getId().getLocus());
					valorGenotipoExpandido.setPosicao(posicaoAlelo);
					valorGenotipoExpandido.setValor(Long.valueOf(GenotipoUtil.extrairAleloParaGenotipoExpandido(valor)));
					valorGenotipoExpandido.setProprietario(proprietario);										
					retornoMap.add(valorGenotipoExpandido);
				}
				return retornoMap;
				
				
			})
			.collect(Collectors.toList()).get(0));
		}
		
		return genotipos.stream().filter(GenotipoUtil.distinctByKey(IValorGenotipoExpandido::getValor))
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> obterValoresGenotipo(IGenotipo genotipoParam) {
		if (genotipoParam == null) {
			return new ArrayList<T>();
		}
		IGenotipo genotipo = null;
		if(genotipoParam instanceof GenotipoDoador){
			if (genotipoParam.getTipoDoador().equals(TiposDoador.NACIONAL) ) {
				genotipo = genotipoDoadorNacionalService.obterGenotipo(genotipoParam.getId());
			}
			else if (genotipoParam.getTipoDoador().equals(TiposDoador.INTERNACIONAL) ) {
				genotipo = genotipoDoadorInternacionalService.obterGenotipo(genotipoParam.getId());
			}
			else if (genotipoParam.getTipoDoador().equals(TiposDoador.CORDAO_NACIONAL) ) {
				genotipo = genotipoCordaoNacionalService.obterGenotipo(genotipoParam.getId());
			}
			else if (genotipoParam.getTipoDoador().equals(TiposDoador.CORDAO_INTERNACIONAL) ) {
				genotipo = genotipoCordaoInternacionalService.obterGenotipo(genotipoParam.getId());
			}			
		}
		else{
			genotipo = genotipoPacienteService.obterGenotipo(genotipoParam.getId());
		}
		if (genotipo == null) {
			return null;
		}
		else {
			List<T> listaRetorno = (List<T>) genotipo.getValores();
			Collections.sort(listaRetorno, new Comparator<T>() {

				@Override
				public int compare(T valorGenotipo1, T valorGenotipo2) {
					if (valorGenotipo1.getLocus() != null && valorGenotipo1.getLocus().getOrdem() != null) {
						return valorGenotipo1.getLocus().getOrdem().compareTo(valorGenotipo2.getLocus().getOrdem());
					}
					else {
						return 0;
					}

				}
			});
			return listaRetorno;
		}
	}
	/**
	 * @param genotipoPacienteService the genotipoPacienteService to set
	 */
	@Autowired
	public void setGenotipoPacienteService(IGenotipoPacienteService genotipoPacienteService) {
		this.genotipoPacienteService = genotipoPacienteService;
	}
	

}
