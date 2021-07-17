package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Doador;

/**
 * Interface para acesso ao banco de dados envolvendo a classe DoadorInternacional.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IDoadorInternacionalRepository extends IDoadorRepository, IDoadorInternacionalRepositoryCustom{
	
	/**
	 * Realiza uma consulta verificando se existe doador com o mesmo
	 * GRID na base.
	 * 
	 * @param grid GRID associado ao doador.
	 * @return TRUE se já existir doador com as mesmas informações que devem ser únicas.
	 */
//	@Query(value = "select d.doad_id from DOADOR d where d.doad_tx_grid = :grid and (d.doad_id <> :idDoador or :idDoador is null)", nativeQuery = true)
//	Long obterIdDoadorPorGrid(@Param("grid") String grid, @Param("idDoador") Long idDoador);

	@Query(value = "select d.id from Doador d where d.grid = :grid and (d.id <> :idDoador or :idDoador is null) ")
	Long obterIdDoadorPorGrid(@Param("grid") String grid, @Param("idDoador") Long idDoador);
	
	
	/**
	 * Realiza a consulta por Registro de Origem e ID do doador no registro
	 * para verificar se não existe outro doador com as mesmas informações na
	 * base.
	 * 
	 * @param idRegistro ID do doador no registro.
	 * @param idRegistroOrigem ID do registro de origem.
	 * @return TRUE se já existir doador com as mesmas informações que devem ser únicas.
	 */
	@Query(value = "select d from Doador d "
				+ " where d.idRegistro = :idRegistro and d.registroOrigem.id = :idRegistroOrigem and (d.id <> :idDoador or :idDoador is null) ")
	Doador obterIdDoadorPorRegistroOrigem(@Param("idRegistro") String idRegistro, @Param("idRegistroOrigem") Long idRegistroOrigem, @Param("idDoador") Long idDoador);
	
}
