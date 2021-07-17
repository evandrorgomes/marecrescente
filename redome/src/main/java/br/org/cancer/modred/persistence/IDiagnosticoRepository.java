package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Diagnostico;

/**
 * Camada de acesso a base dados de Diagnóstico.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IDiagnosticoRepository extends IRepository<Diagnostico, Long>{}