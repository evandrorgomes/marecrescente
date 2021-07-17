package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Medico;

/**
 * Interface de persistencia de Medico.
 * @author bruno.sousa
 *
 */
@Repository
public interface IMedicoRepository  extends IRepository<Medico, Long>, IMedicoRepositoryCustom {
    
    /**
     * Método para obter o médico por usuário.
     * 
     * @param usuarioId
     * @return Medico
     */
    Medico findByUsuarioId(Long usuarioId);

    /**
     * Médico que verifica a existencia do CRM no banco.
     * 
     * @param crm CRM a ser pesquisado.
     * @param id ID do médico.
     * @return TRUE se encontrar.
     */
    @Query(value= "SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Medico m WHERE m.crm = :crm " + 
    		"AND (m.id <> :id OR :id IS NULL)")
	boolean existsByCrmAndId(@Param("crm") String crm, @Param("id") Long id);
}
