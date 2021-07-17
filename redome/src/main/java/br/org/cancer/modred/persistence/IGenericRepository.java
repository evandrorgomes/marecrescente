package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Classe para busca de repositórios no sistema.
 * 
 * @author Cintia Oliveira
 *
 */
public interface IGenericRepository {

    /**
     * Retorna o repositório dada uma classe de dominio.
     * 
     * @param classe
     * @return instância do repositório
     */
    @SuppressWarnings("rawtypes")
    JpaRepository getRepository(Class classe);

 }
