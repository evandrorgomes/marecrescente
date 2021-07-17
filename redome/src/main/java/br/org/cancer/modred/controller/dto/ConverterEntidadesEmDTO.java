/**
 * 
 */
package br.org.cancer.modred.controller.dto;

import java.util.function.Function;

import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;

/**
 * Classe de conversão geneérica de objeto relacional para DTO.
 * @author Fillipe Queiroz
 *
 */
public class ConverterEntidadesEmDTO {

	/**
	 * Método para retornar a conversão de QualificaçãoMatch para GenotipoDTO. 
	 */
	public static Function<IQualificacaoMatch, GenotipoDTO> QUALIFICACAO_MATCH_PARA_GENOTIPO_DTO = new Function<IQualificacaoMatch, GenotipoDTO>() {

		@Override
		public GenotipoDTO apply(IQualificacaoMatch qualificacao) {
			String locus = qualificacao.getLocus().getCodigo();
			GenotipoDTO genotipoDTO = new GenotipoDTO();

//			if (qualificacao.getPosicao() == 1) {
//				genotipoDTO.setPrimeiroAlelo(qualificacao.getGenotipo());
//				genotipoDTO.setTipoPrimeiroAlelo(qualificacao.getTipo());
//				genotipoDTO.setQualificacaoPrimeiroAlelo(qualificacao.getQualificacao());
//			}
//			else {
//				genotipoDTO.setSegundoAlelo(qualificacao.getGenotipo());
//				genotipoDTO.setTipoSegundoAlelo(qualificacao.getTipo());
//				genotipoDTO.setQualificacaoSegundoAlelo(qualificacao.getQualificacao());
//			}

//			genotipoDTO.setTipoSegundoAlelo(qualificacao.getTipo());
//			genotipoDTO.setUnicoAlelo(qualificacao.getLocus().);
			genotipoDTO.setLocus(locus);
			genotipoDTO.setOrdem(qualificacao.getLocus().getOrdem());

			return genotipoDTO;
		}

	};
	
	/**
	 * Método para retornar a conversão de doador para GenotipoDoadorComparadoDTO. 
	 */
	public static Function<MatchDTO, GenotipoDoadorComparadoDTO> DOADOR_PARA_GENOTIPO_DOADOR_COMPARADODTO = new Function<MatchDTO, GenotipoDoadorComparadoDTO>() {

		@Override
		public GenotipoDoadorComparadoDTO apply(MatchDTO match) {
			GenotipoDoadorComparadoDTO genotipoDoadorComparadoDTO = new GenotipoDoadorComparadoDTO();
			genotipoDoadorComparadoDTO.setId(match.getId());
			genotipoDoadorComparadoDTO.setAbo(match.getAbo());
			genotipoDoadorComparadoDTO.setSexo(match.getSexo());
			genotipoDoadorComparadoDTO.setTipoDoador(match.getTipoDoador());
			genotipoDoadorComparadoDTO.setRegistroOrigem(match.getRegistroOrigem());
			genotipoDoadorComparadoDTO.setDataAtualizacao(match.getDataAtualizacao());
			
			if (genotipoDoadorComparadoDTO.getTipoDoador().equals(TiposDoador.NACIONAL.getId())) {
				genotipoDoadorComparadoDTO.setPeso( match.getPeso());
				genotipoDoadorComparadoDTO.setDmr(match.getDmr());
			}
			else if (genotipoDoadorComparadoDTO.getTipoDoador().equals(TiposDoador.INTERNACIONAL.getId())) {
				genotipoDoadorComparadoDTO.setIdRegistro(match.getIdRegistro());
				genotipoDoadorComparadoDTO.setPeso(match.getPeso());				
			}
			else if (genotipoDoadorComparadoDTO.getTipoDoador().equals(TiposDoador.CORDAO_NACIONAL.getId())) {
				genotipoDoadorComparadoDTO.setIdBscup(match.getIdBscup());
			}
			else if (genotipoDoadorComparadoDTO.getTipoDoador().equals(TiposDoador.CORDAO_INTERNACIONAL.getId())) {
				genotipoDoadorComparadoDTO.setIdRegistro(match.getIdRegistro());
			}
						
			genotipoDoadorComparadoDTO.setDataNascimento(match.getDataNascimento());

			return genotipoDoadorComparadoDTO;
		}

	};
	
	
	
}
