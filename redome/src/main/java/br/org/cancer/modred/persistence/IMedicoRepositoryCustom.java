package br.org.cancer.modred.persistence;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.Medico;

/**
 * Interface customizada de persistencia de Medico.
 * @author bruno.sousa
 *
 */
public interface IMedicoRepositoryCustom {
	
	/**
	 * Método que busca os médicos e paginado.
	 * 
	 * @param pageable - informações sobre paginação do conjunto que será retornado (informação opcional)
	 * @return Page - Lista paginada de Medico
	 */
	PageImpl<Medico> listarMedicos(String crm, String nome, Pageable pageable);
}
