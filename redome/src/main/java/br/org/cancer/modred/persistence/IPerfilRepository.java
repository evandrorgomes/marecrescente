package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.security.Perfil;

/**
 * Inteface que concentra e abstrai regras de pesquisa sobre o objeto perfil de acesso da plataforma redome. 
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade Perfil. 
 * 
 * @author Thiago Moraes
 *
 */
@Repository
public interface IPerfilRepository extends IRepository<Perfil, Long> {
	
	/**
	 * Lista os perfis disponíveis para o Redome.
	 * São todos os sistemas que estão disponíveis para cadastro simples, via crud,
	 * no Redome e mais o perfil de Médico, que possui um processo a parte.
	 * 
	 * @return lista de perfis.
	 */
	@Query("select perf from Perfil perf join perf.sistema sist where sist.disponivelRedome = true and perf.id <> 1")
	List<Perfil> listarDisponiveisParaRedome();
	
}
