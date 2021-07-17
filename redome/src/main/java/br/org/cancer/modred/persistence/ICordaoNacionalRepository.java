package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.CordaoNacional;

/**
 * Interface para acesso ao banco de dados envolvendo a classe CordaoNacional.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface ICordaoNacionalRepository extends IDoadorRepository {
	
	/**
	 * Localiza um corão por numero de ficha e banco de sangue de cordão.
	 * @param numeroFicha - numero da fiha no banco.
	 * @param idBanco - id do banco.
	 * @return cordão localizado.
	 */
	CordaoNacional findByIdBancoSangueCordaoAndBancoSangueCordaoId(String numeroFicha, Long idBanco);
}
