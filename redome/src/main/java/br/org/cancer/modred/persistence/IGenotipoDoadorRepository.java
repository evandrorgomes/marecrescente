package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.GenotipoDoador;

/**
 * Interface para acesso ao banco envolvendo a entidade Genotipo.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IGenotipoDoadorRepository extends IRepository<GenotipoDoador, Long> {

	/**
	 * Método para buscar o genótipo ativo pelo id do genotipo e pela flag.
	 * @param idGenotipo Id do genotipo de um paciente ou doador.
	 * @return entidade de genotipo.
	 */
	GenotipoDoador findByIdAndExcluido(Long idGenotipo, boolean excluido);
	
	/**
	 * Obtém o genótipo associado ao doador passado por parâmetro (idDoador).
	 * 
	 * @param idDoador Identificador do doador.
	 * @return genotipo do doador.
	 */
	@Query("select g from GenotipoDoador g join g.doador p where p.id = :idDoador")
	GenotipoDoador obterGenotipoPorDoador(@Param("idDoador") Long idDoador);
	

}
