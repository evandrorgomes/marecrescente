package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.domain.TiposExame;

/**
 * Camada de acesso a base dados de Exame de doador.
 * @param <T> Tipo de exame a ser consultado. Pode ser ExamePaciente ou ExameDoador.
 * 
 * @author bruno.sousa
 *
 */
@NoRepositoryBean
public interface IExameDoadorBaseRepository<T extends Exame> extends IExameBaseRepository<T>{
    
	/**
	 * Lista todos os exames conferidos (somente IDs) de um determinado paciente.
	 * 
	 * @param idDoador ID do doador a ser pesquisado.
	 * @return lista de exames do doador informado.
	 */
	@Query("select ex from PedidoExame pe join pe.exame ex join ex.doador doad join pe.tipoExame tpEx "
		+  "where doad.id = :#{#idDoador} and tpEx.id = :#{#tipoExame.id} and ex.statusExame = 1")
	List<T> listarExamesConferidos(@Param("idDoador") Long idDoador, @Param("tipoExame") TiposExame tipoExame);
	
	/**
	 * Obtém o doador associado ao ID de exame informado.
	 * 
	 * @param idExame ID do exame a ser consultado.
	 * @return doador associado ao pedido.
	 */
	@Query("select doad from Exame ex join ex.doador doad where ex.id = :idExame")
	Doador obterDoador(@Param("idExame") Long idExame);
	
	@Query("select ex from Exame ex where ex.doador.id = :idDoador and ex.statusExame = 1")
	List<T> listarExamesDoador(@Param("idDoador") Long idDoador);
	
	@Query("select ex from Exame ex "
			+  "where ex.doador.id = :idDoador and ex.statusExame = 1 order by ex.dataCriacao desc")
	List<T> listarExamesDoadorOrderByDataCriacaoDesc(@Param("idDoador") Long idDoador);

}