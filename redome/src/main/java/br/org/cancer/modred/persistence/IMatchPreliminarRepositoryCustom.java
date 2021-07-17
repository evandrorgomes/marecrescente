package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.vo.ViewMatchPreliminarQualificacaoVO;



/**
 * Interface para de métodos de banco para match preliminar customizadas.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IMatchPreliminarRepositoryCustom {


	/**
	 * Lista os matchs preliminares de uma busca preliminar e seus doadores.
	 * 
	 * @param idBuscaPreliminar - id do paciente.
	 * @param listaIdDoador - lista de doadores
	 * @return lista de match preliminar de uma busca preliminar
	 */
	List<Object[]> listarMatchsPorBuscaPreliminarIdDoador(Long rmr, List<Long> listaIdDoador);
	
	
	/**
	 * Lista os matchs preliminares relacionados a busca e tipos de doador informados.
	 * 
	 * @param idBuscaPreliminar ID da busca preliminar associada.
	 * @param tiposDoador tipo do doador que deve exibida no resultado, de acordo com o tipo.
	 * @param fases lista das fases do doador
	 * @return lista de DTOS com informações de match medula ou cordão, de acordo com o tipo informado.
	 */
	List<Object[]> listarMatchsPreliminares(@Param("idBuscaPreliminar") Long idBuscaPreliminar, @Param("tiposDoador") List<TiposDoador> tiposDoador, List<FasesMatch> fases);

	/**
	 * Lista a view utilizada na criação do match preliminar. 
	 * 
	 * @param idBuscaPreliminar - identificador ds busca preliminar.
	 * @return lista com os ddados da view.
	 */
	List<ViewMatchPreliminarQualificacaoVO> listarViewMatchPreliminarQualificacao(Long idBuscaPreliminar);
	
		
}
