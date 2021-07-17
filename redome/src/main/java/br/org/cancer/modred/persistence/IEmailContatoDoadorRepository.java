package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EmailContatoDoador;

/**
 * Camada de acesso a base dados de Email de Contato do doador.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IEmailContatoDoadorRepository extends IEmailContatoBaseRepository<EmailContatoDoador>{
	
	@Query("select e from EmailContatoDoador e where e.doador.id = :idDoador")
	List<EmailContatoDoador> listarEmails(@Param("idDoador") Long idDoador);
	
}