package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.FonteCelula;

/**
 * Repositório para acesso a base de dados ligadas a entidade FonteCelula.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IFonteCelulaRepository extends IRepository<FonteCelula, Long> {}
