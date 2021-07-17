package br.org.cancer.redome.tarefa.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.tarefa.model.ValorNmdp;

/**
 * Repositória da entidade TempValorNmdp.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IValorNmdpRepository extends JpaRepository<ValorNmdp, String> {

	/**
	 * Lista todos os registros filtrado pela data de atualização.
	 * 
	 * @param dataUltimaAtualizacaoArquivo
	 * @return
	 */
	List<ValorNmdp> findByDataUltimaAtualizacaoArquivo(LocalDate dataUltimaAtualizacaoArquivo);

	@Modifying()
	@Query("UPDATE ValorNmdp v set v.splitCriado = :splitCriado")
	Integer updateFlagSplitCriado(@Param("splitCriado") Long splitCriado);
	
}
