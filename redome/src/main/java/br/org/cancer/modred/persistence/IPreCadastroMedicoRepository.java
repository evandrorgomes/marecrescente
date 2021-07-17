package br.org.cancer.modred.persistence;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PreCadastroMedico;
import br.org.cancer.modred.model.domain.StatusPreCadastro;

/**
 * Classe que faz acesso aos dados relativos a 
 * entidade PreCadastroMedico no banco de dados.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IPreCadastroMedicoRepository extends IRepository<PreCadastroMedico, Long>,
	IPreCadastroMedicoRepositoryCustom {

	/**
	 * Método que verifica se o CRM existe por status.
	 * 
	 * @param crm
	 * @param status
	 * @return true se o CRM existir.
	 */
	boolean existsByCrmAndStatus(String crm, StatusPreCadastro status);

	/**
	 * Método que verifica se o Login existe por status.
	 * 
	 * @param login - Login a ser verificado
	 * @param status
	 * @return true se o Login existir
	 */
	boolean existsByLoginAndStatus(String login, StatusPreCadastro status);
	
	/**
	 * Aprova o pré cadastro realizado pelo médico.
	 * 
	 * @param idPreCadastro ID do pré cadastro.
	 * @param dataAprovacao data da aprovação (última alteração).
	 * @param usuarioResponsavel usuário que aprovou.
	 */
	@Modifying
	@Query("update PreCadastroMedico pre "
		+  "set pre.status = br.org.cancer.modred.model.domain.StatusPreCadastro.APROVADO, "
		+  "pre.dataAtualizacao = :dataAprovacao, "
		+  "pre.usuarioResponsavel.id = :idUsuarioLogado "
		+  "where pre.id = :idPreCadastro "
		+  "and pre.status = br.org.cancer.modred.model.domain.StatusPreCadastro.AGUARDANDO_APROVACAO")
	Integer aprovar(@Param("idPreCadastro") Long idPreCadastro, 
					@Param("dataAprovacao") LocalDateTime dataAprovacao, 
					@Param("idUsuarioLogado") Long idUsuarioLogado);
}
