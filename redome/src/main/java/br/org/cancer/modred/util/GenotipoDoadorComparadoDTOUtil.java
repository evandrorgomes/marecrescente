package br.org.cancer.modred.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.controller.dto.ConverterEntidadesEmDTO;
import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.controller.dto.GenotipoDoadorComparadoDTO;
import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.GenotipoDoador;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IQualificacaoMatchService;
import br.org.cancer.modred.service.IValorGenotipoService;
import br.org.cancer.modred.service.impl.GenotipoDoadorService;
import br.org.cancer.modred.service.impl.QualificacaoMatchService;
import br.org.cancer.modred.service.impl.ValorGenotipoPacienteService;

/**
 * Classe Util para GenotipoDoadorComparadoDTO.
 * 
 * @author brunosousa
 *
 */
public class GenotipoDoadorComparadoDTOUtil {
	
	private GenotipoDoadorComparadoDTOUtil() {
	}
	
	/**
	 * Método responsável montar o GenotipoDoadorComparadoDTO pelo MatchDTO.
	 * 
	 * @param match - MatchDTO
	 * @return genotipoDoadorComparadoDTO.
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static GenotipoDoadorComparadoDTO montarDtoComGenotipoComparadoDoDoador(MatchDTO match) {
		
		List<GenotipoDTO> listaGenotiposDTO = new ArrayList<>();
		
		if (match.getTipoMatch().equals("MATCH")) {
			
			IGenotipoDoadorService<Exame> genotipoDoadorService = ApplicationContextProvider.obterBean(GenotipoDoadorService.class); 		
			IQualificacaoMatchService qualificacaoMatchService = ApplicationContextProvider.obterBean(QualificacaoMatchService.class); 		
			
			GenotipoDoador genotipoDoador = genotipoDoadorService.obterGenotipoPorDoador(match.getIdDoador());
			if(genotipoDoador != null) {
				IValorGenotipoService valorGenotipoService = ApplicationContextProvider.obterBean(ValorGenotipoPacienteService.class);
				
				final List<GenotipoDTO> recuperalistaGenotiposDTO = valorGenotipoService.obterGenotipoDtoPorValorGenotipo(genotipoDoador.getValores());
				final List<IQualificacaoMatch> qualificacoes = qualificacaoMatchService.listarQualificacaoMatchPorMatchId(match.getId());
				
				listaGenotiposDTO = recuperalistaGenotiposDTO
						.stream().map(genotipo -> populandoGenotipoDTOPorQualificacoes(qualificacoes, genotipo))
						.filter(Objects::nonNull).collect(Collectors.toList());
			}
		}

//		if (match.getTipoMatch().equals("MATCHPRELIMINAR")) {
//			IQualificacaoMatchPreliminarService qualificacaoMatchPreliminarService = ApplicationContextProvider.obterBean(QualificacaoMatchPreliminarService.class);
//			final List<IQualificacaoMatch> qualificacoes = qualificacaoMatchPreliminarService.listarQualificacaoMatchPreliminarPorMatchPreliminarId(match.getId());
//			List<String> codigoLocusSemDuplicados = recuperaListaComTodosOsLocusDaQualificacaoMatch(qualificacoes);
//			
//			genotiposDTO = codigoLocusSemDuplicados
//					.stream().map(locus -> converterGenotipoDoDoadorEmGenotipoDTO(qualificacoes, locus))
//					.filter(Objects::nonNull).collect(Collectors.toList());
//			
//		}

		Collections.sort(listaGenotiposDTO, new Comparator<GenotipoDTO>() {
			@Override
			public int compare(GenotipoDTO genotipoDTO1, GenotipoDTO genotipoDTO2) {
				if (genotipoDTO1.getOrdem() != null) {
					return genotipoDTO1.getOrdem().compareTo(genotipoDTO2.getOrdem());
				}
				else {
					return 0;
				}
			}
		});

		GenotipoDoadorComparadoDTO genotipoDoadorComparadoDTO = ConverterEntidadesEmDTO.DOADOR_PARA_GENOTIPO_DOADOR_COMPARADODTO.apply(match);
		genotipoDoadorComparadoDTO.setGenotipoDoador(listaGenotiposDTO);
		genotipoDoadorComparadoDTO.setClassificacao(match.getClassificacao());
		genotipoDoadorComparadoDTO.setMismatch(match.getMismatch());
		genotipoDoadorComparadoDTO.setFase(match.getFase());
		if(match.getDataNascimento() != null) {
			genotipoDoadorComparadoDTO.setIdade(DateUtils.calcularIdade(match.getDataNascimento()));
		}
		genotipoDoadorComparadoDTO.setQuantidadeTCNPorKilo(match.getQuantidadeTCNPorKilo());
		genotipoDoadorComparadoDTO.setQuantidadeCD34PorKilo(match.getQuantidadeCD34PorKilo());	
		
		if(match.getTipoPermissividade() != null) {
			genotipoDoadorComparadoDTO.setDescricaoTipoPermissividade(match.getTipoPermissividade().getDescricao());
		}
		
		genotipoDoadorComparadoDTO.setIdentificadorDoador(popularIdentificacaoDoador(match));
		
		return genotipoDoadorComparadoDTO;
	}

	private static String popularIdentificacaoDoador(MatchDTO match) {
		if (match.getDmr() != null) {
			return match.getDmr().toString();
		}
		else {
			return match.getIdRegistro();
		}
	}
	
	private static GenotipoDTO populandoGenotipoDTOPorQualificacoes(List<IQualificacaoMatch> qualificacoes, GenotipoDTO genotipo) {
		
		try {
			IQualificacaoMatch qualificacaoRecuperada = qualificacoes.stream()
					.filter(qualificacao -> genotipo.getLocus().equals(qualificacao.getLocus().getCodigo()))
					.findFirst().orElse(null);
			if(qualificacaoRecuperada != null) {
				genotipo.setProbabilidade(qualificacaoRecuperada.getProbabilidade());
				genotipo.setQualificacaoAlelo(qualificacaoRecuperada.getQualificacao());
			}
		}
		catch (Exception e) {
			System.out.println(e.getCause());
		}

		return genotipo; 
	}
	
	
//	private static List<String> recuperaListaComTodosOsLocusDaQualificacaoMatch(List<? extends IQualificacaoMatch> qualificacoes) {
//		return qualificacoes.stream().map(qualificacao -> {
//			return qualificacao.getLocus().getCodigo();
//		}).distinct().collect(Collectors.toList());
//	}
	
//	private static GenotipoDTO converterGenotipoDoDoadorEmGenotipoDTO(List<IQualificacaoMatch> qualificacoes  , String locus) {
//		
//		Supplier<Stream<GenotipoDTO>> genotiposDTOComTodosAlelos = () -> qualificacoes.stream()
//				.filter(qualificacao -> qualificacao.getLocus().getCodigo().equals(locus))
//				.map(qualificacao -> ConverterEntidadesEmDTO.QUALIFICACAO_MATCH_PARA_GENOTIPO_DTO.apply(qualificacao));
//
//		GenotipoDTO genotipoDTOUnificado = recuperarAlelo(genotiposDTOComTodosAlelos); 
//		
//		GenotipoDTO genotipoDTOUnificado = recuperarPrimeiroAlelo(genotiposDTOComTodosAlelos);
//		genotipoDTOUnificado.setSegundoAlelo(recuperarSegundoAlelo(genotiposDTOComTodosAlelos));
//		genotipoDTOUnificado.setTipoSegundoAlelo(recuperarTipoSegundoAlelo(genotiposDTOComTodosAlelos));
//		genotipoDTOUnificado.setQualificacaoSegundoAlelo(recuperarQualificacaoSegundoAlelo(genotiposDTOComTodosAlelos));
//		
//		return new GenotipoDTO();
//	}
	
//	private static String recuperarQualificacaoSegundoAlelo(Supplier<Stream<GenotipoDTO>> genotiposDTOComTodosAlelos) {		
//		return genotiposDTOComTodosAlelos
//				.get()
//				.filter(genotipo -> genotipo.getSegundoAlelo() != null)
//				.map(genotipo -> genotipo.getQualificacaoSegundoAlelo())
//				.findFirst().get();
//	}

//	private static Integer recuperarTipoSegundoAlelo(Supplier<Stream<GenotipoDTO>> genotiposDTOComTodosAlelos) {
//		return genotiposDTOComTodosAlelos
//				.get()
//				.filter(genotipo -> genotipo.getSegundoAlelo() != null)
//				.map(genotipo -> genotipo.getTipoSegundoAlelo())
//				.findFirst().get();
//	}

//	private static String recuperarSegundoAlelo(Supplier<Stream<GenotipoDTO>> genotiposDTOComTodosAlelos) {
//		return genotiposDTOComTodosAlelos
//				.get()
//				.filter(genotipo -> genotipo.getSegundoAlelo() != null)
//				.map(genotipo -> genotipo.getSegundoAlelo())
//				.findFirst().get();
//	}

//	private static GenotipoDTO recuperarPrimeiroAlelo(Supplier<Stream<GenotipoDTO>> genotiposDTOComTodosAlelos) {
//		return genotiposDTOComTodosAlelos
//				.get()
//				.filter(genotipo -> genotipo.getPrimeiroAlelo() != null)
//				.findFirst().get();
//	}
	
}
