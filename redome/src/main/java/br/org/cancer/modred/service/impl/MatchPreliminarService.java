package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.model.BuscaPreliminar;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.MatchPreliminar;
import br.org.cancer.modred.model.QualificacaoMatchPreliminar;
import br.org.cancer.modred.model.ValorGenotipoDoador;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.persistence.IMatchPreliminarRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IMatchPreliminarService;
import br.org.cancer.modred.service.IQualificacaoMatchPreliminarService;
import br.org.cancer.modred.service.IValorGenotipoDoadorService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.MatchDTOUtil;
import br.org.cancer.modred.vo.GenotipoDoadorVO;
import br.org.cancer.modred.vo.ViewMatchPreliminarQualificacaoVO;

/**
 * Implementa os métodos e as regras de negócio envolvendo a entidade MatchPreliminar.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class MatchPreliminarService extends AbstractService<MatchPreliminar, Long> implements IMatchPreliminarService{

	@Autowired
	private IMatchPreliminarRepository matchPreliminarRepositorio;

	@Autowired
	private IValorGenotipoDoadorService<Exame> valorGenotipoDoadorService;
	
	@Autowired
	private IQualificacaoMatchPreliminarService qualificacaoMatchPreliminarService;
	
	@Override
	public IRepository<MatchPreliminar, Long> getRepository() {
		return matchPreliminarRepositorio;
	}
	
	@Override
	public void executarProcedureMatch(Long idBuscaPreliminar){
		entityManager.flush();
		matchPreliminarRepositorio.executarProcedureMatch(idBuscaPreliminar);
	}

	@Override
	public Long obterQuantidadeMatchsMedula(Long idBuscaPreliminar) {
		return matchPreliminarRepositorio.obterQuantidadeMatchs(
				idBuscaPreliminar, Arrays.asList(TiposDoador.NACIONAL.getId(), TiposDoador.INTERNACIONAL.getId()));
	}

	@Override
	public Long obterQuantidadeMatchsCordao(Long idBuscaPreliminar) {
		return matchPreliminarRepositorio.obterQuantidadeMatchs(
				idBuscaPreliminar, Arrays.asList(TiposDoador.CORDAO_NACIONAL.getId(), TiposDoador.CORDAO_INTERNACIONAL.getId()));
	}

	@Override
	public List<MatchDTO> listarMatchsPreliminares(Long idBuscaPreliminar, FiltroMatch filtroMatch, List<FasesMatch> fases) {
		
		List<Object[]> matchs = matchPreliminarRepositorio.listarMatchsPreliminares(idBuscaPreliminar, filtroMatch.getTiposDoadorAssociados(), fases);
		
		return matchs.stream().map(MatchDTOUtil::popularMatchDTO).collect(Collectors.toList());
	}

	@Override
	public List<MatchDTO> listarMatchsAtivosPorIDBuscaPreliminarAndListaIdsDoador(Long idBuscaPreliminar,
			List<Long> listaIdsDoador) {
		
		List<Object[]> matchs = matchPreliminarRepositorio.listarMatchsPorBuscaPreliminarIdDoador(idBuscaPreliminar, 
				listaIdsDoador);
		
		return matchs.stream().map(MatchDTOUtil::popularMatchDTO).collect(Collectors.toList());
	}
	
	
	@Override
	public void criarMatchPreliminar(Long idBuscaPreliminar) {
		
		List<ViewMatchPreliminarQualificacaoVO> matchs = matchPreliminarRepositorio.listarViewMatchPreliminarQualificacao(idBuscaPreliminar);
		
		Map<Long, List<ViewMatchPreliminarQualificacaoVO>> matchsAgrupadoPorDoador = matchs.stream()
				.collect(Collectors.groupingBy(ViewMatchPreliminarQualificacaoVO::getIdGenotipoDoador));
		
		matchsAgrupadoPorDoador.forEach((idGenotipoDoador, lista) -> {
			
			final List<String> listaMismatch = new ArrayList<>();
			int[] totalMatch = {0}; 
			
			List<ValorGenotipoDoador> valoresGenotipoDoador = valorGenotipoDoadorService.listarPorGenotipoDoadorId(idGenotipoDoador);
			
			List<GenotipoDoadorVO> genotiposDoador =  new ArrayList<>();
			
			lista.forEach(view -> 
				
				totalMatch[0] += criarGenotipoDoadorVO(listaMismatch, valoresGenotipoDoador, genotiposDoador, view)
				 
			);
			
			final String match = StringUtils.leftPad(totalMatch[0]+"", 2, "0") +"/" + StringUtils.leftPad( (lista.size() * 2) +"", 2, "0");
			if (match.equals("05/06") || match.equals("06/06") || match.equals("07/08") || match.equals("08/08") || match.equals("09/10") || match.equals("10/10")) {
				Doador doador = (Doador) valoresGenotipoDoador.get(0).getGenotipo().getProprietario();
				salvarMatchPreliminar(idBuscaPreliminar, doador, match, listaMismatch, genotiposDoador);
			}
			
		});
		
		
	}

	/**
	 * @param idBuscaPreliminar
	 * @param listaMismatch
	 * @param genotiposDoador
	 */
	private void salvarMatchPreliminar(Long idBuscaPreliminar, Doador doador, String match, final List<String> listaMismatch,
			List<GenotipoDoadorVO> genotiposDoador) {
		MatchPreliminar matchPreliminar = new MatchPreliminar();
		matchPreliminar.setBuscaPreliminar(new BuscaPreliminar(idBuscaPreliminar));
		matchPreliminar.setDataCriacao(LocalDateTime.now());
		matchPreliminar.setDoador(doador);
		
		matchPreliminar.setMismatch(!listaMismatch.isEmpty() ? StringUtils.join(listaMismatch.toArray(), ", ") : null );
		matchPreliminar.setGrade(match);
		
		save(matchPreliminar);
		
		genotiposDoador.stream().map(genotipoDoador -> {
			
			QualificacaoMatchPreliminar qualificacao = new QualificacaoMatchPreliminar();
			qualificacao.setLocus(new Locus(genotipoDoador.getCodigoLocus()));
			qualificacao.setQualificacao(genotipoDoador.getQualificacao());
			qualificacao.setPosicao(genotipoDoador.getPosicao().intValue());
			qualificacao.setMatch(matchPreliminar);
			qualificacao.setGenotipo(genotipoDoador.getGenotipo());
			qualificacao.setTipo(genotipoDoador.getTipo().intValue());
								
			return qualificacao;
		})
		.forEach(qualificacaoMatchPreliminar -> {
			qualificacaoMatchPreliminarService.save(qualificacaoMatchPreliminar);
		});
	}

	/**
	 * @param listaMismatch
	 * @param totalMatch
	 * @param valoresGenotipoDoador
	 * @param genotiposDoador
	 * @param view
	 */
	private int criarGenotipoDoadorVO(final List<String> listaMismatch, 
			List<ValorGenotipoDoador> valoresGenotipoDoador, List<GenotipoDoadorVO> genotiposDoador,
			ViewMatchPreliminarQualificacaoVO view) {
		
		int totalMatch = 0;		
		
		Optional<String> opctionaValorAlelo1 = valoresGenotipoDoador.stream()
				.filter(valorGenotipoDoador ->	valorGenotipoDoador.getLocus().getCodigo().equals(view.getCodigoLocus()) && 
						valorGenotipoDoador.getPosicao().equals(1))
				.map(ValorGenotipoDoador::getAlelo)
				.findFirst();
		String valorAlelo1 = "";
		if (opctionaValorAlelo1.isPresent()) {
			valorAlelo1 = opctionaValorAlelo1.get();
		}
		
		Optional<String> opctionaValorAlelo2 = valoresGenotipoDoador.stream()
				.filter(valorGenotipoDoador ->	valorGenotipoDoador.getLocus().getCodigo().equals(view.getCodigoLocus()) && 
						valorGenotipoDoador.getPosicao().equals(2))
				.map(ValorGenotipoDoador::getAlelo)
				.findFirst();
		String valorAlelo2 = "";		
		if (opctionaValorAlelo2.isPresent()) {
			valorAlelo2 = opctionaValorAlelo2.get();
		}
		
		if (view.getTroca().equals(1L)) {
		    final String valorAux = valorAlelo1;
		    valorAlelo1 = valorAlelo2;
		    valorAlelo2 = valorAux;
		}
		
		if ( view.getLetraPosicao1().equals("M") || view.getLetraPosicao1().equals("L") || 
				view.getLetraPosicao2().equals("M") || view.getLetraPosicao2().equals("L")) { 
		   listaMismatch.add(view.getCodigoLocus());
		}
		
		if (view.getTipoPosicao1() != null) {    
		   if (view.getLetraPosicao1().equals("P") ||  view.getLetraPosicao1().equals("A")) {
		      totalMatch += 1;
		   }		           
		   genotiposDoador.add(new GenotipoDoadorVO(view.getCodigoLocus(), view.getTipoPosicao1(), 1L, view.getLetraPosicao1(), valorAlelo1));
		}
		
		if (view.getTipoPosicao2() != null) {
		   if (view.getLetraPosicao2().equals("P") ||  view.getLetraPosicao2().equals("A")) {
		      totalMatch += 1;
		   }		           
		   genotiposDoador.add(new GenotipoDoadorVO(view.getCodigoLocus(), view.getTipoPosicao2(), 2L, view.getLetraPosicao2(), valorAlelo2));
		}
		
	    return totalMatch;
	}
	
	
	
	
}
