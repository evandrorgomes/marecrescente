package br.org.cancer.modred.util;

import java.util.List;
import java.util.stream.Collectors;

import br.org.cancer.modred.model.domain.QualificacoesMatch;
import br.org.cancer.modred.model.interfaces.IMatch;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;

/**
 * Classe utilitária.
 * 
 * @author Filipe Queiroz
 *
 */
public class MatchUtil {

	/**
	 * Recupera os locus como string que tenham mismatch.
	 * 
	 * @param match - para buscar o locus
	 * @return String formatada com os locus que contém mismatch
	 */
	public static String recuperarLocusComMismatch(IMatch match) {
		return match.getQualificacoes().stream()
				.map(qualificacao ->
		{
					if (qualificacao.getQualificacao().equals(QualificacoesMatch.MISMATCH.getId()) || 
							qualificacao.getQualificacao().equals(QualificacoesMatch.MISMATCH_ALELICO.getId()) ) {
						return qualificacao.getLocus().getCodigo();
					}
					return "";
				})
				.filter(resultado -> resultado != "")
				.distinct()
				.collect(Collectors.joining(","));
	}

	/**
	 * recupera a classificação do match (6/6), (10/10).
	 * 
	 * @param match - match para gerar a classificação
	 * @return string com a classificação
	 */
	public static String recuperarClassificacao(IMatch match) {
		List<? extends IQualificacaoMatch> qualificacoes = match.getQualificacoes();
		
		int tamanhoFinal = qualificacoes.size();
		int tamanhoSemMismatch = Long.valueOf(match.getQualificacoes().stream().filter(qualificacao -> {
			return !qualificacao.getQualificacao().equals(QualificacoesMatch.MISMATCH.getId()) && 
					!qualificacao.getQualificacao().equals(QualificacoesMatch.MISMATCH_ALELICO.getId());
		}).count()).intValue();

		return tamanhoSemMismatch + "/" + tamanhoFinal;

	}

}
