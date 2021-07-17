package br.org.cancer.modred.persistence;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.Laboratorio;

/**
 * Interface customizada de persistencia do Laboratório.
 * @author ergomes
 *
 */
public interface ILaboratorioRepositoryCustom {
	
	/**
	 * Método que busca os laboratórios e paginado.
	 * 
	 * @param nome - nome do laboratório (informação opcional)
	 * @param uf - UF do laboratório (informação opcional)
	 * @param pageable - informações sobre paginação do conjunto que será retornado (informação opcional)
	 * @return Page - Lista paginada de Medico
	 */
	PageImpl<Laboratorio> listarLaboratorios(String nome, String uf, Pageable pageable);

}
