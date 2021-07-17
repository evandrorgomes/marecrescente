package br.org.cancer.modred.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.QualificacaoMatchDTO;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.QualificacaoMatch;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;
import br.org.cancer.modred.persistence.IQualificacaoMatchRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IQualificacaoMatchService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação de classe de negócios de Match.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class QualificacaoMatchService extends AbstractService<QualificacaoMatch, Long> implements IQualificacaoMatchService {

	@Autowired
	private IQualificacaoMatchRepository qualificacaoMatchRepository;

	/**
	 * Construtor definido para que seja informado as estratégias
	 * para os eventos de criação de notificação.
	 */
	public QualificacaoMatchService() {
		super();		
	}
	
	@Override
	public IRepository<QualificacaoMatch, Long> getRepository() {
		return qualificacaoMatchRepository;
	}
	
	@Override
	public List<IQualificacaoMatch> listarQualificacaoMatchPorMatchId(Long matchId) {
		return qualificacaoMatchRepository.findByMatchId(matchId);
	}

	@Override
	public void criarListaDeQualificacaoMatch(Match match, List<QualificacaoMatchDTO> qualificacoes) {

		qualificacoes.stream().forEach(qualificacao -> {
		
			QualificacaoMatch novaQualificacao = new QualificacaoMatch(); 
			novaQualificacao.setMatch(match);
			novaQualificacao.setDoador(match.getDoador().getId());
			novaQualificacao.setQualificacao(qualificacao.getQualificacao());
			novaQualificacao.setProbabilidade(qualificacao.getProbabilidade().getProbabilidade());
			
			Locus novoLocus = new Locus();
			novoLocus.setCodigo(qualificacao.getLocus());
			novaQualificacao.setLocus(novoLocus);
			
			qualificacaoMatchRepository.save(novaQualificacao);
		});
	}

	@Override
	public void atualizarListaDeQualificacaoMatch(Match match, List<QualificacaoMatchDTO> qualificacoes) {

		List<IQualificacaoMatch> listaQualificacoes = listarQualificacaoMatchPorMatchId(match.getId());
		listaQualificacoes.stream().forEach(qualificacao -> { 
			qualificacaoMatchRepository.delete((QualificacaoMatch) qualificacao);
		});
		criarListaDeQualificacaoMatch(match, qualificacoes);
	}

	
	
}
