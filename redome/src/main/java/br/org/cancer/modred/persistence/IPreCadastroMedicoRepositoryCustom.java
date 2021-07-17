package br.org.cancer.modred.persistence;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.PreCadastroMedico;
import br.org.cancer.modred.model.domain.StatusPreCadastro;

/**
 * Inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos relacionados ao pré  
 * cadastro médico.
 * 
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias
 * da entidade PreCadastroMedico.
 * 
 * @author Thiago Moraes
 *
 */
public interface IPreCadastroMedicoRepositoryCustom {
    
    /**
     * Método que busca os pré cadastros por status e paginado.
     * 
     * @param status - filtro por status.
     * @param pageable - informações sobre paginação do conjunto que será retornado (informação opcional)
     * @return Page - Lista paginada de PreCadastroMedico
     */
    PageImpl<PreCadastroMedico> findByStatus(StatusPreCadastro status, Pageable pageable);
    
}
