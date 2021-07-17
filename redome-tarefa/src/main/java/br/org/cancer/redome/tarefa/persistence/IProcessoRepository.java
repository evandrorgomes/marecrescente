package br.org.cancer.redome.tarefa.persistence;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.tarefa.model.Processo;

/**
 * Inteface que concentra e abstrai regras de pesquisa sobre os objetos do motor de processos da plataforma redome. 
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade Processo. 
 * 
 * @author Thiago Moraes
 *
 */
@Repository
public interface IProcessoRepository extends JpaRepository<Processo, Long>, IProcessoRepositoryCustom{
    
		
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Processo SET "+            
            "dataAtualizacao= :now, "+
            "status= :status "+
            "where id = :processoId AND status not in (3, 4)")
	int atualizarStatusProcesso(@Param("processoId") Long processoId, @Param("status") Long status, @Param("now") LocalDateTime now);	
    
}
